/** Implementing a deque by using a circular doubly linked list data structure.
	Some invariants:
		- The first node in the list is always sentinel.next.
		- The last node in the list is always sentinel.prev.
		- If a linked list deque is empty, then the prev and next fields of the sentinel point at the sentinel itself.
*/
public class LinkedListDeque<Item>{
	private class DLList {
		public Item item;
		public DLList prev;
		public DLList next;
		public DLList(DLList p, Item i, DLList n) {
			prev = p;
			item = i;
			next = n;
		}
	}
	private int size = 0;
	private DLList sentinel = new DLList(null, null, null);
	/** Creates an empty deque. */
	public LinkedListDeque() {
		// Is there a more concise way to implement a circular sentinel?
		sentinel.prev = sentinel; // Creates the circular DLList by pointing the prev and next fields at the sentinel itself.
		sentinel.next = sentinel;
	}
	/** Adds an item to the front of the deque. */
	public void addFirst(Item i) {
		DLList toFront = new DLList(sentinel, i, sentinel.next);
		// Special case for adding onto an empty list.
		if (sentinel.prev.equals(sentinel)) {
			sentinel.prev = toFront;
		}
		else {
			sentinel.next.prev = toFront; // Set the former first node's prev field to point at the added node.
		}
		sentinel.next = toFront;
		size += 1;
	}
	/** Adds an item to the back of the deque. */
	public void addLast(Item i) {
		DLList toLast = new DLList(sentinel.prev, i, sentinel);
		// Special case for adding onto an empty list.
		if (sentinel.next.equals(sentinel)) {
			sentinel.next = toLast;	
		}
		sentinel.prev.next = toLast; // Set the former last node's next field to point at the added node.
		sentinel.prev = toLast; // Set the prev field of the sentinel to point at the added node.
		size += 1;
	}
	/** Returns true if deque is empty (size is 0), false otherwise. */
	public boolean isEmpty() {
		return size == 0;
	}
	/** Returns the number of items in the deque. */
	public int size() {
		return size;
	}
	/** Removes and returns the item at the front of the Deque. If no such item exists, returns null. */
	public Item removeFirst() {
		if (sentinel.next.equals(sentinel)) {
			return null;
		}
		else {
			DLList toRemove = sentinel.next;
			DLList moveToFront = toRemove.next; // next field of sentinel points at the node after the removed item. 
			sentinel.next = moveToFront;
			moveToFront.prev = sentinel; // prev field of the new front item points at the sentinel
			toRemove.prev = null; // remove the pointer to the sentinel
			toRemove.next = null; // remove the pointer to the next node
			size -= 1;
			return toRemove.item;
		}	
	}
	/** Removes and returns the item at the back of the deque. If no such item exists, returns null. */
	public Item removeLast() {
		if (sentinel.prev.equals(sentinel)) {
			return null;
		}
		else {
			DLList toRemove = sentinel.prev; 
			DLList moveToLast = toRemove.prev;
			sentinel.prev = moveToLast; // Set the second-to-last node as the last node.
			moveToLast.next = sentinel;
			toRemove.prev = null;
			toRemove.next = null;
			size -= 1;
			return toRemove.item;
		}
	}
	/** Prints the items in the deque from the front to the rear, separated by spaces. */
	public void printDeque() {
		DLList p = sentinel.next;
		while (!p.equals(sentinel)) {
			System.out.print(p.item + " ");
			p = p.next;
		}
		System.out.print("\n");
	}
	/** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. */
	public Item get(int index) {
		DLList p = sentinel.next;
		if (index >= size) {
			return null;
		}
		while (index > 0) {
			p = p.next;
			index -= 1;
		}
		return p.item;
	}
	private Item getRec(DLList lst, int index) {
		if (index == 0) {
			return lst.item;
		}
		else if (index >= size) {
			return null;
		}
		else {
			return getRec(lst.next, index-1);
		}
	}
	public Item getRecursive(int index) {
		return getRec(sentinel.next, index);
	}
	/** Testing out the LinkedListDeque class! */
	public static void main(String[] args) {
		LinkedListDeque<Integer> l = new LinkedListDeque<>();
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
		l.printDeque();
		System.out.println("Size " + l.size());
		System.out.println(l.get(3));
		System.out.println(l.sentinel.next.next.item);
		System.out.println(l.sentinel.next.item);
		System.out.println(l.removeLast());
		l.printDeque();
		System.out.println(l.sentinel.next.item);
		System.out.println(l.getRecursive(0));

	}

}