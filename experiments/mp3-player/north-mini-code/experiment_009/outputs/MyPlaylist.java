```java
/**
 * Represents a music playlist capable of reading and writing playlist files in both
 * WPL (Windows Media Player) and M3U formats. This class manages a list of {@link Track}
 * objects along with metadata such as the playlist's name and description.
 *
 * @author Generated
 * @version 1.0
 */
public class MyPlaylist
{
    /**
     * The description of the playlist.
     */
    private String description;

    /**
     * The name of the playlist.
     */
    private String name;

    /**
     * The File object representing the playlist file on disk.
     */
    private File playlistFile;

    /**
     * A queue holding the {@link Track} objects in the playlist. Using a Queue
     * allows for FIFO addition and removal operations.
     */
    private Queue<Track> tracks;

    /**
     * The XML declaration prefix for a valid WPL file.
     */
    private static final String WPL_PREFIX = "<?wpl version=\"1.0\"?>";

    /**
     * String constant representing the M3U file format.
     */
    public static final String AUDIO_M3U = "m3u";

    /**
     * String constant representing the WPL file format.
     */
    public static final String WPL_STR = "wpl";


    /**
     * Constructs an empty MyPlaylist instance. Initializes name and description
     * to empty strings and creates a new queue for tracks.
     */
    public MyPlaylist()
    {
        this.description = new String();
        this.name = new String();
        this.tracks = new ArrayDeque<Track>();
    }

    /**
     * Constructs a MyPlaylist instance by parsing the specified File.
     * <p>
     * If the file ends with ".wpl", it parses the XML structure to extract the
     * playlist name, description, and media tracks. Otherwise, it prints an
     * error message.
     *
     * @param pFile The file to load the playlist from.
     * @throws FileNotFoundException If the specified file is not found during parsing.
     */
    public MyPlaylist(File pFile) throws FileNotFoundException
    {
        this.playlistFile = pFile;
        this.tracks = new ArrayDeque<Track>();
        if (pFile.getName().endsWith(".wpl"))
        {
            try
            {
                DocumentBuilderFactory dBuildFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dBuildFactory.newDocumentBuilder();
                Document document = dBuilder.parse(pFile);
                Node titleItem = document.getElementsByTagName("title").item(0);
                this.name = titleItem.getTextContent();
                Node descItem = document.getElementsByTagName("author").item(0);
                this.description = descItem.getTextContent();
                NodeList mediaItem = document.getElementsByTagName("media");

                for (int i = 0; i < mediaItem.getLength(); i++)
                {
                    Track track = new Track(mediaItem.item(i).getAttributes().getNamedItem("src").getTextContent());

                    this.tracks.add(track);
                }
            }
            catch (FileNotFoundException e)
            {
                throw new FileNotFoundException();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("File format not supported.");
        }
    }

    /**
     * Saves the current playlist to a file in the specified format.
     * <p>
     * Supported formats are "wpl" and "m3u" (defined by {@link #WPL_STR} and {@link #AUDIO_M3U}).
     * If the format is "wpl", it generates the WPL XML content. If "m3u", the logic is currently empty
     * based on the provided code.
     *
     * @param file The target format ("wpl" or "m3u").
     * @throws FileNotFoundException If an error occurs writing to the file.
     */
    public void savePlayList(String file)
    {
        String result = "";
        switch(file)
        {
            case WPL_STR:
            {
                result = this.wplParser();
                break;
            }
            case AUDIO_M3U:
            {
                // Implementation for M3U is not present in the provided code snippet
                break;
            }
            default:
            {
                System.out.println("File format not supported: " + file);
                return;
            }
        }

        try
        {
            PrintWriter writer = new PrintWriter(this.getPlayListFile().getAbsolutePath());
            writer.println(result);
            writer.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Generates the XML string representation of the playlist in WPL format.
     * <p>
     * This method constructs a DOM Document representing the SMIL structure,
     * populates it with metadata and track information, and transforms it
     * into a String.
     *
     * @return A String containing the WPL formatted playlist.
     */
    public String wplParser()
    {
        String result = WPL_PREFIX;
        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document document = docBuilder.newDocument();

            Element smilEle = document.createElement("smil");
            document.appendChild(smilEle);
            Element headEle = document.createElement("head");
            smilEle.appendChild(headEle);
            Element titleEle = document.createElement("title");
            titleEle.appendChild(document.createTextNode(this.getName()));
            headEle.appendChild(titleEle);
            Element authorEle = document.createElement("author");
            authorEle.appendChild(document.createTextNode(this.getDescription()));
            headEle.appendChild(authorEle);
            Element bodyEle = document.createElement("body");
            smilEle.appendChild(bodyEle);
            Element seqEle = document.createElement("seq");
            bodyEle.appendChild(seqEle);

            for (Track track : this.tracks)
            {
                Element mediaElement = document.createElement("media");
                mediaElement.setAttribute("src", track.getTrackFile().getAbsolutePath());
                mediaElement.setAttribute("albumTitle", track.getAlbum());
                mediaElement.setAttribute("albumArtist", track.getArtist());
                mediaElement.setAttribute("trackTitle", track.getTitle());
                mediaElement.setAttribute("trackArtist", track.getArtist());
                mediaElement.setAttribute("duration", Long.toString(track.getDuration()));
                seqEle.appendChild(mediaElement);
            }

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformerObj = transFactory.newTransformer();
            transformerObj.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            DOMSource domSrc = new DOMSource(document);
            StringWriter strWriter = new StringWriter();
            StreamResult strResult = new StreamResult(strWriter);
            transformerObj.transform(domSrc, strResult);
            StringBuffer sbuilder = strWriter.getBuffer();
            result += sbuilder.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Checks if the current in-memory playlist state matches the state saved on disk.
     * <p>
     * This is determined by comparing the generated WPL output with the first line
     * of the saved file.
     *
     * @return true if the playlist matches the saved state, false otherwise.
     */
    public boolean checkSaved()
    {
        if (this.playlistFile == null && this.description.equals("") && this.name.equals("") && this.tracks.size() == 0)
            return true;

        if (this.playlistFile == null)
            return false;
        String wplOutput = this.wplParser();
        String fileResult = new String();
        try
        {
            fileResult = Files.readAllLines(this.playlistFile.toPath()).get(0);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        if (wplOutput.equals(fileResult))
        {
            return true;
        }
        return false;
    }

    /**
     * Gets the playlist name.
     *
     * @return The name of the playlist.
     */
    public String getName()
    {
        return this.name;
    }


    /**
     * Gets the playlist description.
     *
     * @return The description of the playlist.
     */
    public String getDescription()
    {
        return this.description;
    }


    /**
     * Sets the playlist name.
     *
     * @param name The new name for the playlist.
     */
    public void setName(String name)
    {
        this.name = name;
    }


    /**
     * Sets the playlist description.
     *
     * @param description The new description for the playlist.
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Gets the queue of tracks in the playlist.
     *
     * @return A Queue containing all Track objects.
     */
    public Queue<Track> getTracks()
    {
        return this.tracks;
    }



    /**
     * Finds the index of a specific track within the playlist.
     *
     * @param track The Track object to search for.
     * @return The index of the track if found, -1 otherwise.
     */
    public int getIndexOf(Track track)
    {
        Queue<Track> queueCopy = new ArrayDeque<Track>(tracks);
        for(int i=0; i < tracks.size(); i++){
            Track c = queueCopy.remove();
            if (c.equals(track)) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Retrieves a track based on its index (ID).
     *
     * @param id The zero-based index of the track to retrieve.
     * @return The Track object at the specified index, or null if the index is out of bounds.
     */
    public Track getTrackById(int id)
    {
        if (id <= (this.tracks.size() - 1) && id >= 0)
        {
            return getById(id);
        }
        return null;
    }


    /**
     * Internal helper method to retrieve a track by index using a queue copy.
     * This is used because standard Queues do not support random access efficiently.
     *
     * @param index The zero-based index of the track.
     * @return The Track object at the specified index.
     */
    public Track getById(int index) {
        Queue<Track> queueCopy = new ArrayDeque<Track>(tracks);
        for(int i=0; i<index; i++){
            queueCopy.remove();
        }
        return queueCopy.peek();
    }

    /**
     * Gets the last track in the playlist.
     *
     * @return The last Track object, or null if the playlist is empty.
     */
    public Track getLast()
    {
        if (this.tracks.size() != 0)
        {
            return getById(this.tracks.size() - 1);
        }
        return null;
    }

    /**
     * Gets the File associated with this playlist.
     *
     * @return The File object representing the playlist file.
     */
    public File getPlayListFile()
    {
        return this.playlistFile;
    }

    /**
     * Sets the File associated with this playlist.
     *
     * @param file The File object to associate.
     */
    public void setPlayListFile(File file)
    {
        this.playlistFile = file;
    }

    /**
     * Gets the total number of tracks in the playlist.
     *
     * @return The count of tracks.
     */
    public int getCount()
    {
        return this.tracks.size();
    }

    /**
     * Calculates the total duration of the playlist and returns it as a formatted string.
     * <p>
     * The format is HHh MMm SSs (e.g., "1h 30m 15s"). Zero values are omitted.
     *
     * @return A String representing the total duration.
     */
    public String getDuration()
    {
        long duration = 0;

        for (Track track : this.tracks)
        {
            duration += track.getDuration();
        }

        int hrs = (int) duration / 3600;
        int rem = (int) duration - hrs * 3600;
        int min = rem / 60;
        rem = rem - min * 60;
        int sec = rem;

        return (((hrs == 0) ? "" : hrs + "h ") + ((hrs == 0 && min == 0) ? "" : min + "m ") + sec + "s");
    }


    /**
     * Adds a track to the end of the playlist.
     *
     * @param track The Track object to add.
     */
    public void add(Track track)
    {
        this.tracks.add(track);
    }

    /**
     * Removes a track from the playlist based on its index.
     *
     * @param id The zero-based index of the track to remove.
     */
    public void remove(int id)
    {

        this.tracks.remove(getById(id));
    }

    /**
     * Converts the internal Queue of tracks into an array.
     *
     * @return An array of Track objects.
     */
    public Track[] getTrackArray() {
        Queue<Track> queueCopy = new ArrayDeque<Track>(tracks);
        Track array[] = new Track[tracks.size()];
        for(int i=0; i < tracks.size(); i++){
            array[i] = queueCopy.remove();
        }
        return array;
    }

    /**
     * Updates the internal track queue by replacing it with the tracks from the provided array.
     *
     * @param ts An array of Track objects to set as the new playlist.
     */
    public void updateTracks(Track[] ts) {
        Queue<Track> queueCopy = new ArrayDeque<Track>();;
        for (Track t : ts) {
            queueCopy.add(t);
        }
        tracks = queueCopy;
    }

    /*public void swap(int index1, int index2)
    {
        Collections.swap(this.tracks, index1, index2);
    }*/
}
```