```java
import java.util.*;

public class DataStructure {

	private LinkedList<Track> tracks = new LinkedList<>();
	private QuickSort quickSort;

	public DataStructure() {
		this.quickSort = new QuickSort("artist");
	}

	public void addTracks(List<Track> ts) {
		LinkedList<Track> queueCopy = new LinkedList<>();
		for (Track t : ts) {
			queueCopy.add(t);
		}
		tracks = queueCopy;
	}

	/*public void swap(int index1, int index2)
	{
		Collections.swap(this.tracks, index1, index2);
	}*/

}
```