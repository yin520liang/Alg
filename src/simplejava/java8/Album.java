/**
 * 
 */
package simplejava.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @title
 * @description
 */
public class Album {
	private String name;
	private List<Track> tracks;

	public List<Track> getTracks() {
		return tracks;
	}

	public String getName() {
		return name;
	}

	public void addTrack(Track track) {
		if (tracks == null)
			tracks = new ArrayList<Track>();
		tracks.add(track);
	}

	public static void main(String[] args) {
//		Album album = new Album();
//		List<Track> tracks = new ArrayList<>(Arrays.asList(
//				new Track("Count on you", "Bruno Mars", 3),  
//				new Track("Treasure", "Bruno Mars", 4), 
//				new Track("As long as you love me", "Backstreet Boys", 5)));
//		tracks.stream().forEach(t -> album.addTrack(t));
//		Map<String, Map<Integer, List<Track>>> bySingerAndRating =
//			    tracks.stream()
//			          .collect(Collectors.groupingBy(t -> t.singer,
//			                              Collectors.groupingBy(t -> t.rating)));
//		tracks.sort(Comparator.comparing((Track t) -> t.getName()).reversed());
//		tracks.sort(Comparator.comparing((Track t) -> t.getName())
//				.thenComparing(t -> t.getRating()));
	}

}

class Track {
	String name;
	String singer;
	int rating;

	Track() {
	}

	Track(String name, String singer, int rating) {
		this.name = name;
		this.singer = singer;
		this.rating = rating;
	}

	String getName() {
		return name;
	}

	String getSinger() {
		return singer;
	}

	int getRating() {
		return rating;
	}

}
