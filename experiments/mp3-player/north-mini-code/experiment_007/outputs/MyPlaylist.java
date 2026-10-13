```java
/**
 * A class representing a music playlist.
 * <p>
 * This class manages playlist metadata (name, description) and a collection of {@link Track} objects.
 * It provides functionality to load playlist data from a file (specifically WPL format),
 * save the playlist to disk in WPL or M3U formats, and manipulate the track list (add, remove, get).
 * </p>
 *
 * @author (Assumed)
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
	 * The file associated with this playlist.
	 */
	private File playlistFile;
	
	/**
	 * The collection of tracks in this playlist, stored as a queue.
	 */
	private Queue<Track> tracks ;

	private static final String WPL_PREFIX = "<?wpl version=\"1.0\"?>";
	public static final String AUDIO_M3U = "m3u";
	public static final String WPL_STR = "wpl";


	/**
	 * Constructs an empty MyPlaylist object.
	 */
	public MyPlaylist()
	{
		this.description = new String();
		this.name = new String();
		this.tracks = new ArrayDeque<Track>();
	}

	/**
	 * Constructs a MyPlaylist object by loading data from the specified file.
	 * <p>
	 * Parses the WPL XML file to extract the playlist name, description, and track list.
	 * </p>
	 *
	 * @param pFile the file to load the playlist from.
	 * @throws FileNotFoundException if the specified file does not exist.
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
	 * Saves the current playlist to a file.
	 * <p>
	 * The format of the playlist to save is determined by the 'file' parameter.
	 * This method writes the generated content to the file path stored in {@link #playlistFile}.
	 * </p>
	 *
	 * @param file the format of the playlist to save ("wpl" or "m3u").
	 * @throws FileNotFoundException if an I/O error occurs when writing to the file.
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
	 * Generates the WPL XML string representation of the current playlist.
	 *
	 * @return a String containing the WPL XML content.
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
	 * Checks if the current state of the playlist matches the saved file on disk.
	 * <p>
	 * This compares the generated WPL output with the first line of the existing file.
	 * </p>
	 *
	 * @return {@code true} if the playlist is saved and matches the current state, {@code false} otherwise.
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
	 * Gets the name of the playlist.
	 *
	 * @return the playlist name.
	 */
	public String getName()
	{
		return this.name;
	}


	public String getDescription()
	{
		return this.description;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Gets the tracks in this playlist.
	 *
	 * @return a Queue containing the tracks.
	 */
	public Queue<Track> getTracks()
	{
		return this.tracks;
	}



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


	public Track getTrackById(int id)
	{
		if (id <= (this.tracks.size() - 1) && id >= 0)
		{
			return getById(id);
		}
		return null;
	}


	public Track getById(int index) {
		Queue<Track> queueCopy = new ArrayDeque<Track>(tracks);
		for(int i=0; i<index; i++){
			queueCopy.remove();
		}
		return queueCopy.peek();
	}

	public Track getLast()
	{
		if (this.tracks.size() != 0)
		{
			return getById(this.tracks.size() - 1);
		}
		return null;
	}

	/**
	 * Gets the playlist file.
	 *
	 * @return the File object associated with the playlist.
	 */
	public File getPlayListFile()
	{
		return this.playlistFile;
	}

	/**
	 * Sets the playlist file.
	 *
	 * @param file the File object to associate with the playlist.
	 */
	public void setPlayListFile(File file)
	{
		this.playlistFile = file;
	}

	/**
	 * Gets the number of tracks in the playlist.
	 *
	 * @return the count of tracks.
	 */
	public int getCount()
	{
		return this.tracks.size();
	}

	/**
	 * Calculates the total duration of all tracks in the playlist.
	 * <p>
	 * The duration is formatted as hours, minutes, and seconds (e.g., "1h 30m 5s").
	 * </p>
	 *
	 * @return a String representing the total duration.
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
	 * Adds a track to the playlist.
	 *
	 * @param track the Track to add.
	 */
	public void add(Track track)
	{
		this.tracks.add(track);
	}

	/**
	 * Removes a track from the playlist by its index.
	 *
	 * @param id the zero-based index of the track to remove.
	 */
	public void remove(int id)
	{

		this.tracks.remove(getById(id));
	}

	/**
	 * Gets an array containing all tracks in the playlist.
	 *
	 * @return an array of Track objects.
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
	 * Replaces the current list of tracks with the provided array.
	 *
	 * @param ts the new array of Track objects.
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