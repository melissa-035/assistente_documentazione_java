```java
import java.util.Arrays;

/**
 * Implements the QuickSort algorithm to sort an array of tracks based on various criteria.
 * This class provides methods for comparing tracks by artist, title, and duration,
 * and utilizes the QuickSort partitioning approach for efficient sorting.
 */
public class QuickSort {

	/**
     * The category to sort the tracks by.  Currently supports "artist", "title", and "duration".
     */
	private String category;

	/**
	 * Constructs a QuickSort object with the specified category for sorting.
	 * 
	 * @param c The category to use for sorting.  Valid values are "artist", "title", or any string.
	 */
	public QuickSort(String c) {
		this.category = c;
	}

	/**
	 * Compares two tracks based on their artist names.
	 * 
	 * @param t1 The first track.
	 * @param t2 The second track.
	 * @return A negative integer if the artist of t1 is lexicographically less than the artist of t2,
	 *         a positive integer if the artist of t1 is lexicographically greater than the artist of t2,
	 *         and 0 if the artists are equal.
	 */
	int compareByArtist(Track t1, Track t2) {
		return t1.getArtist().compareTo(t2.getArtist());
	}

	/**
	 * Compares two tracks based on their title names.
	 * 
	 * @param t1 The first track.
	 * @param t2 The second track.
	 * @return A negative integer if the title of t1 is lexicographically less than the title of t2,
	 *         a positive integer if the title of t1 is lexicographically greater than the title of t2,
	 *         and 0 if the titles are equal.
	 */
	int compareByTitle(Track t1, Track t2) {
		return t1.getTitle().compareTo(t2.getTitle());
	}

	/**
	 * Compares two tracks based on their duration in seconds.
	 * 
	 * @param o1 The first track.
	 * @param o2 The second track.
	 * @return A negative integer if the duration of o1 is less than the duration of o2,
	 *         a positive integer if the duration of o1 is greater than the duration of o2,
	 *         and 0 if the durations are equal.
	 */
	int compareByDuration(Track o1, Track o2) {
		if (o2.getDuration() < o1.getDuration()) {
			return 1;
		} else if (o2.getDuration() > o1.getDuration()) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * Compares two tracks based on the specified category ("artist", "title", or "duration").
	 * 
	 * @param t1 The first track.
	 * @param t2 The second track.
	 * @return A comparison result based on the category.  The result is determined by calling the appropriate comparison method (compareByArtist, compareByTitle, or compareByDuration).
	 */
	int compare(Track t1, Track t2) {
		if (category.equals("artist")) {
			return compareByArtist(t1, t2);
		} else if (category.equals("title")) {
			return compareByTitle(t1, t2);
		} else {
			return compareByDuration(t1, t2);
		}
	}

	/**
	 * Partitions the input array of tracks around a pivot element.
	 * 
	 * @param array The array of tracks to partition.
	 * @param start The starting index of the subarray to partition.
	 * @param end The ending index of the subarray to partition.
	 * @return The index of the pivot element after partitioning.
	 */
	private int partition(Track array[], int start, int end) {
		Track pivotElement = array[end];
		int i = (start - 1);
		for (int j = start; j < end; j++) {
			if (compare(array[j], pivotElement) <= 0) {
				i++;
				Track temp = array[i];
				array[i] = array[j];
				array[j] = temp;
			}
		}
		Track temp = array[i + 1];
		array[i + 1] = array[end];
		array[end] = temp;
		return i + 1;
	}

	/**
	 * Sorts an array of tracks using the QuickSort algorithm.
	 * 
	 * @param array The array of tracks to sort.
	 * @param start The starting index of the subarray to sort.
	 * @param end The ending index of the subarray to sort.
	 */
	public void sort(Track array[], int start, int end) {
		if (start < end) {
			int pi = partition(array, start, end);
			sort(array, start, pi - 1);
			sort(array, pi + 1, end);
		}
	}
}
```