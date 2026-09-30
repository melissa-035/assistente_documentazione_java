```java
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Represents a music track with associated metadata.
 */
public class Track
{
    /**
     * The album image associated with the track.
     */
    private BufferedImage albumImage;
    /**
     * The file object representing the music track.
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
     * The album of the track.
     */
    private String albumStr;
    /**
     * The duration of the track in seconds.
     */
    private long duration;

    /**
     * Constructs a Track object from a given file path.
     *
     * @param path The path to the music file.
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
     * Returns the artist of the track.
     *
     * @return The artist's name.
     */
    public String getArtist()
    {
        return this.artistStr;
    }

    /**
     * Returns the title of the track.
     *
     * @return The track's title.
     */
    public String getTitle()
    {
        return this.titleStr;
    }


    /**
     * Returns the duration of the track in seconds.
     *
     * @return The track's duration.
     */
    public long getDuration()
    {
        return this.duration;
    }


    /**
     * Returns the album art of the track.
     *
     * @return The album art image.
     */
    public BufferedImage getAlbumArt()
    {
        return this.albumImage;
    }

    /**
     * Returns the album name of the track.
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
     * @return The track file.
     */
    public File getTrackFile()
    {
        return this.trackFile;
    }

    /**
     * Returns the hash code for the Track object.
     *
     * @return The hash code.
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
     * @param obj The object to compare to.
     * @return True if the objects are equal, false otherwise.
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