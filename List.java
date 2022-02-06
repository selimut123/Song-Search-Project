/**
 * List.java
 * @author Bryan Matthew Budiputra
 * @author Bryan Christiano
 * @author Christopher Bryan
 * @author Russel Sofia
 * CIS22C, Course Project
 */
import java.util.NoSuchElementException;

public class List<T> {
	private class Node {
		private T data;
		private Node next;
		private Node prev;

		public Node(T data) {
			this.data = data;
			this.next = null;
			this.prev = null;
		}
	}

	private int length;
	private Node first;
	private Node last;
	private Node iterator;

	/****CONSTRUCTOR****/

	/**
	 * Instantiates a new List with default values
	 * @postcondition A new linked list object has has been constructed 
	 * with default values.
	 */
	public List() {
		first = null;
		last = null;
		length = 0;
		iterator = null;
	}

	/**
	 * Instantiates a new List by copying another List
	 * @param original the List to make a copy of
	 * @postcondition a new List object, which is an identical
	 * but separate copy of the List original
	 */
	public List(List<T> original) {
		if (original == null) {
			return;
		}
		if (original.length == 0) {
			length = 0;
			first = null;
			last = null;
			iterator = null;
		} else {
			Node temp = original.first;
			while (temp != null) {
				addLast(temp.data);
				temp = temp.next;
			}
			iterator = null;
		}
	}

	/****ACCESSORS****/

	/**
	 * Returns the value stored in the first node
	 * @precondition !isEmpty()
	 * @return the value stored at node first
	 * @throws NoSuchElementException when precondition is violated
	 */
	public T getFirst() throws NoSuchElementException{
		if (length == 0) {
			throw new NoSuchElementException("getFirst: List is Empty. No data to access!");
		}
		return first.data;// return the value stored in first node.
	}

	/**
	 * Returns the value stored in the last node
	 * @precondition !isEmpty()
	 * @return the value stored in the node last
	 * @throws NoSuchElementException when precondition is violated
	 */
	public T getLast() throws NoSuchElementException{
		if (length == 0) {
			throw new NoSuchElementException("getLast: List is Empty. No data to access!");
		}
		return last.data; // return the value stored in last node.
	}

	/**
	 * Returns the current length of the list
	 * @return the length of the list from 0 to n
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Returns whether the list is currently empty
	 * @return whether the list is empty
	 */
	public boolean isEmpty() {
		return length == 0;
	}

	/**
	 * Returns the element currently pointed at by the iterator
	 * @precondition iterator != offEnd
	 * @return the element currently pointed at by the iterator
	 * @throws NoSuchElementException when precondition is violated
	 */
	public T getIterator() throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("getIterator(): cannot get element, iterator is offEnd");
		}
		return iterator.data;
	}

	/**
	 * Returns the element currently pointed at by the iterator
	 * @precondition iterator != null
	 * @return true if iterator == null
	 */
	public boolean offEnd() {
		if (iterator == null) {
			return true;
		}
		return false;
	}

	/**
	 * Determines whether two Lists have the same data
	 * in the same order
	 * @param L the List to compare to this List
	 * @return whether the two Lists are equal
	 */
	@SuppressWarnings("unchecked")
	@Override public boolean equals(Object o) {
		if(o == this) {
			return true;
		} else if (!(o instanceof List)) {
			return false;
		} else {
			List<T> L = (List<T>) o;
			if (this.length != L.length) {
				return false;
			} else {
				Node temp1 = this.first;
				Node temp2 = L.first;
				while (temp1 != null) { //Lists are same length
					if (!(temp1.data.equals(temp2.data))) {
						return false;
					}
					temp1 = temp1.next;
					temp2 = temp2.next;
				}
				return true;
			}
		}
	}

	/****MUTATORS****/

	/**
	 * Creates a new first element
	 * @param data the data to insert at the
	 * front of the list
	 * @postcondition A new first node is created
	 */
	public void addFirst(T data) {
		if (first == null) {
			first = last = new Node(data);
		} else {
			Node N = new Node(data);
			N.next = first;
			first.prev = N;
			first = N;
		}
		length++;
	}

	/**
	 * Creates a new last element
	 * @param data the data to insert at the
	 * end of the list
	 * @postcondition A new last node is created
	 */
	public void addLast(T data) {
		if (first == null) {
			first = last = new Node(data);
		} else {
			Node N = new Node(data);
			last.next = N;
			N.prev = last;
			last = N;
		}
		length++;
	}

	/**
	 * Moves the iterator to the start of the list
	 * @precondition length != 0
	 * @postcondition Iterator is at the start of the list
	 * @throws NoSuchElementException when precondition is violated
	 */
	public void placeIterator() throws NoSuchElementException {
		if(length == 0) {
			throw new NoSuchElementException("placeIterator: List is Empty!");
		} else {
			iterator = first;
		}
	}

	/**
	 * Removes the element currently referenced by the iterator
	 * @precondition iterator != null
	 * @throws NullPointerException when iterator is off end
	 * @postcondition iterator will be null
	 */
	public void removeIterator() throws NullPointerException {
		if (iterator == null) { //precondition
			throw new NullPointerException("removeIterator: iterator is off end.");
		} else if (iterator == first) { //edge case
			removeFirst(); //should set iterator to null in this case
		} else if (iterator == last) { //edge case
			removeLast(); //should set iterator to null in this case
		} else { //general case
			iterator.next.prev = iterator.prev;
			iterator.prev.next = iterator.next;
			iterator = null;
			length--;
		}

	}

	/**
	 * Adds a Node following the one currently being referenced by the iterator
	 * @precondition The iterator cannot be null / offend()
	 * @edge_case The iterator is equal to last.
	 * @postcondition a new node is created following the 
	 * one currently being referenced by the iterator, length++
	 * @throws NullPointerException when precondition is violated
	 */
	public void addIterator(T data) throws NullPointerException {
		if(offEnd()) { // Precondition
			throw new NullPointerException("addIterator: iterator is pointing out to null!");
		}else if(iterator == last) { // Edge case
			addLast(data);
		}else { // General case
			Node temp = new Node(data);
			temp.next = iterator.next;
			iterator.next.prev = temp;
			iterator.next = temp;
			temp.prev = iterator;
			//temp.next.prev = temp;
			length++;
		}
	}

	/**
	 * Move the iterator to the Node following the one currently being referenced by the iterator.
	 * @precondition The iterator cannot be null / offEnd()
	 * @throws NullPointerException when precondition is violated
	 * @postcondition iterator points to the next Node
	 */
	public void advanceIterator() throws NullPointerException{
		if(iterator == null) {
			throw new NullPointerException("advanceIterator: iterator is null and cannot advance.");
		}
		iterator = iterator.next;
	}

	/**
	 * Move the iterator to the Node preceding the one currently being referenced by the iterator.
	 * @precondition The iterator cannot be null / offEnd()
	 * @throws NullPointerException when precondition is violated
	 * @postcondition iterator points to the previous Node
	 */
	public void reverseIterator() throws NullPointerException{
		if(iterator == null) {
			throw new NullPointerException("advanceIterator: iterator is null and cannot advance.");
		}
		iterator = iterator.prev;
	}

	/**
	 * Removes the element at the front of the list
	 * @precondition !isEmpty() / length != 0
	 * @postcondition The first node is removed
	 * @throws NoSuchElementException when precondition is violated
	 */
	public void removeFirst() throws NoSuchElementException{
		if (length == 0) {
			throw new NoSuchElementException("removeFirst(): Cannot remove from an empty List!");
		} else if (length == 1) {
			first = last = iterator = null;
		} else {
			if (iterator == first) { //edge case
				iterator = null;
			}
			first = first.next;
			first.prev = null;
		}
		length--;
	}

	/**
	 * Removes the element at the end of the list
	 * @precondition !isEmpty() / length != 0
	 * @postcondition The last node is removed
	 * @throws NoSuchElementException when precondition is violated
	 */
	public void removeLast() throws NoSuchElementException{
		if (length == 0) {
			throw new NoSuchElementException("removeLast: list is empty. Nothing to remove.");
		} else if (length == 1) {
			first = last = iterator = null;
		} else {
			if (iterator == last) {
				iterator = null;
			}
			last = last.prev;
			last.next.prev = null;
			last.next = null;
		}
		length--;
	}

	/****ADDITIONAL OPERATIONS****/

	/**
	 * List with each value on its own line
	 * At the end of the List a new line
	 * @return the List as a String for display
	 */
	@Override public String toString() {
		String result = "";
		Node temp = first;
		while(temp != null) {
			result += temp.data;
			temp = temp.next;
		}
		return result;
	}

	/**
	 * List with each value on its own line
	 * At the end of the List a new line
	 * @return the List as a String for display
	 */
	public void printNumberedList() {
		int x = 1;
		Node temp = first;
		while(temp != null) {
			System.out.println(x + ": " + temp.data);
			temp = temp.next;
			x++;
		}
	}

	/**
	 * Points the iterator at first
	 * and then advances it to the
	 * specified index
	 * @param index the index where
	 * the iterator should be placed
	 * @precondition 0 < index <= length
	 * @throws IndexOutOfBoundsException
	 * when precondition is violated
	 */
	public void iteratorToIndex(int index) throws IndexOutOfBoundsException{
		if(index <= 0 || index > getLength()) {
			throw new IndexOutOfBoundsException("IteratorToIndex(): index is out of range!");
		}
		placeIterator();
		for(int i = 1; i < index; i++) {
			advanceIterator();
		}
	}

	/**
	 * Searches the List for the specified
	 * value using the linear  search algorithm
	 * @param value the value to search for
	 * @return the location of value in the
	 * List or -1 to indicate not found
	 * Note that if the List is empty we will
	 * consider the element to be not found
	 * post: position of the iterator remains
	 * unchanged
	 */
	public int linearSearch(T value) {
		if (length == 0) {
			return -1;
		}
		int count = 1;
		for (Node temp = first; temp != null; temp = temp.next) {
			
			if (temp.data.equals(value)) {
				return count;
			}
			count++;
		}
		return -1;
	}
}
