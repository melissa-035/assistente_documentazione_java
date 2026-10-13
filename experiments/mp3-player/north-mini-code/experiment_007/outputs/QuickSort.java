```java
/**
 * Implements the Quicksort algorithm to sort an array of Track objects.
 * <p>
 * This class provides a flexible sorting mechanism where the comparison criteria
 * (Artist, Title, or Duration) is determined by the {@code category} field
 * provided during instantiation.
 *
 * @author (To be filled)
 * @see Track
 */
public class QuickSort
{
	/**
	 * Constructs a QuickSort instance with a specific sorting category.
	 *
	 * @param c the category to use for sorting; valid values are "artist", "title",
	 *         or "duration". This determines which comparison method will be used
	 *         when sorting tracks.
	 */
	String category;
	public QuickSort(String c) {
		this.category = c;
	}

	/**
	 * Compares two Track objects based on their artist names.
	 *
	 * @param t1 the first track to compare
	 * @param t2 the second track to compare
	 * @return a negative integer, zero, or a positive integer as the first track
	 *         is less than, equal to, or greater than the second track
	 */
	int compareByArtist(Track t1, Track t2) {
		return t1.getArtist().compareTo(t2.getArtist());
	}

	/**
	 * Compares two Track objects based on their titles.
	 *
	 * @param t1 the first track to compare
	 * @param t2 the second track to compare
	 * @return a negative integer, zero, or a positive integer as the first track
	 *         is less than, equal to, or greater than the second track
	 */
	int compareByTitle(Track t1, Track t2) {
		return t1.getTitle().compareTo(t2.getTitle());
	}

	/**
	 * Compares two Track objects based on their duration.
	 *
	 * @param o1 the first track to compare
	 * @param o2 the second track to compare
	 * @return a negative integer, zero, or a positive integer as the first track
	 *         is less than, equal to, or greater than the second track.
	 *         Note: Returns 1 if o2 is shorter, -1 if o2 is longer, 0 if equal.
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
	 * Compares two Track objects based on the sorting category specified at construction.
	 * <p>
	 * This method acts as a dispatcher, calling the appropriate comparison method
	 * ({@code compareByArtist}, {@code compareByTitle}, or {@code compareByDuration})
	 * depending on the value of {@code category}.
	 *
	 * @param t1 the first track to compare
	 * @param t2 the second track to compare
	 * @return a negative integer, zero, or a positive integer as the first track
	 *         is less than, equal to, or greater than the second track
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
	 * Partitions the data into two separate arrays allows for quicksort to work.
	 * <p>
	 * Selects a pivot element (the last element in the segment) and rearranges the
	 * elements in the array segment such that all elements less than or equal to the
	 * pivot are on the left side, and all elements greater than the pivot are on the
	 * right side. Returns the index of the pivot element.
	 *
	 * @param array the array to partition
	 * @param start the starting index of the segment to partition
	 * @param end the ending index of the segment to partition (the pivot index)
	 * @return the index of the pivot element after partitioning
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
	 * Sorts an array of Track objects using the Quicksort algorithm.
	 * <p>
	 * This is the public entry point for sorting. It recursively partitions the array
	 * until the sub-arrays are trivially small (size < 2), then sorts those partitions.
	 *
	 * @param array the array to be sorted
	 * @param start the starting index of the segment to sort
	 * @param end the ending index of the segment to sort
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