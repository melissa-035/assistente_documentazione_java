```java
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
import java.util.Collections;

/**
 * Represents a playlist file, allowing for creation, loading from WPL files,
 * saving to various formats (WPL and M3U), and managing the tracks within the playlist.
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
	 * The file object representing the playlist file.
	 */
	private File playlistFile;

	/**
	 * The list of tracks in the playlist, implemented as a Queue for efficient
	 * adding and removal from the end.
	 */
	private Queue<Track> tracks ;

	/**
	 * A constant string representing the WPL XML declaration prefix.
	 */
	private static final String WPL_PREFIX = "<?wpl version=\"1.0\"?>";

	/**
	 * A constant string representing the audio M3U file extension.
	 */
	public static final String AUDIO_M3U = "m3u";

	/**
	 * A constant string representing the WPL file extension.
	 */
	public static final String WPL_STR = "wpl";


	/**
	 * Constructs a new MyPlaylist object with an empty playlist.
	 */
	public MyPlaylist()
	{
		this.description = new String();
		this.name = new String();
		this.tracks = new ArrayDeque<Track>();
	}

	/**
	 * Constructs a new MyPlaylist object from a given WPL file.
	 *
	 * @param pFile The File object representing the WPL playlist file.
	 * @throws FileNotFoundException If the specified file does not exist.
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
	 * Saves the playlist to a specified file in either WPL or M3U format.
	 *
	 * @param file The file extension to save the playlist as (e.g., "wpl", "m3u").
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
	 * Parses the playlist into a WPL XML format string.
	 *
	 * @return A string containing the WPL XML representation of the playlist.
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
	 * Checks if the playlist has been saved correctly by comparing its WPL output with the
	 * contents of the saved file.
	 *
	 * @return True if the playlist has been saved correctly, false otherwise.
	 */
	public boolean checkSaved()
	{
		if (this.playlistFile == null && this.description.equals("") && this.name.equals("") && this.tracks