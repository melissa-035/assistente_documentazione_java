```java
public class QuickSort
{
	/**
	 * Represents a track with an artist, title, and duration.
	 */
	private Track track; // Assuming Track class exists

	/**
	 * Constructor for the QuickSort class.
	 * 
	 * @param c The category for sorting (e.g., "artist", "title", "duration").
	 */
	public QuickSort(String c) {
		this.category = c;
	}

	/**
	 * Compares two tracks based on their artist names.
	 * 
	 * @param t1 The first track.
	 * @param t2 The second track.
	 * @return A negative integer if the artist of t1 is lexicographically smaller than the artist of t2,
	 *         a positive integer if it is larger, and 0 if they are equal.
	 */
	int compareByArtist(Track t1, Track t2) {
		return t1.getArtist().compareTo(t2.getArtist());
	}

	/**
	 * Compares two tracks based on their title names.
	 * 
	 * @param t1 The first track.
	 * @param t2 The second track.
	 * @return A negative integer if the title of t1 is lexicographically smaller than the title of t2,
	 *         a positive integer if it is larger, and 0 if they are equal.
	 */
	int compareByTitle(Track t1, Track t2) {
		return t1.getTitle().compareTo(t2.getTitle());
	}

	/**
	 * Compares two tracks based on their durations.
	 * 
	 * @param o1 The first track.
	 * @param o2 The second track.
	 * @return A negative integer if the duration of o1 is less than the duration of o2,
	 *         a positive integer if it is greater, and 0 if they are equal.
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
	 * Compares two tracks based on the specified category.
	 * 
	 * @param t1 The first track.
	 * @param t2 The second track.
	 * @return A negative integer if the comparison based on the category is less than 0,
	 *         a positive integer if it is greater than 0, and 0 if they are equal.
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
	 * Partitions a subarray of an array into two subarrays based on a pivot element.
	 * This is the core logic behind the quicksort algorithm.
	 * 
	 * @param array The array to partition.
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
	 * Sorts a subarray of an array using the quicksort algorithm.
	 * 
	 * @param array The array to sort.
	 * @param start The starting index of the subarray.
	 * @param end The ending index of the subarray.
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