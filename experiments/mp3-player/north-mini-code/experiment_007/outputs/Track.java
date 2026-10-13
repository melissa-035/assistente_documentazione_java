/**
 * Represents a music track (MP3 file) and extracts metadata such as title, artist,
 * album, duration, and album art from ID3v2 tags. If metadata is missing,
 * the title is derived from the file name and artist/album default to "unknown artist"
 * and "unknown album" respectively.
 *
 * @param path the file path of the MP3 track to be loaded.
 * @throws FileNotFoundException if the specified track file does not exist.
 */
public class Track
{
	private BufferedImage albumImage;
	private File trackFile;
	private String titleStr;
	private String artistStr;
	private String albumStr;
	private long duration;


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

	public String getArtist()
	{
		return this.artistStr;
	}

	public String getTitle()
	{
		return this.titleStr;
	}


	public long getDuration()
	{
		return this.duration;
	}


	public BufferedImage getAlbumArt()
	{
		return this.albumImage;
	}

	public String getAlbum()
	{
		return this.albumStr;
	}

	public File getTrackFile()
	{
		return this.trackFile;
	}

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