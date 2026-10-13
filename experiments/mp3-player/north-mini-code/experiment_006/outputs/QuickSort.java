```java
/**
 * Implements the QuickSort algorithm to sort an array of Track objects.
 * This class allows sorting based on different criteria such as artist, title, 
 * or duration, determined by the constructor argument.
 */
public class QuickSort
{
    /**
     * The category (artist, title, or duration) used to determine 
     * the comparison logic during sorting.
     */
    String category;

    /**
     * Constructs a QuickSort instance with a specific sorting category.
     * 
     * @param c The sorting category. Valid values are "artist", "title", or "duration".
     */
    public QuickSort(String c) {
        this.category = c;
    }

    /**
     * Compares two tracks based on their artist names.
     * 
     * @param t1 The first track to compare.
     * @param t2 The second track to compare.
     * @return A negative integer, zero, or a positive integer as the first argument 
     *         is less than, equal to, or greater than the second.
     */
    int compareByArtist(Track t1, Track t2) {
        return t1.getArtist().compareTo(t2.getArtist());
    }

    /**
     * Compares two tracks based on their titles.
     * 
     * @param t1 The first track to compare.
     * @param t2 The second track to compare.
     * @return A negative integer, zero, or a positive integer as the first argument 
     *         is less than, equal to, or greater than the second.
     */
    int compareByTitle(Track t1, Track t2) {
        return t1.getTitle().compareTo(t2.getTitle());
    }

    /**
     * Compares two tracks based on their duration.
     * 
     * @param o1 The first track to compare.
     * @param o2 The second track to compare.
     * @return A negative integer, zero, or a positive integer based on the duration comparison.
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
     * Selects the appropriate comparison method based on the category.
     * 
     * @param t1 The first track.
     * @param t2 The second track.
     * @return The comparison result.
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
     * Partitions the array into two halves around a pivot element.
     * Elements smaller than or equal to the pivot are moved to the left side.
     * This method implements the Lomuto partition scheme.
     * 
     * @param array The array to partition.
     * @param start The starting index of the subarray.
     * @param end The ending index of the subarray (the pivot index).
     * @return The final position of the pivot element.
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
     * Public method to initiate the QuickSort algorithm on the entire array.
     * 
     * @param array The array to be sorted.
     * @param start The starting index of the array.
     * @param end The ending index of the array.
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