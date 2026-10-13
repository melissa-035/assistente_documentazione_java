```java
/**
 * This class implements the quicksort algorithm for sorting Track objects.
 * It can sort by category (artist, title, or duration) based on the provided
 * category in the constructor.
 */
public class QuickSort {
    /** The category by which sorting is performed. */
    String category;

    /**
     * Constructs a QuickSort instance with the specified sorting category.
     * @param c The category for comparison: "artist", "title", or "duration".
     */
    public QuickSort(String c) {
        this.category = c;
    }

    /**
     * Compares two Track objects by their artist names.
     * @param t1 The first Track.
     * @param t2 The second Track.
     * @return a negative integer if t1's artist is less than t2's artist,
     *         zero if equal, positive if greater.
     */
    int compareByArtist(Track t1, Track t2) {
        return t1.getArtist().compareTo(t2.getArtist());
    }

    /**
     * Compares two Track objects by their titles.
     * @param t1 The first Track.
     * @param t2 The second Track.
     * @return a negative integer if t1's title is less than t2's title,
     *         zero if equal, positive if greater.
     */
    int compareByTitle(Track t1, Track t2) {
        return t1.getTitle().compareTo(t2.getTitle());
    }

    /**
     * Compares two Track objects by their durations.
     * @param o1 The first Track.
     * @param o2 The second Track.
     * @return a negative integer if o1's duration is less than o2's duration,
     *         zero if equal, positive if greater.
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
     * Compares two Track objects based on the current category.
     * @param t1 The first Track.
     * @param t2 The second Track.
     * @return a negative integer if t1 should come before t2 in ascending order,
     *         zero if equal, positive if t1 should come after t2.
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
     * Partitions the array into two parts for quicksort.
     * Uses the last element as pivot.
     * @param array The array to partition.
     * @param start The starting index.
     * @param end The ending index.
     * @return the index of the pivot after partitioning.
     */
    // Partition of data into two separate arrays allows for
    // quicksort to work.
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
     * Sorts the specified array of Track objects in ascending order using the quicksort algorithm.
     * @param array The array to be sorted.
     * @param start The starting index of the subarray to be sorted.
     * @param end The ending index of the subarray to be sorted.
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