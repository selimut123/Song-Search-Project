/**
 * Song.java
 * @author Bryan Matthew Budiputra
 * @author Bryan Christiano
 * @author Christopher Bryan
 * @author Russel Sofia
 * CIS22C, Course Project
 */

import java.util.Comparator;

public class Song {
	private String title;
	private String artist;
    private int year;
    private String genre;
    private String lyrics;

    /**** CONSTRUCTOR ****/

	/**
	 * Instantiates a new Song with default values
	 * 
	 * @postcondition title, artist, genre, and lyrics are assigned an empty string,
     *                 year is assigned to -1. 
	 */
    public Song(){
        title = "";
        artist = "";
        year = -1;
        genre = "";
        lyrics = "";
    }
    
    /**** OVERLOADED CONSTRUCTOR 1 ****/

	/**
	 * Instantiates a new Song by copying the title and artist of another Song
	 * 
     * @param title
     * @param artist
	 * @postcondition a new Song object with only the title and artist
	 */
    public Song(String title, String artist){
        this.title = title;
        this.artist = artist;
        year = -1;
        genre = "";
        lyrics = "";
    }
    
    /**** OVERLOADED CONSTRUCTOR 2 ****/

	/**
	 * Instantiates a new Song by copying the title, 
     * artist, year, genre and lyrics of another Song
	 * 
     * @param artist
     * @param year
     * @param genre
     * @param lyrics
	 * @postcondition a new Song object, which is an identical 
     *                but separate copy of the Song original
	 */
    public Song(String title, String artist, int year, String genre, String lyrics) {
    	this.title = title;
    	this.artist = artist;
    	this.year = year;
    	this.genre = genre;
    	this.lyrics = lyrics;
    }

    /**** ACCESSORS ****/

    /**
	 * Accesses the song's title
	 * 
	 * @return the song's title
	 */
    public String getTitle(){
        return title;
    }
    
    /**
	 * Accesses the song's artist
	 * 
	 * @return the song's artist
	 */
    public String getArtist(){
        return artist;
    }
    
    /**
	 * Accesses the song's release year
	 * 
	 * @return the song's release year
	 */
    public int getYear(){
        return year;
    }

    /**
	 * Accesses the song's genre
	 * 
	 * @return the song's genre
	 */
    public String getGenre(){
        return genre;
    }
    
    /**
	 * Accesses the song's lyrics
	 * 
	 * @return the song's lyrics
	 */
    public String getLyrics(){
        return lyrics;
    }
    
    /**** MUTATORS ****/

    /**
	 * Updates the song's title
	 * 
	 * @param title a new title
	 */
    public void setTitle(String title) {
    	this.title = title;
    }
    
    /**
	 * Updates the song's artist
	 * 
	 * @param artist a new artist
	 */
    public void setArtist(String artist) {
    	this.artist = artist;
    }
    
    /**
	 * Updates the song's release year
	 * 
	 * @param year a new year
	 */
    public void setYear(int year) {
    	this.year = year;
    }
    
    /**
	 * Updates the song's genre
	 * 
	 * @param genre a new genre
	 */
    public void setGenre(String genre) {
    	this.genre = genre;
    }
    
    /**
	 * Updates the song's lyrics
	 * 
	 * @param lyrics new lyrics
	 */
    public void setLyrics(String lyrics) {
    	this.lyrics = lyrics;
    }

    /**** ADDITIONAL OPERATIONS ****/

    /**
	 * Returns a consistent hash code for each Song by summing the Unicode
	 * values of title and artist in the key 
     * Key = title + artist
	 * 
	 * @return the hash code
	 */
    @Override public int hashCode(){
        String key = title + artist;
        int sum = 0;
        for(int i = 0; i < key.length(); i++){
            sum += key.charAt(i);
        }
        return sum;
    }

    /**
	 * Compares this Song to another Object for equality
	 * 
	 * @param o another Object
	 * @return true if o is a Song and has a matching title and artist to this Song
	 */
    @Override public boolean equals(Object o) {
		if(this == o) {
			return true;
		}else if(!(o instanceof Song)) {
			return false;
		}else {
			Song L = (Song) o;
			if(this.title.compareTo(L.title) != 0) {
				return false;
			}
		}
		return true;
	}

    /**
     * Prints out the details of the song
     */
    @Override public String toString() {
    	return "Title: " + title + "\n" + "Artist: " + artist + "\n" + "Year: " + year + "\n" + "Genre: " + genre + "\n" + "Lyrics:\n" + lyrics + "\n";
    }
    
}

class SongComparator implements Comparator<Song>{
	@Override public int compare(Song song1, Song song2) {
		return song1.getTitle().compareTo(song2.getTitle());
	}
}

