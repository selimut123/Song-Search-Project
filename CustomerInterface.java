/**
 * songProject.java
 * @author Bryan Matthew Budiputra
 * @author Bryan Christiano
 * @author Christopher Bryan
 * @author Russel Sofia
 * CIS22C, Course Project
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerInterface {
	public static void main(String[] args) {
		final int CUSTOMER = 100;
		final int SONG = 15;
		HashTable<Song> song1 = new HashTable<>(SONG);
		ArrayList<BST<Song>> songs_list = new ArrayList<>();
	    HashTable<WordID> word_id = new HashTable<>(CUSTOMER);
		Song song = null;
		WordID song_id = null;
		SongComparator sCompare = new SongComparator();
		File inFile = new File("songs.txt");
		Scanner input = null;
		PrintWriter output = null;
		
		// Variable Declarations
		String title, genre, email, password, first, last, enWord, outFile, addFile;
		String lyrics = "", artist = "", temp = "";
		char uncapChoice, choice;
		int year, id = 0, count = 0, number = 0, decision = 0;
		int [] yearArr = new int[CUSTOMER]; 
		boolean invalid = true;

		try{
			input = new Scanner(inFile);
			while(input.hasNext()){
				title = input.nextLine().trim();
				artist = input.nextLine().trim();
				year = input.nextInt();
				yearArr[count] = year;
				count++;
				input.nextLine();
				genre = input.nextLine().trim();
				while(input.hasNext()) {
					temp = input.nextLine().trim() + " ";
					if(temp.equals(" "))
						break;
					temp = temp.replaceAll("[^a-zA-Z0-9 '_-]", "");
					lyrics += temp + "\n";
				}
				song = new Song(title, artist,year, genre, lyrics);
				song1.put(song);
				for(String word : lyrics.split(" ")) {
					word = word.trim();
					song_id = new WordID(word);
					if(!(word_id.contains(song_id))) {
						song_id.setID(id);
						word_id.put(song_id);
						songs_list.add(new BST<>());
						id++;
					}
					songs_list.get(word_id.get(song_id).getID()).insert(song, sCompare);
				}
				lyrics = "";
			}
		} catch(FileNotFoundException e){
			e.printStackTrace();
		}

		input = new Scanner(System.in);
		System.out.println("Welcome to our Song Menu Project!");
		System.out.println("\nLet's create an account for you!");
		System.out.print("\nPlease enter your email address: ");
		email = input.nextLine().trim();
		while(invalid){
			if(email.contains("@") && email.contains(".")){
				invalid = false;
			} else {
				System.out.print("\nPlease enter a valid email address with a domain name: ");
				email = input.nextLine().trim();
			}
		}
		System.out.print("Please enter your password: ");
		password = input.nextLine().trim();		
		Customer temp1 = new Customer(email, password);			
		System.out.print("Enter your first name: ");
		first = input.nextLine().trim();
		System.out.print("Enter your last name: ");
		last = input.nextLine().trim();
		temp1.setFirstName(first);
		temp1.setLastName(last);
		temp1.setInfo(songs_list, song1, word_id, yearArr);		
		
		System.out.println("\nWelcome, " + first + " " + last + "!\n");
	
		do {
			System.out.println("\nPlease select from the following options:\n");
			System.out.println("A. Upload a new song");
			System.out.println("B. Delete a song");
			System.out.println("C. Search for a song");
			System.out.println("D. Modify or update a song");
			System.out.println("E. Display song statistics");
			System.out.println("X. Quit");
			
			System.out.print("\nEnter your choice: ");
			uncapChoice = input.next().charAt(0); 
			choice = Character.toUpperCase(uncapChoice);

			if(choice == 'A') {
				input.nextLine();
				System.out.print("Please enter the file name: ");
				addFile = input.nextLine().trim();
				
				while(true){
					try{
						File newFile = new File(addFile);
						Scanner input1 = new Scanner(newFile);
						while(input1.hasNext()){
							title = input1.nextLine().trim();
							artist = input1.nextLine().trim();
							year = input1.nextInt();
							yearArr[count] = year;
							count++;
							input1.nextLine();
							genre = input1.nextLine().trim();
							while(input1.hasNext()) {
								temp = input1.nextLine().trim() + " ";
								if(temp.equals(" "))
									break;
								temp = temp.replaceAll("[^a-zA-Z0-9 '_-]", "");
								lyrics += temp + "\n";
							}
							song = new Song(title, artist,year, genre, lyrics);
							if(!song1.contains(song))
								temp1.addSong(song);
							lyrics = "";
						}
						input1.close();
						break;
					} catch(FileNotFoundException e){
						System.out.print("File not found! Please enter a valid file name: ");
						addFile = input.nextLine().trim();
					}
				}
			}else if(choice == 'B') {
				input.nextLine();
				System.out.println(temp1.printDetails());
				System.out.print("Please enter the song title to delete: ");
				title = input.nextLine().trim();
				while(temp1.searchByKey(title, "Lauv") == null) {
					System.out.print("Please enter the valid song title to delete: ");
					title = input.nextLine().trim();
				}
				song = new Song(title, artist);
				temp1.deleteSong(song);
			}else if(choice == 'C') {
				System.out.println("\nPlease select from the following options:");
				System.out.println("1.Search for specific song (using primary key)");
				System.out.println("2.Search for specific song (using search engine)");		
				System.out.print("\n"+"Enter your choice (1-2): ");
				temp = input.next();
				input.nextLine();
				while(true){
					try{
						number  = Integer.parseInt(temp);
						if(number != 1 || number != 2)
							break;
					}catch(NumberFormatException e){
						System.out.print("Please enter a valid choice: ");
						temp = input.next();
						input.nextLine();
					}
				}
				if(number == 1) {
					System.out.print("Enter the title of the song (using primary key): ");
					enWord = input.nextLine().trim();
						if(!(temp1.searchByKey(enWord, "Lauv") == null))
							System.out.println(temp1.searchByKey(enWord, "Lauv"));	
						else
							System.out.println("You don't have a record by that title.");
				}else if(number == 2) {
					System.out.print("Enter the word (search engine): ");
					enWord = input.nextLine().trim();
					song_id = new WordID(enWord);
					if(temp1.checkByWord(enWord)) {
						System.out.println("The following songs contains the word \"" + enWord + "\":");
						temp1.searchByWord(enWord);
						System.out.print("To view more information about any of these songs, enter the title name: ");
						title = input.nextLine().trim();
						while(true){
							if(temp1.checkByTitle(title, enWord)){
								System.out.println(temp1.searchByKey(title, "Lauv"));
								break;
							}else{
								System.out.print("Please input a valid title name from the list above: ");
								title = input.nextLine().trim();
							}
						}
					}else {
						System.out.println("There are no lyrics with that word.");
					}
				}else {
					System.out.println("\nInvalid menu option. Please enter 1 or 2.");
				}
			}else if(choice == 'D') {
				System.out.println(temp1.printDetails());
				input.nextLine();
				
				System.out.print("Please enter the song title to modify or update: ");
				title = input.nextLine().trim();
				
				while(true) {
					if(temp1.searchByKey(title, "Lauv") != null) {
						break;
					}else {
						System.out.print("Please enter the valid song title to modify or update: ");
						title = input.nextLine().trim();
					}
				}

				System.out.println("Choose one of the following: ");
				System.out.println("1. Year");
				System.out.println("2. Lyrics");
				System.out.println("3. Both");
				System.out.print("Choice to modify or update: ");
				temp = input.next();
				while(true){
					try{
						decision  = Integer.parseInt(temp);
						if(number != 1 || number != 2)
							break;
					}catch(NumberFormatException e){
						System.out.print("Please enter a valid choice: ");
						temp = input.next();
						input.nextLine();
					}
				}
				
				if (decision == 1) {
					System.out.print("Please enter a new year to modify or update: ");
					temp = input.next();
					input.nextLine();
					while(true){
					try{
						year  = Integer.parseInt(temp);
						break;
					}catch(NumberFormatException e){
						System.out.print("Please enter a valid year: ");
						temp = input.next();
						input.nextLine();
					}
				}
					temp1.updateSong(temp1.searchByKey(title, "Lauv"), year, "");
				}else if (decision == 2) {
					input.nextLine();
					System.out.println("Please enter new lyrics to modify or update following with the ENTER key pressed twice: ");
					while(input.hasNextLine()) {
						temp = input.nextLine().trim() + " ";
						if(temp.equals(" "))
							break;
						temp = temp.replaceAll("[^a-zA-Z0-9 '_-]", "");
						lyrics += temp + "\n";
					}
					temp1.updateSong(temp1.searchByKey(title, "Lauv"), -1, lyrics);
				}else if (decision == 3) {
					System.out.print("Please enter a new year to modify or update: ");
					temp = input.next();
					input.nextLine();
					while(true){
					try{
						year  = Integer.parseInt(temp);
						break;
					}catch(NumberFormatException e){
						System.out.print("Please enter a valid year: ");
						temp = input.next();
						input.nextLine();
					}

				}
					System.out.print("Please enter new lyrics to modify or update following with the ENTER key pressed twice: ");
					while(input.hasNextLine()) {
						temp = input.nextLine().trim() + " ";
						if(temp.equals(" "))
							break;
						temp = temp.replaceAll("[^a-zA-Z0-9 '_-]", "");
						lyrics += temp + "\n";
					}
					temp1.updateSong(temp1.searchByKey(title, "Lauv"), year, lyrics);
				}else {
					System.out.println("Invalid Choice!");
				}

			}else if(choice == 'E') {
				// 1. Total number of songs overall
				System.out.println("Number of songs in record: " + song1.getNumElements());
				// 2. Total number of songs released in year 2017
				System.out.println("Number of songs released in year 2017: " + temp1.statisticsByYear(2017));
				// 3. Total number of songs released in year 2018
				System.out.println("Number of songs released in year 2018: " + temp1.statisticsByYear(2018));
			}else if(choice != 'X') {
				System.out.println("\nInvalid menu option. Please enter A-D or X to exit.");
			}
		}while(choice != 'X');
		System.out.print("Please enter the file name to output records: ");
		outFile = input.next();
		File outputFile = new File(outFile);
		try{
			output = new PrintWriter(outputFile);
		} catch(FileNotFoundException e){
			e.printStackTrace();
		}
		output.println(temp1);
		output.close();
		System.out.println("\nGoodbye!");
		input.close();
	}
}
