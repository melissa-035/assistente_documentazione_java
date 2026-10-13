```java
/**
 * This class provides utility functions for managing music folders and files.
 * It includes methods for retrieving files and folders, generating image previews for folders,
 * and broadcasting messages to other components of the application.
 */
public class Util {

    /**
     * Default constructor for the Util class.
     */
    public Util() {}

    /**
     * Retrieves the current files and folders from various sources, including the database,
     * a document file, and a parent folder.  It prioritizes the database if available,
     * falling back to the document file if the database is not accessible.
     *
     * @param overrideCurrent An optional {@link DocumentFile} object to override the current folder.
     * @return A {@link filesAndFolders} object containing the list of files and folders.
     *         Returns {@code null} if there's an error retrieving the files and folders.
     */
    public filesAndFolders getCurrentFilesAndFolders(DocumentFile overrideCurrent) {
        DocumentFile theRoot = null;

        DocumentFile theParent = properties.getCurrentFolder();

        if (overrideCurrent != null) {
            properties.setCurrentFolder(overrideCurrent);
        }

        DocumentFile tryDbDocument = properties.getCurrentFolder();
        Boolean tryDb = true;
        Boolean useDb = true;

        if (tryDbDocument == null) {
            try {
                tryDbDocument = DocumentFile.fromTreeUri(theMusicExplorer, Uri.parse(properties.getRootFolder()));
            } catch (Exception ex) {
                tryDb = false;
                useDb = false;
            }
        }

        if (tryDb) {
            useDb = theDatabase.hasFoldersAndFiles(tryDbDocument.getUri());
        }

        Boolean retainParentUri = false;

        if (!useDb) {
            if (tryDbDocument == null) {
                try {
                    theRoot = DocumentFile.fromTreeUri(theMusicExplorer, Uri.parse(properties.getRootFolder()));
                } catch (Exception ex) {
                    return null;
                }
            }
        } else {
            filesAndFolders returnVal = new filesAndFolders(new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());
            if (theDatabase.getFoldersAndFiles(tryDbDocument.getUri(), returnVal.getFolderNames(), returnVal.getFolderURis(), returnVal.getFileNames(), returnVal.getFileURis())) {
                return returnVal;
            }
            retainParentUri = true;
        }

        if (tryDbDocument == null) {
            properties.setCurrentFolder(theRoot);
        }

        DocumentFile[] entityArray = properties.getCurrentFolder().listFiles();

        filesAndFolders theFilesAndFolders = new filesAndFolders(new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());

        if (entityArray.length == 0) {
            return theFilesAndFolders;
        }

        for (DocumentFile df : entityArray) {
            if (!df.getName().startsWith(".")) {
                theFilesAndFolders.addEntity(df);
            }
        }

        theFilesAndFolders.sort();

        theDatabase.addFolder(theParent, null == properties.getCurrentFolder() ? theRoot : properties.getCurrentFolder(), theFilesAndFolders, true, retainParentUri);

        return theFilesAndFolders;
    }

    /**
     * Generates an image preview for a given folder.
     * It creates a ConstraintLayout with a folder icon and the folder name,
     * adjusting the text size if necessary to fit within the layout.
     *
     * @param theActivity The {@link AppCompatActivity} in which to create the image.
     * @param folderName The name of the folder to display.
     * @return A {@link Bitmap} representing the folder image preview.
     */
    public static Bitmap getFolderImage(AppCompatActivity theActivity, String folderName) {
        ConstraintLayout layout = new ConstraintLayout(theActivity);

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(224, 224);

        layout.setId(R.id.folder_layout);
        layout.setLayoutParams(layoutParams);

        ImageView imageView = null;

        if (properties.getAutoDisplayFolders()) {
            imageView = new ImageView(theActivity);
            imageView.setId(R.id.folder_image_view);
            ConstraintLayout.LayoutParams imageViewParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setImageResource(R.drawable.folder);
            imageViewParams.bottomToBottom = R.id.folder_layout;
            imageViewParams.leftToLeft = R.id.folder_layout;
            imageViewParams.topToTop = R.id.folder_layout;
            imageViewParams.verticalBias = 0.0f;
            imageView.setLayoutParams(imageViewParams);
        }

        float textSize = 0.0f;

        TextView textView = new TextView(theActivity);

        textView.setId(R.id.folder_text_view);

        ConstraintLayout.LayoutParams textViewParams = new ConstraintLayout.LayoutParams(0, 224);

        textViewParams.width = 224;
        textViewParams.height = 224;

        if (! (imageView == null)) {
            textViewParams.leftMargin = 16;
            textViewParams.rightMargin = 16;
        }

        textViewParams.bottomToBottom = R.id.folder_layout;
        textViewParams.endToEnd = R.id.folder_layout;
        textViewParams.horizontalBias = 0;
        textViewParams.startToStart = R.id.folder_layout;
        textViewParams.topToTop = R.id.folder_layout;
        textViewParams.verticalBias = 1.7f;

        textView.setLayoutParams(textViewParams);
        textView.setForegroundGravity(Gravity.CENTER);

        if (imageView == null) {
            textView.setTextColor(Color.WHITE);
            textSize = 28.0f;
            textView.setTextSize(textSize);
        } else {
            textView.setTextColor(Color.BLACK);
            textSize = 20.0f;
            textView.setTextSize(textSize);
        }

        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        textView.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        textView.setBreakStrategy(LineBreaker.BREAK_STRATEGY_HIGH_QUALITY);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            textView.setLineBreakStyle(LineBreakConfig.LINE_BREAK_STYLE_NORMAL);
        }

        String theText = folderName;

        textView.setText(theText);

        if (! (imageView == null))