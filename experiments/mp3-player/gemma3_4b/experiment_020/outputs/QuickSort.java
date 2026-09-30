```java
/**
 * Sorts an array of `Track` objects using the QuickSort algorithm.
 * This class provides methods to compare tracks based on artist, title,
 * and duration, and implements the QuickSort algorithm to sort the tracks
 * in a specified category.
 */
public class QuickSort
{
	String category;
	/**
	 * Constructs a QuickSort object with the specified sorting category.
	 *
	 * @param c The sorting category ("artist", "title", or another string).
	 */
	public QuickSort(String c) {
		this.category = c;
	}

	/**
	 * Compares two tracks based on the artist name.
	 *
	 * @param t1 The first track.
	 * @param t2 The second track.
	 * @return A negative integer if the artist of t1 is less than the artist of t2,
	 *         a positive integer if the artist of t1 is greater than the artist of t2,
	 *         or 0 if the artists are equal.
	 */
	int compareByArtist(Track t1, Track t2) {
		return t1.getArtist().compareTo(t2.getArtist());
	}

	/**
	 * Compares two tracks based on the title name.
	 *
	 * @param t1 The first track.
	 * @param t2 The second track.
	 * @return A negative integer if the title of t1 is less than the title of t2,
	 *         a positive integer if the title of t1 is greater than the title of t2,
	 *         or 0 if the titles are equal.
	 */
	int compareByTitle(Track t1, Track t2) {
		return t1.getTitle().compareTo(t2.getTitle());
	}

	/**
	 * Compares two tracks based on their duration.
	 *
	 * @param o1 The first track.
	 * @param o2 The second track.
	 * @return A negative integer if the duration of o1 is less than the duration of o2,
	 *         a positive integer if the duration of o1 is greater than the duration of o2,
	 *         or 0 if the durations are equal.
	 */
	int compareByDuration(Track o1, Track o2) {
		if (o2.getDuration() < o1.getDuration()) {
			return 1;
		}
		else if(o2.getDuration() > o1.getDuration()) {
			return -1;
		}
		else {
			return 0;
		}
	}

	/**
	 * Compares two tracks based on the specified category ("artist" or "title").
	 * If the category is "artist", it compares tracks by artist name.
	 * If the category is "title", it compares tracks by title name.
	 * Otherwise, it compares tracks by duration.
	 *
	 * @param t1 The first track.
	 * @param t2 The second track.
	 * @return A negative integer if the first track is "less than" the second track
	 *         according to the specified category, a positive integer if the first track
	 *         is "greater than" the second track, or 0 if the tracks are equal.
	 */
	int compare(Track t1, Track t2) {
		if (category.equals("artist")) {
			return compareByArtist(t1, t2);
		}
		else if (category.equals("title")) {
			return compareByTitle(t1, t2);
		}
		else {
			return compareByDuration(t1, t2);
		}

	}

	/**
	 * Partitions a subarray of `Track` objects around a pivot element.
	 * This is a helper function used by the QuickSort algorithm.
	 *
	 * @param array The array of tracks to partition.
	 * @param start The starting index of the subarray.
	 * @param end The ending index of the subarray.
	 * @return The index of the pivot element after partitioning.
	 */
	private int partition(Track array[], int start, int end)
	{
		Track pivotElement = array[end];
		int i = (start-1);
		for (int j=start; j<end; j++)
		{
			if (compare(array[j],pivotElement) <=0)
			{
				i++;
				Track temp = array[i];
				array[i] = array[j];
				array[j] = temp;
			}
		}

		Track temp = array[i+1];
		array[i+1] = array[end];
		array[end] = temp;
		return i+1;
	}


	/**
	 * Sorts an array of `Track` objects using the QuickSort algorithm.
	 *
	 * @param array The array of tracks to sort.
	 * @param start The starting index of the subarray to sort.
	 * @param end The ending index of the subarray to sort.
	 */
	public void sort(Track array[], int start, int end)
	{
		if (start < end)
		{
			int pi = partition(array, start, end);
			sort(array, start, pi-1);
			sort(array, pi+1, end);
		}
	}
}
```