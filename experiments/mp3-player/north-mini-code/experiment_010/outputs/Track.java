```java
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Represents an MP3 track with metadata extracted from its ID3v2 tag.
 * <p>
 * The class reads the track file and retrieves the title, artist, album,
 * duration, and album cover image. If any metadata is missing, default
 * values are used.
 * </p>
 * @author (your name)
 */
public class Track
{
	/**
	 * The album cover image as a BufferedImage, or {@code null} if not available.
	 */
	private BufferedImage albumImage;
	/**
	 * The File object representing the track file.
	 */
	private File trackFile;
	/**
	 * The title of the track.
	 */
	private String titleStr;
	/**
	 * The artist of the track.
	 */
	private String artistStr;
	/**
	 * The album name.
	 */
	private String albumStr;
	/**
	 * The duration of the track in seconds.
	 */
	private long duration;


	/**
	 * Constructs a Track object from the given file path.
	 * <p>
	 * The constructor attempts to read the ID3v2 tag of the MP3 file to extract
	 * metadata such as title, artist, album, duration, and album cover image.
	 * If the file is not found, a FileNotFoundException is thrown.
	 * If any other error occurs during reading, the exception is printed but
	 * the constructor continues, leaving metadata fields as null/default.
	 * </p>
	 * @param path the absolute or relative path to the MP3 file.
	 * @throws FileNotFoundException if the specified file does not exist.
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
		this.titleStr = (this.titleStr == null) ? (this.trackFile.getName().substring(0, this.trackFile.getName().length() - 4)) : this.titleStr;
		this.artistStr = (this.artistStr == null) ? "unknown artist" : this.artistStr;
		this.albumStr = (this.albumStr == null) ? "unknown album" : this.albumStr;
	}

	/**
	 * Returns the artist of the track.
	 * @return the artist as a String, or "unknown artist" if not found.
	 */
	public String getArtist()
	{
		return this.artistStr;
	}

	/**
	 * Returns the title of the track.
	 * @return the title as a String, or the file name without extension if not found.
	 */
	public String getTitle()
	{
		return this.titleStr;
	}


	/**
	 * Returns the duration of the track in seconds.
	 * @return the duration as a long.
	 */
	public long getDuration()
	{
		return this.duration;
	}


	/**
	 * Returns the album cover image.
	 * @return a BufferedImage of the album cover, or null if no image is present.
	 */
	public BufferedImage getAlbumArt()
	{
		return this.albumImage;
	}

	/**
	 * Returns the album name.
	 * @return the album as a String, or "unknown album" if not found.
	 */
	public String getAlbum()
	{
		return this.albumStr;
	}

	/**
	 * Returns the File object representing the track.
	 * @return the track file.
	 */
	public File getTrackFile()
	{
		return this.trackFile;
	}

	/**
	 * Returns a hash code value for this track based on its metadata.
	 * @return the hash code.
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
	 * Compares this track with another object for equality.
	 * Two Track objects are considered equal if their title, artist, album,
	 * and duration are equal.
	 * @param obj the object to compare with.
	 * @return true if the objects are equal, false otherwise.
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