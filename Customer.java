/**
 * Customer.java
 * @author Bryan Matthew Budiputra
 * @author Bryan Christiano
 * @author Christopher Bryan
 * @author Russel Sofia
 * CIS22C, Course Project
 */

import java.util.ArrayList;

public class Customer {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private final int SIZE = 100;
    
	private HashTable<Song> song_list = new HashTable<>(SIZE);
    private ArrayList<BST<Song>> song_id = new ArrayList<>();
	private HashTable<WordID> word_id = new HashTable<>(SIZE);
    private SongComparator sCompare = new SongComparator();
	private ArrayList<Integer> yearArr = new ArrayList<>();

	/**** CONSTRUCTORS ****/

	/**
	 * Creates a new Customer when only email
	 * and password are known
	 * @param email the Customer email
	 * @param password the Customer password
	 * Assigns firstName to "first name unknown"
	 * Assigns lastName to "last name unknown"
	 */

	public Customer(String email, String password) {
		this.email = email;
		this.password = password;
		this.firstName = "first name unknown";
		this.lastName = "last name unknown";
	}

	/**
	 * Creates a new Customer
	 * @param firstName member first name
	 * @param lastName member last name
	 */	
	public Customer(String firstName, String lastName, String email, String password) {
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}


	/**** ACCESSORS ****/

	/**
	 * Accesses the customer first name
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Accesses the customer last name
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Accesses the email address
	 * @return the email address
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Accesses the password
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/** Determines whether a given password matches the customer password 
	 * @param anotherPassword the password to compare 
	 * @return true if the password matches and false otherwise
	 */ 
	public boolean passwordMatch(String anotherPassword) { 
		return password.equals(anotherPassword); 
	}

	/**
	 * Searches the BST by word
	 *
	 * @param word
	 */
	public void searchByWord(String word){
		ArrayList<Song> temp = new ArrayList<>();
		song_id.get(word_id.get(new WordID(word)).getID()).inOrderPrint(temp);
		for(int i = 0; i < temp.size(); i++){
			System.out.println((i+1) + ". " + temp.get(i).getTitle());
		}
	}

	/**
	 * Searches the hash table by key
	 *
	 * @param title
	 * @param artist
	 */
	public Song searchByKey(String title, String artist){
		return song_list.get(new Song(title, artist));
	}
	
	/**
	 * Check whether the given word is in any song
	 *
	 * @param word the specific word of the lyric
	 * @return true if the given word has a song lyric with that word
	 */
	
	public boolean checkByWord(String word){
		return word_id.get(new WordID(word)) != null && song_id.get(word_id.get(new WordID(word)).getID()).getSize() != 0;
	}
	
	/**
	 * Check whether the given title is in the list of BST
	 *
	 * @param title the title of the song
	 * @return true if the given title is in the list of BST
	 */
	public boolean checkByTitle(String title, String word) {
		return song_id.get(word_id.get(new WordID(word)).getID()).search(new Song(title, "Lauv"), false, sCompare) != null;
	}
	
	/**
	 * Compares whether the song is released in the year input
	 *
	 * @param input the year in which the song was released
	 * @return the number of songs released in the year input
	 */
	public int statisticsByYear(int input){
		int count  = 0;
		for(int year : yearArr){
			if(year == input)
				count++;
		}
		return count;
	}
	
	/**
	 * Prints the songs to a string
	 *
	 * @return all of the song's title
	 */
	public String printDetails(){
		ArrayList<Song> temp = new ArrayList<>();
		song_list.printTable(temp);
		String sum = "";
		for(int i = 0; i < temp.size(); i++){
 			sum += (i+1) + ". " + temp.get(i).getTitle() + "\n";
		}
		return sum;
	}

	/**** MUTATORS ****/

	/**
	 * Updates the customer's first name
	 * @param firstName a new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Updates the customer's last name
	 * @param lastName a new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Updates the string of the email address
	 * @param email the Customer's email address
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Updates the string of the password
	 * @param password the Customer's password
	 */
	public void setPassword(String password) {
		this.password = password; 
	}

	/**
	 * Sets the information of a song
	 *
	 * @param songs the list of songs
	 * @param song1 the list of songs
	 * @param wordId the mapping of words to numerical IDs
	 * @param years the release years
	 */
	public void setInfo(ArrayList<BST<Song>> songs, HashTable<Song> song1, HashTable<WordID> wordId, int [] years){
		for(BST<Song> song : songs){
            song_id.add(new BST<>(song, sCompare));
        }
        song_list = song1;
		word_id = wordId;
		for(int year : years){
			yearArr.add(year);
		}
	}

	/**
	 * Adds a new song after removing the 20 unwanted words
	 *
	 * @param song the song to be added
	 */
	public void addSong(Song song){
		int id = song_id.size();
		yearArr.add(song.getYear());
		removeUnwantedWords(song);
		song_list.put(song);
		for(String word : song.getLyrics().split(" ")) {
			word = word.trim();
			WordID words = new WordID(word);
			if(!(word_id.contains(words))) {
				words.setID(id);
				word_id.put(words);
				song_id.add(new BST<>());
				id++;
			}
			song_id.get(word_id.get(words).getID()).insert(song, sCompare);
		}
	}

	/**
	 * Deletes a song
	 *
	 * @param song the song to be deleted
	 */
	public void deleteSong(Song song){
		song = song_list.get(song);
		song_list.remove(song);
		for(int i = 0; i < yearArr.size(); i++){
			if(yearArr.get(i) == song.getYear()){
				yearArr.remove(i);
				break;
			}
		}
		for(String word : song.getLyrics().split(" ")) {
			word = word.trim();
			WordID words = new WordID(word);
			if(!(song_id.get(word_id.get(words).getID()).search(song, false, sCompare) == null))
				song_id.get(word_id.get(words).getID()).remove(song, sCompare);
		}
	}

	/**
	 * Updates information of a song
	 *
	 * @param song the song to be updated
	 * @param year the year to be updated
	 * @param lyrics the lyrics to be updated
	 */
	public void updateSong(Song song, int year, String lyrics){
		Song temp = song_list.get(song);
		
		if(year != -1){
			for(int i = 0; i < yearArr.size(); i++){
				if(yearArr.get(i) == temp.getYear()){
					yearArr.remove(i);
					break;
				}
			}
			temp.setYear(year);
			yearArr.add(year);
			for(String word : temp.getLyrics().split(" ")) {
				word = word.trim();
				WordID words = new WordID(word);
				song_id.get(word_id.get(words).getID()).search(temp, false, sCompare).setYear(year);
			}
		}
		if(lyrics != ""){
			Song temp2 = new Song("", "", -1, "", lyrics);
			removeUnwantedWords(temp2);
			for(String word : temp.getLyrics().split(" ")) {
				word = word.trim();
				WordID words = new WordID(word);
				if(!(song_id.get(word_id.get(words).getID()).search(temp, false, sCompare) == null))
					song_id.get(word_id.get(words).getID()).remove(temp, sCompare);
			}
			temp.setLyrics(temp2.getLyrics());
			int id = song_id.size();
			for(String word : temp2.getLyrics().split(" ")) {
				word = word.trim();
				WordID words = new WordID(word);
				if(!(word_id.contains(words))) {
					words.setID(id);
					word_id.put(words);
					song_id.add(new BST<>());
					id++;
				}
				song_id.get(word_id.get(words).getID()).insert(song, sCompare);
			}
		}
	}
	
	public void removeUnwantedWords(Song song) {
		song.setLyrics(song.getLyrics().replaceAll("A ", ""));
		song.setLyrics(song.getLyrics().replaceAll("It ", ""));
		song.setLyrics(song.getLyrics().replaceAll("It's '", ""));
		song.setLyrics(song.getLyrics().replaceAll("At ", ""));
		song.setLyrics(song.getLyrics().replaceAll("On ", ""));
		song.setLyrics(song.getLyrics().replaceAll("To ", ""));
		song.setLyrics(song.getLyrics().replaceAll("Of ", ""));
		song.setLyrics(song.getLyrics().replaceAll("The ", ""));
		song.setLyrics(song.getLyrics().replaceAll("And ", ""));
		song.setLyrics(song.getLyrics().replaceAll("Uh ", ""));
		song.setLyrics(song.getLyrics().replaceAll("Oh ", ""));
		song.setLyrics(song.getLyrics().replaceAll("Ooh ", ""));
		song.setLyrics(song.getLyrics().replaceAll("Yeah ", ""));
		song.setLyrics(song.getLyrics().replaceAll("Hmm ", ""));
		song.setLyrics(song.getLyrics().replaceAll("Mm ", ""));
		song.setLyrics(song.getLyrics().replaceAll("Mmm ", ""));
		song.setLyrics(song.getLyrics().replaceAll("Then ", ""));
		song.setLyrics(song.getLyrics().replaceAll("Just ", ""));
		song.setLyrics(song.getLyrics().replaceAll("For ", ""));
		song.setLyrics(song.getLyrics().replaceAll(" a ", " "));
		song.setLyrics(song.getLyrics().replaceAll(" it ", " "));
		song.setLyrics(song.getLyrics().replaceAll(" it's ", " "));
		song.setLyrics(song.getLyrics().replaceAll(" at ", " "));
		song.setLyrics(song.getLyrics().replaceAll(" on ", " "));
		song.setLyrics(song.getLyrics().replaceAll(" to ", " "));
		song.setLyrics(song.getLyrics().replaceAll(" of ", " "));
		song.setLyrics(song.getLyrics().replaceAll(" the ", " "));
		song.setLyrics(song.getLyrics().replaceAll(" and ", " "));
		song.setLyrics(song.getLyrics().replaceAll(" uh ", " "));
		song.setLyrics(song.getLyrics().replaceAll(" oh ", " "));
		song.setLyrics(song.getLyrics().replaceAll(" ooh ", " "));
		song.setLyrics(song.getLyrics().replaceAll(" yeah ", " "));
		song.setLyrics(song.getLyrics().replaceAll(" hmm ", " "));
		song.setLyrics(song.getLyrics().replaceAll(" mm ", " "));
		song.setLyrics(song.getLyrics().replaceAll(" mmm ", " "));
		song.setLyrics(song.getLyrics().replaceAll(" then ", " "));
		song.setLyrics(song.getLyrics().replaceAll(" just ", " "));
		song.setLyrics(song.getLyrics().replaceAll(" for ", " "));
	}
	/**** ADDITIONAL OPERATIONS ****/

	/**
	 * Creates a String of the customer's name in the form
	 * Account Name: <firstName> <lastName> + <song_list>
	 */
	@Override public String toString() {
		return "Account Name: " + firstName + " " + lastName + "\n" + song_list;
	}

	/**
	 * Compares this Customer to another Object for equality
	 * @param o another Object
	 * @return true if o is a Customer and has a matching 
	 * 		   email and password to this Customer
	 */
	@Override public boolean equals(Object o) {
		if(this == o) {
			return true;
		}else if(o instanceof Customer) {
			return true;
		}else {
			Customer L = (Customer) o;
			if(this.email != L.email) {
				return false;
			}else if(this.password != L.password){
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns a consistent hash code for
	 * each Customer by summing the Unicode values
	 * of each character in the key
	 * Key = email + password
	 *
	 * @return the hash code
	 */
	@Override public int hashCode() {
		String key = email + password;
		int sum = 0;
		for(int i = 0; i < key.length(); i++) {
			sum += (int) key.charAt(i);
		}
		return sum;
	}
}
