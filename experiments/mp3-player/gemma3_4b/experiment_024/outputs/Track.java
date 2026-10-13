```java
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Represents a music track with metadata and image data.
 *
 * <p>This class encapsulates the information for a single music track,
 * including its file, title, artist, album, duration, and album artwork.
 * It attempts to extract this information from an MP3 file using the
 * MP3agic library. If the MP3 file is invalid or missing required tags,
 * default values are used.</p>
 */
public class Track
{
	private BufferedImage albumImage;
	private File trackFile;
	private String titleStr;
	private String artistStr;
	private String albumStr;
	private long duration;

	/**
	 * Constructs a Track object from a given file path.
	 *
	 * <p>This constructor attempts to read an MP3 file from the specified path.
	 * It extracts the track's title, artist, album, duration, and album artwork
	 * from the MP3's ID3v2 tag. If the ID3v2 tag is not found or if any
	 * errors occur during the process, default values are used.</p>
	 *
	 * @param path The path to the MP3 file.
	 * @throws FileNotFoundException If the specified file does not exist.
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
	 * Returns the artist's name.
	 *
	 * @return The artist's name.
	 */
	public String getArtist()
	{
		return this.artistStr;
	}

	/**
	 * Returns the track's title.
	 *
	 * @return The track's title.
	 */
	public String getTitle()
	{
		return this.titleStr;
	}


	/**
	 * Returns the track's duration in seconds.
	 *
	 * @return The track's duration in seconds.
	 */
	public long getDuration()
	{
		return this.duration;
	}


	/**
	 * Returns the album artwork image.
	 *
	 * @return The album artwork image as a BufferedImage.
	 */
	public BufferedImage getAlbumArt()
	{
		return this.albumImage;
	}

	/**
	 * Returns the album's name.
	 *
	 * @return The album's name.
	 */
	public String getAlbum()
	{
		return this.albumStr;
	}

	/**
	 * Returns the file object representing the track.
	 *
	 * @return The file object.
	 */
	public File getTrackFile()
	{
		return this.trackFile;
	}

	/**
	 * Returns the hash code for the track.
	 *
	 * <p>This method overrides the {@code hashCode()} method to provide
	 * a consistent hash code based on the track's metadata. This is
	 * important for using Track objects in hash-based collections like
	 * {@code HashMap}.</p>
	 *
	 * @return The hash code for the track.
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
	 * Returns true if this Track object is equal to another object.
	 *
	 * <p>This method overrides the {@code equals()} method to provide
	 * a custom comparison of two Track objects based on their metadata.
	 * It considers the equality of the title, artist, album, and duration
	 * to determine if two tracks are the same.</p>
	 *
	 * @param obj The object to compare with.
	 * @return True if the two Track objects are equal, false otherwise.
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