```java
/**
 * Represents an MP3 audio track and provides access to its metadata.
 * <p>
 * This class reads an MP3 file using the {@link com.mpatric.mp3agic.Mp3File} library
 * to extract ID3v2 tags. It retrieves standard track information such as title,
 * artist, album, duration, and album art. If specific tags are missing, default
 * values are assigned (e.g., filename for title, "unknown artist" for artist).
 * </p>
 *
 * @author (Generated from provided code)
 * @version 1.0
 */
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;

public class Track
{
	/**
	 * The album cover art image associated with the track.
	 * <p>
	 * This is extracted from the ID3v2 tag if available. It can be {@code null}
	 * if the track does not contain album art or if the image data is invalid.
	 * </p>
	 */
	private BufferedImage albumImage;
	
	/**
	 * The source MP3 file associated with this track object.
	 */
	private File trackFile;
	
	/**
	 * The title of the track.
	 * <p>
	 * Initially populated from the ID3v2 tag. If the tag is missing, it defaults
	 * to the filename (without the extension).
	 * </p>
	 */
	private String titleStr;
	
	/**
	 * The artist name of the track.
	 * <p>
	 * Initially populated from the ID3v2 tag. If the tag is missing, it defaults
	 * to the string "unknown artist".
	 * </p>
	 */
	private String artistStr;
	
	/**
	 * The album name associated with the track.
	 * <p>
	 * Initially populated from the ID3v2 tag. If the tag is missing, it defaults
	 * to the string "unknown album".
	 * </p>
	 */
	private String albumStr;
	
	/**
	 * The duration of the track in seconds.
	 */
	private long duration;


	/**
	 * Constructs a {@code Track} instance from the given file path.
	 * <p>
	 * The constructor attempts to open the file and parse its ID3v2 metadata.
	 * If the file is not found, a {@code FileNotFoundException} is thrown immediately.
	 * If the file is found but parsing fails, the exception stack trace is printed,
	 * and the track object will contain null/default values for metadata.
	 * </p>
	 *
	 * @param path The file path (absolute or relative) to the MP3 file.
	 * @throws FileNotFoundException If the specified file does not exist on the filesystem.
	 */
	public Track(String path) throws FileNotFoundException
	{
		this.trackFile = new File(path);
		try
		{
			Mp3File track = new Mp3File(this.getTrackFile().getAbsolutePath());
			if (track.hasId3v2Tag())
			{
				ID3v2 id3v2Obj = track.getId3v2Tag();
				this.titleStr = id3v2Obj.getTitle();
				this.artistStr = id3v2Obj.getArtist();
				this.albumStr = id3v2Obj.getAlbum();
				this.duration = track.getLengthInSeconds();
				byte[] imgData = id3v2Obj.getAlbumImage();
				if (imgData != null)
				{
					this.albumImage = ImageIO.read(new ByteArrayInputStream(imgData));
				}
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
		
		// Fallback logic for metadata if tags were not present or parsing failed
		this.titleStr = (this.titleStr == null) ? (this.trackFile.getName().substring(0, this.trackFile.getName().length() - 4)) : this.titleStr;
		this.artistStr = (this.artistStr == null) ? "unknown artist" : this.artistStr;
		this.albumStr = (this.albumStr == null) ? "unknown album" : this.albumStr;
	}

	/**
	 * Retrieves the artist name of the track.
	 *
	 * @return The artist string. Returns "unknown artist" if no artist tag was found.
	 */
	public String getArtist()
	{
		return this.artistStr;
	}

	/**
	 * Retrieves the title of the track.
	 *
	 * @return The title string. Returns the filename (minus extension) if no title tag was found.
	 */
	public String getTitle()
	{
		return this.titleStr;
	}


	/**
	 * Retrieves the duration of the track.
	 *
	 * @return The track duration in seconds.
	 */
	public long getDuration()
	{
		return this.duration;
	}


	/**
	 * Retrieves the album cover art.
	 *
	 * @return A {@code BufferedImage} representing the album art. Returns {@code null}
	 *         if no album art is present in the file.
	 */
	public BufferedImage getAlbumArt()
	{
		return this.albumImage;
	}

	/**
	 * Retrieves the album name.
	 *
	 * @return The album string. Returns "unknown album" if no album tag was found.
	 */
	public String getAlbum()
	{
		return this.albumStr;
	}

	/**
	 * Retrieves the source MP3 file object.
	 *
	 * @return The {@code File} object representing the track's source file.
	 */
	public File getTrackFile()
	{
		return this.trackFile;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This hash code is generated based on the track's title, artist, album, and duration.
	 * If any of these fields are null, their contribution to the hash code is 0.
	 * </p>
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((albumStr == null) ? 0 : albumStr.hashCode());
		result = prime * result + ((artistStr == null) ? 0 : artistStr.hashCode());
		result = prime * result + (int) (duration ^ (duration >>> 32));
		result = prime * result + ((titleStr == null) ? 0 : titleStr.hashCode());
		return result;
	}

	/**
	 * Checks if this track is equal to another object.
	 * <p>
	 * Two {@code Track} objects are considered equal if their title, artist,
	 * album, and duration are identical. If one object has null fields and the
	 * other does not, or if string values differ, the objects are not equal.
	 * </p>
	 *
	 * @param obj The object to compare with.
	 * @return {@code true} if the objects represent the same track metadata;
	 *         {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Track other = (Track) obj;
		if (albumStr == null) {
			if (other.albumStr != null)
				return false;
		} else if (!albumStr.equals(other.albumStr))
			return false;
		if (artistStr == null) {
			if (other.artistStr != null)
				return false;
		} else if (!artistStr.equals(other.artistStr))
			return false;
		if (duration != other.duration)
			return false;
		if (titleStr == null) {
			if (other.titleStr != null)
				return false;
		} else if (!titleStr.equals(other.titleStr))
			return false;
		return true;
	}


}
```