```java
/**
 * Represents an MP3 audio track.
 * <p>
 * This class encapsulates the metadata of an MP3 file, such as title, artist, album,
 * duration, and album artwork. It reads the file from a given path and attempts to
 * extract information from the ID3v2 tags. If specific tags are missing or null,
 * the class provides fallback default values (e.g., "unknown artist", "unknown album",
 * or the file's name without extension for the title).
 * </p>
 *
 * @author [Author not specified]
 * @version 1.0
 */
public class Track
{
	/**
	 * The album cover art image extracted from the ID3v2 tag.
	 * <p>
	 * This field is initialized to {@code null} if the MP3 file does not contain
	 * an album image or if an error occurs during the image decoding process.
	 * </p>
	 */
	private BufferedImage albumImage;

	/**
	 * The source MP3 file associated with this track.
	 */
	private File trackFile;

	/**
	 * The title of the track extracted from the ID3v2 tag.
	 * <p>
	 * If the tag is missing or null, this defaults to the filename (minus extension).
	 * </p>
	 */
	private String titleStr;

	/**
	 * The artist name extracted from the ID3v2 tag.
	 * <p>
	 * If the tag is missing or null, this defaults to the string "unknown artist".
	 * </p>
	 */
	private String artistStr;

	/**
	 * The album name extracted from the ID3v2 tag.
	 * <p>
	 * If the tag is missing or null, this defaults to the string "unknown album".
	 * </p>
	 */
	private String albumStr;

	/**
	 * The duration of the track in seconds, extracted from the MP3 file.
	 */
	private long duration;


	/**
	 * Constructs a {@code Track} object from the specified file path.
	 * <p>
	 * The constructor attempts to read the MP3 file to extract ID3v2 metadata.
	 * If the file is not found, a {@code FileNotFoundException} is thrown.
	 * If other exceptions occur during reading (e.g., invalid format), they are
	 * caught and printed to the console, leaving the object partially initialized
	 * with default values for the failed attributes.
	 * </p>
	 *
	 * @param path The file path to the MP3 track.
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
	 * Retrieves the artist name of the track.
	 *
	 * @return The artist name. Returns "unknown artist" if not found in the file tags.
	 */
	public String getArtist()
	{
		return this.artistStr;
	}

	/**
	 * Retrieves the title of the track.
	 *
	 * @return The track title. Returns the filename (minus extension) if not found in the file tags.
	 */
	public String getTitle()
	{
		return this.titleStr;
	}


	/**
	 * Retrieves the duration of the track.
	 *
	 * @return The duration of the track in seconds.
	 */
	public long getDuration()
	{
		return this.duration;
	}


	/**
	 * Retrieves the album cover art image.
	 *
	 * @return The album art as a {@code BufferedImage}. Returns {@code null} if the
	 *         track does not have an album image or if the image could not be loaded.
	 */
	public BufferedImage getAlbumArt()
	{
		return this.albumImage;
	}

	/**
	 * Retrieves the album name.
	 *
	 * @return The album name. Returns "unknown album" if not found in the file tags.
	 */
	public String getAlbum()
	{
		return this.albumStr;
	}

	/**
	 * Retrieves the source MP3 file.
	 *
	 * @return The {@code File} object representing this track.
	 */
	public File getTrackFile()
	{
		return this.trackFile;
	}

	/**
	 * {@inheritDoc}
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
	 * {@inheritDoc}
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