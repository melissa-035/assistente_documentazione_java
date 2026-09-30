```java
/**
 * Represents a music track with associated metadata.
 *
 * This class encapsulates the details of a music track, including its
 * file, title, artist, album, and duration. It attempts to extract
 * this information from an MP3 file using the Mp3agic library.
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
	 * Constructs a Track object, attempting to load data from the specified file path.
	 *
	 * @param path The path to the MP3 file.
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
	 * Returns the name of the artist for this track.
	 *
	 * @return The artist's name.
	 */
	public String getArtist()
	{
		return this.artistStr;
	}

	/**
	 * Returns the title of this track.
	 *
	 * @return The title of the track.
	 */
	public String getTitle()
	{
		return this.titleStr;
	}


	/**
	 * Returns the duration of this track in seconds.
	 *
	 * @return The track's duration.
	 */
	public long getDuration()
	{
		return this.duration;
	}


	/**
	 * Returns the album art image for this track.
	 *
	 * @return The album art image.
	 */
	public BufferedImage getAlbumArt()
	{
		return this.albumImage;
	}

	/**
	 * Returns the album name for this track.
	 *
	 * @return The album name.
	 */
	public String getAlbum()
	{
		return this.albumStr;
	}

	/**
	 * Returns the file object representing this track.
	 *
	 * @return The file object.
	 */
	public File getTrackFile()
	{
		return this.trackFile;
	}

	/**
	 * Returns the hash code for this Track object.
	 *
	 * <p>
	 * This method overrides the {@link java.util.Object#hashCode()} method.
	 * The hash code for the object is computed based on the values of
	 * its attributes.
	 * </p>
	 *
	 * @return The hash code for this Track object.
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
	 * <p>
	 * This method overrides the {@link java.util.Object#equals()} method.
	 * The equals method for the object is computed based on the values
	 * of its attributes.
	 * </p>
	 *
	 * @param obj The object to compare this object with.
	 * @return True if this Track object is equal to the specified object,
	 *         false otherwise.
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