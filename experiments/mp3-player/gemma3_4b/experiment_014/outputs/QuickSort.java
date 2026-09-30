```java
import java.util.Comparator;

/**
 * This class implements the QuickSort algorithm to sort an array of Track objects.
 * It allows sorting by artist, title, or duration, based on the specified category.
 */
public class QuickSort {

    /**
     * The category for sorting.  Currently supports "artist", "title", and "duration".
     */
    String category;

    /**
     * Constructs a QuickSort object with a specified category.
     *
     * @param c The category to use for sorting.  Valid values are "artist", "title", and "duration".
     */
    public QuickSort(String c) {
        this.category = c;
    }

    /**
     * Compares two Track objects based on the artist name.
     *
     * @param t1 The first Track object.
     * @param t2 The second Track object.
     * @return A negative integer if the artist name of t1 is lexicographically less than the artist name of t2.
     *         A positive integer if the artist name of t1 is lexicographically greater than the artist name of t2.
     *         0 if the artist names are equal.
     */
    int compareByArtist(Track t1, Track t2) {
        return t1.getArtist().compareTo(t2.getArtist());
    }

    /**
     * Compares two Track objects based on the title name.
     *
     * @param t1 The first Track object.
     * @param t2 The second Track object.
     * @return A negative integer if the title name of t1 is lexicographically less than the title name of t2.
     *         A positive integer if the title name of t1 is lexicographically greater than the title name of t2.
     *         0 if the title names are equal.
     */
    int compareByTitle(Track t1, Track t2) {
        return t1.getTitle().compareTo(t2.getTitle());
    }

    /**
     * Compares two Track objects based on their duration.
     *
     * @param o1 The first Track object.
     * @param o2 The second Track object.
     * @return A negative integer if the duration of o1 is less than the duration of o2.
     *         A positive integer if the duration of o1 is greater than the duration of o2.
     *         0 if the durations are equal.
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
     * Compares two Track objects based on the specified category.
     *
     * @param t1 The first Track object.
     * @param t2 The second Track object.
     * @return A value based on the comparison result according to the specified category.
     *         If category is "artist", returns the result of compareByArtist(t1, t2).
     *         If category is "title", returns the result of compareByTitle(t1, t2).
     *         If category is "duration", returns the result of compareByDuration(t1, t2).
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
     * Partitions the given array of Track objects around a pivot element.
     *
     * @param array The array of Track objects to partition.
     * @param start The starting index of the partition.
     * @param end The ending index of the partition.
     * @return The final index of the pivot element after partitioning.
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
     * Sorts an array of Track objects in place using the QuickSort algorithm.
     *
     * @param array The array of Track objects to sort.
     * @param start The starting index of the portion of the array to sort.
     * @param end The ending index of the portion of the array to sort.
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