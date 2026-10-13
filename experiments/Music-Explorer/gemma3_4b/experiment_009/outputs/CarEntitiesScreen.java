```java
package com.envisionate.carservice.screen;

import static com.envisionate.musicinfolders.Globals.properties;
import static com.envisionate.musicinfolders.Globals.theMusicExplorer;
import static com.envisionate.musicinfolders.Globals.theMusicExplorerInterface;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.OptIn;
import androidx.car.app.CarContext;
import androidx.car.app.Screen;
import androidx.car.app.annotations.ExperimentalCarApi;
import androidx.car.app.model.Action;
import androidx.car.app.model.CarIcon;
import androidx.car.app.model.CarText;
import androidx.car.app.model.GridItem;
import androidx.car.app.model.GridSection;
import androidx.car.app.model.Header;
import androidx.car.app.model.MessageTemplate;
import androidx.car.app.model.Row;
import androidx.car.app.model.RowSection;
import androidx.car.app.model.Section;
import androidx.car.app.model.SectionedItemTemplate;
import androidx.car.app.model.Template;
import androidx.core.graphics.drawable.IconCompat;

import com.envisionate.musicinfolders.R;
import com.envisionate.musicinfolders.Util;
import com.envisionate.musicinfolders.filesAndFolders;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * A screen for displaying car entities, such as folders and files.
 *
 * <p>This class manages the layout and content of the screen, allowing users to navigate and interact
 * with the available entities. It leverages Car App Model components to create a visually appealing and
 * functional interface.</p>
 */
@ExperimentalCarApi
public class CarEntitiesScreen extends Screen {

    public static CarEntitiesScreen Instance = null;

    private static CarEntitiesScreen waitingScreen = null;
    public static int entitiesScreenBuildCount = 0;

    private List<GridSection.Builder> theGridSectionBuilders = new ArrayList<>();
    private RowSection.Builder rowSectionBuilder = new RowSection.Builder();
    private RowSection.Builder tracksRowSectionBuilder = new RowSection.Builder();
    private List<Section<?>> sections = new ArrayList<>();

    private CarIcon exitCarIcon = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.exit)).build();

    private SectionedItemTemplate.Builder theSectionedItemTemplateBuilder = new SectionedItemTemplate.Builder();

    TreeMap<String,String> nameToUri = new TreeMap<String,String>();

    private Boolean navBusy = false;

    /**
     * Constructs a new CarEntitiesScreen instance.
     *
     * @param carContext The CarContext object providing access to Car App features.
     */
    public CarEntitiesScreen(CarContext carContext) {
        super(carContext);
    }

    /**
     * Generates the Template for the screen, configuring the layout, content, and actions.
     *
     * <p>This method builds the UI components based on the available files and folders. It handles
     * navigation, interactions, and data display, ensuring a seamless user experience.</p>
     *
     * @return A MessageTemplate object representing the complete screen template.
     */
    @OptIn(markerClass = ExperimentalCarApi.class)
    @Override
    public Template onGetTemplate() {

        waitingScreen = null;
        navBusy = false;

        entitiesScreenBuildCount++;

        filesAndFolders theEntities = theMusicExplorer.getDisplayedFilesAndFolder();

        if ( null == theEntities ) {
            getCarContext().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    if ( navBusy )
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

        for ( GridSection.Builder gsb : theGridSectionBuilders )
            gsb.clearItems();
        theGridSectionBuilders.clear();

        CarIcon trackIcon = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.track)).build();

        if ( ! theMusicExplorerInterface.isRoot() ) {
            rowSectionBuilder.addItem(new Row.Builder()
                    .setTitle(String.format("Folder: %s",properties.getCurrentFolder().getName()))
                    .build());
            sections.add(rowSectionBuilder.build());
        }

        theGridSectionBuilders.add(new GridSection.Builder());

        tracksRowSectionBuilder.clearItems();

        int itemIndex = 0;
        int builderIndex = 0;
        int entityIndex = 0;

        for ( String folderName : theEntities.getFolderNames() ) {
            itemIndex++;
            entityIndex++;
            nameToUri.put(folderName, theEntities.getFolderURis().get(entityIndex - 1));
            theGridSectionBuilders.get(builderIndex).addItem(new GridItem.Builder()
                .setTitle(" ")
                .setImage(carIconFromFolder(folderName),GridItem.IMAGE_TYPE_LARGE)
                .setOnClickListener( () -> {
                    if ( navBusy )
                        return;
                    navBusy = true;
                    Util.doLater(new Runnable() {
                                     @Override
                                     public void run() {
                                         navBusy = false;
                                     }
                                 },500);
                    theMusicExplorerInterface.onItemClicked(nameToUri.get(folderName), null, true);
                })
                .build());

            if ( itemIndex == properties.getAutoDisplayColumns() ) {
                sections.add(theGridSectionBuilders.get(builderIndex).build());
                theGridSectionBuilders.add(new GridSection.Builder());
                itemIndex = 0;
                builderIndex++;
            }

        }

        if ( 0 < itemIndex % properties.getAutoDisplayColumns() ) {
            sections.add(theGridSectionBuilders.get(builderIndex).build());
            theGridSectionBuilders.add(new GridSection.Builder());
        }

        if ( 0 < theEntities.getFileNames().size() ) {

            itemIndex = 0;
            entityIndex = 0;
            for ( String fileName : theEntities.getFileNames() ) {
                itemIndex++;
                entityIndex++;
                nameToUri.put(fileName,theEntities.getFileURis().get(entityIndex - 1));
                String s = fileName;
                if ( -1 < s.lastIndexOf('.') )
                    s = s.substring(0,s.lastIndexOf('.'));
                tracksRowSectionBuilder.addItem(new Row.Builder()
                    .setTitle(s)
                    .setImage(trackIcon,Row.IMAGE_TYPE_LARGE)
                    .setOnClickListener( () -> {
                        theMusicExplorerInterface.setCurrentTrack(nameToUri.get(fileName));
                        theMusicExplorerInterface.onItemClicked(nameToUri.get(fileName),null,true);
                    })
                    .build());

            }

            sections.add(tracksRowSectionBuilder.build());
        }

        Action extraAction = new Action.Builder()
                .setIcon(exitCarIcon)
                .setOnClickListener(() -> {
                    Util.broadcast(theMusicExplorer,"com.envisionate.musicinfolders.STOP_REQUESTED_BY_CAR");
                })
                .build();

        Header.Builder headerBuilder = new Header.Builder();

        if ( ! theMusicExplorerInterface.isRoot() ) {
            Header theHeader = headerBuilder
                    .setTitle(String.format("Folder: %s",properties.getCurrentFolder().getName()))
                    .setStartHeaderAction(Action.BACK)
                    .addEndHeaderAction(extraAction)
                    .build();
            theSectionedItemTemplateBuilder.setHeader(theHeader);
            getCarContext().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    if ( navBusy )
                        return;
                    theMusicExplorerInterface.gotoParent();
                    navBusy = false;
                }
            });
        } else {
            Header theHeader =