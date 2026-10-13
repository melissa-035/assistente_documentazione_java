```java
/**
 * Represents the Car Entities Screen in the car application.
 * This screen displays a list of folders and files, allowing the user to navigate
 * and select items for playback.
 */
@ExperimentalCarApi
public class CarEntitiesScreen extends Screen {

    /**
     * A static instance of the CarEntitiesScreen.  Initialized lazily.
     */
    public static CarEntitiesScreen Instance = null;

    /**
     * A static variable that holds the waiting screen.
     */
    private static CarEntitiesScreen waitingScreen = null;

    /**
     * A static variable to track the number of screen builds.
     */
    public static int entitiesScreenBuildCount = 0;

    /**
     * A list of builders to construct grid sections.
     */
    private List<GridSection.Builder> theGridSectionBuilders = new ArrayList<>();

    /**
     * A builder for constructing row sections.
     */
    private RowSection.Builder rowSectionBuilder = new RowSection.Builder();

    /**
     * A builder for constructing tracks row sections.
     */
    private RowSection.Builder tracksRowSectionBuilder = new RowSection.Builder();

    /**
     * A list of sections to be displayed in the screen.
     */
    private List<Section<?>> sections = new ArrayList<>();

    /**
     * A CarIcon for the exit action.
     */
    private CarIcon exitCarIcon = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.exit)).build();

    /**
     * A builder for constructing sectioned item templates.
     */
    private SectionedItemTemplate.Builder theSectionedItemTemplateBuilder = new SectionedItemTemplate.Builder();

    /**
     * A TreeMap to store the mapping between folder names and URIs.
     */
    private TreeMap<String, String> nameToUri = new TreeMap<>();

    /**
     * A boolean flag to indicate if navigation is busy.
     */
    private Boolean navBusy = false;

    /**
     * Constructor for the CarEntitiesScreen.
     *
     * @param carContext The CarContext to use for this screen.
     */
    public CarEntitiesScreen(CarContext carContext) {
        super(carContext);
    }

    /**
     * Generates the template for the CarEntitiesScreen.
     *
     * @return The template.
     */
    @OptIn(markerClass = ExperimentalCarApi.class)
    @Override
    public Template onGetTemplate() {
        waitingScreen = null;
        navBusy = false;

        entitiesScreenBuildCount++;

        filesAndFolders theEntities = theMusicExplorer.getDisplayedFilesAndFolder();

        if (null == theEntities) {
            getCarContext().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    if (navBusy)
                        return;
                    navBusy = true;
                    theMusicExplorerInterface.gotoParent();
                }
            });
            CarText theMessage = new CarText.Builder(getCarContext().getString(R.string.empty_folder)).build();
            return new MessageTemplate.Builder(theMessage).setHeader(new Header.Builder().setStartHeaderAction(Action.BACK).setTitle(new CarText.Builder("Note").build()).build()).build();
        }

        nameToUri.clear();
        sections.clear();
        rowSectionBuilder.clearItems();
        theSectionedItemTemplateBuilder.clearSections();

        for (GridSection.Builder gsb : theGridSectionBuilders)
            gsb.clearItems();
        theGridSectionBuilders.clear();

        CarIcon trackIcon = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.track)).build();

        if (!theMusicExplorerInterface.isRoot()) {
            rowSectionBuilder.addItem(new Row.Builder()
                    .setTitle(String.format("Folder: %s", properties.getCurrentFolder().getName()))
                    .build());
            sections.add(rowSectionBuilder.build());
        }

        theGridSectionBuilders.add(new GridSection.Builder());

        tracksRowSectionBuilder.clearItems();

        int itemIndex = 0;
        int builderIndex = 0;
        int entityIndex = 0;

        for (String folderName : theEntities.getFolderNames()) {
            itemIndex++;
            entityIndex++;
            nameToUri.put(folderName, theEntities.getFolderURis().get(entityIndex - 1));
            theGridSectionBuilders.get(builderIndex).addItem(new GridItem.Builder()
                    .setTitle(" ")
                    .setImage(carIconFromFolder(folderName), GridItem.IMAGE_TYPE_LARGE)
                    .setOnClickListener(() -> {
                        if (navBusy)
                            return;
                        navBusy = true;
                        Util.doLater(new Runnable() {
                            @Override
                            public void run() {
                                navBusy = false;
                            }
                        }, 500);
                        theMusicExplorerInterface.onItemClicked(nameToUri.get(folderName), null, true);
                    })
                    .build());

            if (itemIndex == properties.getAutoDisplayColumns()) {
                sections.add(theGridSectionBuilders.get(builderIndex).build());
                theGridSectionBuilders.add(new GridSection.Builder());
                itemIndex = 0;
                builderIndex++;
            }

        }

        if (0 < itemIndex % properties.getAutoDisplayColumns()) {
            sections.add(theGridSectionBuilders.get(builderIndex).build());
            theGridSectionBuilders.add(new GridSection.Builder());
        }

        if (0 < theEntities.getFileNames().size()) {

            itemIndex = 0;
            entityIndex = 0;
            for (String fileName : theEntities.getFileNames()) {
                itemIndex++;
                entityIndex++;
                nameToUri.put(fileName, theEntities.getFileURis().get(entityIndex - 1));
                String s = fileName;
                if (-1 < s.lastIndexOf('.'))
                    s = s.substring(0, s.lastIndexOf('.'));
                tracksRowSectionBuilder.addItem(new Row.Builder()
                        .setTitle(s)
                        .setImage(trackIcon, Row.IMAGE_TYPE_LARGE)
                        .setOnClickListener(() -> {
                            theMusicExplorerInterface.setCurrentTrack(nameToUri.get(fileName));
                            theMusicExplorerInterface.onItemClicked(nameToUri.get(fileName), null, true);
                        })
                        .build());

            }

            sections.add(tracksRowSectionBuilder.build());
        }

        Action extraAction = new Action.Builder()
                .setIcon(exitCarIcon)
                .setOnClickListener(() -> {
                    Util.broadcast(theMusicExplorer, "com.envisionate.musicinfolders.STOP_REQUESTED_BY_CAR");
                })
                .build();

        Header.Builder headerBuilder = new Header.Builder();

        if (!theMusicExplorerInterface.isRoot()) {
            Header theHeader = headerBuilder
                    .setTitle(theMusicExplorer.getParentOf(properties.getCurrentFolder(), false).getName())
                    .setStartHeaderAction(Action.BACK)
                    .addEndHeaderAction(extraAction)
                    .build();
            theSectionedItemTemplateBuilder.setHeader(theHeader);
            getCarContext().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    if (navBusy)
                        return;
                    theMusicExplorerInterface.gotoParent();
                    navBusy = false;
                }
            });
        } else {
            Header theHeader = headerBuilder
                    .setTitle(getCarContext().getString(R.string.app_name))
                    .addEndHeaderAction(extraAction)
                    .build();
            theSectionedItemTemplateBuilder.setHeader(theHeader);
        }

        return theSectionedItemTemplateBuilder.setSections(sections).build();
    }

    /**
     * Creates a CarIcon object for a given folder name.
     *
     * @param folderName The name of the folder.
     * @return The CarIcon object.
     */
    private CarIcon carIconFromFolder(String folderName) {
        return new CarIcon.Builder(IconCompat.createWithBitmap(theMusicExplorerInterface.getFolderImage(folderName))).build();
    }

    /**
     * Returns the waiting screen.
     *
     * @return The waiting screen.
     */
    public static CarEntitiesScreen getWaitingScreen() {
        return waitingScreen;
    }

    /**
     * Clears the