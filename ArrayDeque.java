public class ArrayDeque<Item> {
	private Item[] items;
	private int front, rear; // Front and rear always have to contain some value after the first insertion
	private int size = 0;
	/** Creates an empty Deque */
	public ArrayDeque() {
		items = (Item[]) new Object[8];
		front = items.length/2;
		rear = items.length/2;
	}
    /** Decrement and increment helper functions for nextFirst and nextLast, respectively. */
    private static int decrement(int n, int length) {
        if (n == 0) {
            return n = length - 1;
        } else {
            return n = n - 1;
        }
    }
    private static int increment(int n, int length) {
        return n = (n + 1) % length;
    }
    /** Resizes the entire array */
    private void resize(int capacity) {
        Item[] resized = (Item[]) new Object[capacity];
        int j = 0;
        for (int i = this.front; i != this.rear; i = ArrayDeque.increment(i, this.items.length)) {
            resized[j] = this.items[i];
            j = ArrayDeque.increment(j, resized.length);
        }
        resized[j] = this.items[this.rear]; // rear
        this.front = 0;
        this.rear = this.items.length-1;
        this.items = resized;
    }
    /** Adds an item to the front of the deque. */
    public void addFirst(Item i) {
    	if (this.isEmpty()) {
    		items[this.front] = i; // front and rear should still be the same
            this.size += 1;
    	} else {
    		this.front = ArrayDeque.decrement(this.front, this.items.length);
    		this.items[this.front] = i;
    		this.size += 1;
    		if (this.size == this.items.length) {
    		    this.resize(this.items.length * 2);
            }
    	}
    }
    /** Adds an item to the rear of the deque. */
    public void addLast(Item i) {
    	if (this.isEmpty()) {
    		items[this.rear] = i; // front and rear should still be the same
            this.size += 1;
    	} else {
    		this.rear = ArrayDeque.increment(this.rear, this.items.length);
    		this.items[this.rear] = i;
    		this.size += 1;
    		if (this.size == this.items.length) {
    		    this.resize(this.items.length * 2);
            }
    	}
    }
    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return this.size == 0;
    }
    /** Returns the number of items in the Deque. */
    public int size() {
        return this.size;
    }
    /** Decides whether to condense an array of length 16 or more if the usage ratio of the array is less than 25%. */
    private void condense() {
        Item[] condensed = (Item[]) new Object[this.items.length/2];
        int j = 0;
        for (int i = this.front; i != this.rear; i = ArrayDeque.increment(i, this.items.length)) {
            condensed[j] = this.items[i];
            j = ArrayDeque.increment(j, condensed.length);
        }
        condensed[j] = this.items[this.rear];
        this.front = 0;
        this.rear = j;
        this.items = condensed;
    }
    // Fix removeFirst and removeLast.
    /** Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    public Item removeFirst() {
    	if (this.isEmpty()) {
    		return null;
    	} else {
    		Item toRemove = this.items[this.front];
    		this.items[this.front] = null;
    		this.front = ArrayDeque.increment(this.front, this.items.length);
    		this.size -= 1;
            // Check the usage ratio (r) and shrink the array by half if r < 0.25
            double r = this.size / (double) this.items.length;
    		if (r < 0.25) {
    		    this.condense();
            }
    		return toRemove;
    	}
    }
    /** Removes and returns the item at the back of the Deque. If no such item exists, returns null. */
    public Item removeLast() {
    	if (this.isEmpty()) {
    		return null;
    	} else {
    		Item toRemove = this.items[this.rear];
    		this.items[this.rear] = null;
    		this.rear = ArrayDeque.decrement(this.rear, this.items.length);
    		this.size -= 1;
    		// Check the usage ratio (r) and shrink the array by half if r < 0.25
            double r = this.size / (double) this.items.length;
    		if (r < 0.25) {
    		    this.condense();
            }
    		return toRemove;
    	}
    }
    /** Prints the items in the Deque from the first to last, separated by a space. */
    public void printDeque() {
 		if (this.isEmpty()) {
 			System.out.println("You have no items in your deque.");
 		} else {
 			for (int i = this.front; i != this.rear; i = ArrayDeque.increment(i, this.items.length)) {
 				System.out.print(this.items[i] + " ");
 			}
 			System.out.print(this.items[this.rear]); // Last item
 		}
 		System.out.print("\n");
    }
    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. */
    public Item get(int index) {
        if (index >= this.size) {
            return null;
        }
    	int actual_i = (index + this.front) % this.items.length;
    	return this.items[actual_i];
    }
    private int len() {
        return this.items.length;
    }
    /** Testing out the ArrayDeque class */
    public static void main(String[] args) {
        ArrayDeque<Integer> l = new ArrayDeque<>();
        l.addFirst(3);
        l.addFirst(3);
        l.addFirst(3);
        l.addFirst(3);
        l.addLast(7);
        l.addLast(8);
        l.addLast(9);
        l.addLast(6);
        l.addLast(0);
        l.addLast(5);
        l.addFirst(3);
        l.addFirst(3);
        l.addFirst(3);
        l.addFirst(3);
        l.addFirst(3);
        l.addFirst(4);
        l.printDeque();
        System.out.println("Size: " + l.size());
        System.out.println("Array length: " + l.len());
        l.removeFirst();
        l.removeFirst();
        l.removeFirst();
        l.removeFirst();
        l.removeFirst();
        l.removeFirst();
        l.removeFirst();
        l.removeFirst();
        l.removeFirst();
        l.printDeque();
        System.out.println("Array length: " + l.len());
        l.printDeque();
        System.out.println("Size " + l.size());
        System.out.println(l.get(3));
    }
}