```java
/**
 * Represents a music playlist that can handle WPL (Windows Playlist) and M3U formats.
 * This class manages the playlist's metadata (name, description) and a collection of {@link Track} objects.
 * It provides functionality to load playlists from files, save them in different formats, 
 * and manipulate the track queue (add, remove, search, update).
 *
 * @author Assistant
 * @version 1.0
 * @since 2023
 */
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.Queue;


public class MyPlaylist
{
	/**
	 * The description of the playlist (usually author or genre).
	 */
	private String description;
	
	/**
	 * The name/title of the playlist.
	 */
	private String name;
	
	/**
	 * The reference to the physical file associated with this playlist instance.
	 */
	private File playlistFile;
	
	/**
	 * The collection of tracks contained within this playlist.
	 * Using a Queue (specifically ArrayDeque) to maintain order and allow sequential processing.
	 */
	//private ArrayList<Track> tracks;
	private Queue<Track> tracks ;

	/**
	 * Constant representing the XML header prefix for WPL files.
	 */
	private static final String WPL_PREFIX = "<?wpl version=\"1.0\"?>";
	
	/**
	 * Constant string identifier for the M3U audio format.
	 */
	public static final String AUDIO_M3U = "m3u";
	
	/**
	 * Constant string identifier for the WPL format.
	 */
	public static final String WPL_STR = "wpl";


	/**
	 * Constructs an empty playlist.
	 * Initializes the playlist name, description, and track queue to default empty states.
	 */
	public MyPlaylist()
	{
		this.description = new String();
		this.name = new String();
		this.tracks = new ArrayDeque<Track>();
	}

	/**
	 * Constructs a playlist by loading data from a specified file.
	 * Currently supports only .wpl format. Parses the XML structure to extract
	 * the playlist name, description, and the list of media tracks.
	 *
	 * @param pFile The File object pointing to the playlist file (must be .wpl).
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
					// Assuming Track class constructor takes a file path or src string
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
	 * The format is determined by the {@code file} parameter string.
	 * 
	 * Note: The implementation currently only fully supports WPL format in the wplParser method.
	 * The M3U case is a placeholder.
	 *
	 * @param file The format to save as (e.g., "wpl" or "m3u").
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
				// Logic to generate M3U format would go here
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
	 * Generates a WPL (Windows Playlist) XML string representation of the current playlist.
	 * This method constructs a DOM Document, populates it with track data, and transforms it to a string.
	 *
	 * @return A String containing the full WPL XML document.
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
	 * Checks if the current in-memory playlist state is identical to the content saved in the playlist file.
	 * It compares the parsed WPL output of the current object with the first line of the saved file.
	 *
	 * @return true if the file is saved and matches the current state; false otherwise.
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
	 * @return The playlist name string.
	 */
	public String getName()
	{
		return this.name;
	}


	/**
	 * Gets the playlist description.
	 *
	 * @return The playlist description string.
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
	 * @return The Queue containing all tracks.
	 */
	public Queue<Track> getTracks()
	{
		return this.tracks;
	}



	/**
	 * Finds the index of a specific track within the playlist.
	 * Iterates through the tracks to find the first match.
	 *
	 * @param track The Track object to search for.
	 * @return The index of the track if found; -1 if the track is not in the playlist.
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
	 * Retrieves a track by its numerical ID (index).
	 * This method acts as a wrapper for {@link #getById(int)}.
	 *
	 * @param id The integer ID (index) of the track to retrieve.
	 * @return The Track object at the specified ID, or null if the ID is out of bounds.
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
	 * Internal helper method to access a track by index.
	 * Creates a copy of the queue and iterates to the specific index to peek at the element.
	 *
	 * @param index The zero-based index of the track to access.
	 * @return The Track at the specified index.
	 */
	public Track getById(int index) {
		Queue<Track> queueCopy = new ArrayDeque<Track>(tracks);
		for(int i=0; i<index; i++){
			queueCopy.remove();
		}
		return queueCopy.peek();
	}

	/**
	 * Gets the last track added to the playlist.
	 *
	 * @return The last Track in the queue, or null if the playlist is empty.
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
	 * Gets the playlist file object.
	 *
	 * @return The File object representing the associated playlist file.
	 */
	public File getPlayListFile()
	{
		return this.playlistFile;
	}

	/**
	 * Sets the playlist file object.
	 *
	 * @param file The File object to associate with this playlist.
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
	 * Calculates the total duration of all tracks in the playlist and formats it.
	 * Format is "Xh Ym Zs" (e.g., "1h 30m 15s").
	 *
	 * @return A formatted string representing the total duration.
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
	 * Adds a track to the end of the playlist queue.
	 *
	 * @param track The Track object to add.
	 */
	public void add(Track track)
	{
		this.tracks.add(track);
	}

	/**
	 * Removes a track from the playlist based on its ID (index).
	 *
	 * @param id The ID (index) of the track to remove.
	 */
	public void remove(int id)
	{

		this.tracks.remove(getById(id));
	}

	/**
	 * Converts the internal track queue into an array.
	 * Creates a copy of the queue to avoid external modification of internal state.
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
	 * Updates the playlist's track queue with a new array of tracks.
	 * Replaces the current queue with the contents of the provided array.
	 *
	 * @param ts An array of Track objects to populate the playlist with.
	 */
	public void updateTracks(Track[] ts) {
		Queue<Track> queueCopy = new ArrayDeque<Track>();;
		for (Track t : ts) {
			queueCopy.add(t);
		}
		tracks = queueCopy;
	}

	/**
	 * Swaps two tracks in the playlist based on their indices.
	 * This method is currently commented out in the source code.
	 *
	 * @param index1 The first index.
	 * @param index2 The second index.
	 
	public void swap(int index1, int index2)
	{
		Collections.swap(this.tracks, index1, index2);
	}*/
}
```