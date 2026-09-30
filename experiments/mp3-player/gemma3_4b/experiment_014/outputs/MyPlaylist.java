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

/**
 * Represents a playlist file (WPL format).
 * This class manages a collection of tracks and provides functionality to create, save, and load playlists.
 */
public class MyPlaylist
{
	private String description;
	private String name;
	private File playlistFile;
	//private ArrayList<Track> tracks;
	private Queue<Track> tracks ;

	private static final String WPL_PREFIX = "<?wpl version=\"1.0\"?>";
	public static final String AUDIO_M3U = "m3u";
	public static final String WPL_STR = "wpl";


	/**
	 * Constructs a new MyPlaylist object with an empty description and name.
	 */
	public MyPlaylist()
	{
		this.description = new String();
		this.name = new String();
		this.tracks = new ArrayDeque<Track>();
	}

	/**
	 * Constructs a new MyPlaylist object from an existing WPL file.
	 *
	 * @param pFile The File object representing the WPL playlist file.
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
	 * Saves the playlist to a file in either WPL or M3U format.
	 *
	 * @param file The file extension to use for saving the playlist (e.g., "wpl", "m3u").
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
				// Implementation for saving to M3U format would go here.
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
	 * Parses the playlist data and generates a WPL XML document.
	 *
	 * @return A string containing the WPL XML document.
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
	 * Checks if the playlist has been saved correctly by comparing the WPL output to the contents of the playlist file.
	 *
	 * @return True if the playlist has been saved correctly, false otherwise.
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
	 * Returns the name of the