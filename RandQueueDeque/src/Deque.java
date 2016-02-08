import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	   private int itemCount;
	   private Node<Item> head;
	   private Node<Item> tail;
	   
	   private static class Node<Item> {
		   private Item item;
		   private Node<Item> next;
		   private Node<Item> previous;
		   
		   public Node( Item new_item, Node<Item> next, Node<Item> prev ) {
			   item = new_item;
			   this.next = next;
			   previous = prev;
		   }
		   
		   public void setNext( Node<Item> next ) {
			   this.next = next;
		   }
		   
		   public void setPrevious( Node<Item> prev ) {
			   this.previous = prev;
		   }
		   
	   }
	
	   public Deque() {                          // construct an empty deque
		  itemCount = 0;
		  head = null;
		  tail = head;
		  
	   }
	   
	   public boolean isEmpty() {                 // is the deque empty?
		   return itemCount == 0;
	   }
	   
	   public int size() {                       // return the number of items on the deque
		   return itemCount;
	   }
	   
	   public void addFirst( Item item ) {          // add the item to the front
		   if ( item == null ) throw new java.lang.NullPointerException();
		   Node<Item> newItem;
	
		   //case: empty list
		   if ( head == null ) {
			   newItem = new Node<Item>( item, null, null );
			   head = newItem;
			   tail = head;
			   itemCount++;
			   return;
		   }
		   //case: one element in list, need to set tail's previous
		   else if ( head == tail && itemCount == 1 ) {
			   newItem = new Node<Item>( item, tail, null );
			   tail.setPrevious(newItem);
			   head = newItem;
			   itemCount++;
			   return;
		   }
		   else {
			   Node<Item> oldHead = head;
			   newItem = new Node<Item>( item, oldHead, null );
			   oldHead.setPrevious(newItem);
			   head = newItem;
			   itemCount++;
			   return;
		   }  
		      
	   }
	   
	   public void addLast( Item item ) {          // add the item to the end
		   if ( item == null ) throw new java.lang.NullPointerException();
		   
		   Node<Item> newItem;
		   
		   //case: empty list
		   if ( head == null ) {
			   newItem = new Node<Item>( item, null, null );
			   head = newItem;
			   tail = head;
			   itemCount++;
			   return;
		   }
		   else if ( head == tail && itemCount == 1 ) {
			   newItem = new Node<Item>( item, null, head);
			   head.setNext(newItem);
			   tail = newItem;
			   itemCount++;
			   return;
		   }
		   else {
			   Node<Item> oldLast = tail;
			   newItem = new Node<Item>(item, null, oldLast);
			   oldLast.setNext(newItem);
			   tail = newItem;
			   itemCount++;
			   return;
		   }
		   
		  
	   }
	 
	   public Item removeFirst() {               // remove and return the item from the front
		   if ( isEmpty() ) throw new java.util.NoSuchElementException();
		   Item item = head.item;
		   itemCount--;
		   // if head == tail, both are pointing to the same element, i.e. single element in list
		   if ( head == tail ) {
			   head = null;
			   tail = head;
			   return item;
		   }
		   else {
			   head = head.next;
			   return item;
		   }   
	   }
	   
	   public Item removeLast() {                // remove and return the item from the end
		   if ( isEmpty() ) throw new java.util.NoSuchElementException();
		   
		   Item item = tail.item;
		   itemCount--;
		   
		   if ( head == tail ) {
			   tail = null;
			   head = tail;
			   return item;
		   }
		   else {
			   tail = tail.previous;
			   return item;
		   }
	   }
	   
	   public Iterator<Item> iterator() {        // return an iterator over items in order from front to end
		   return new DequeIterator();
	   }
	   private class DequeIterator implements Iterator<Item> {
		   private Node<Item> current = head;
		   
		   public boolean hasNext() {
			   return current != null;
		   }
		   public void remove() { 
			   throw new java.lang.UnsupportedOperationException();
		   }
		   public Item next() {
			   Item item = current.item;
			   current = current.next;
			   return item;
		   }
		   
	   }
	   
	   public static void main( String[] args ) {  // unit testing
		   Deque dq = new Deque();
		   dq.addLast("dog");
		   dq.addLast("cat");
		   dq.addLast("sex");
		   dq.removeFirst();
		   dq.removeLast();
		   dq.addLast("monkey");
		 
		   dq.addFirst("Heather");
		   dq.addFirst("Eric");
		   dq.removeFirst();
		  
		   Iterator iter = dq.iterator();
		   while (iter.hasNext()) {
			   System.out.println(iter.next());
		   }
		   
	   }
}
