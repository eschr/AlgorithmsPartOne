/**
 * Algorithms I, Princeton University via coursera.org
 * Programming assignment II - Randomized Queues and Deques
 * 
 * Author : Eric Schraeder 
 * 			2/2016
 * 
 * RandomizedQueue.java creates a Queue data structure that uses a resizable array.  The class supports adding new <Item>s via enqueue()
 * and random dequeue().  
 */


import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> { 
	
	   private int itemCount;
	   private Item[] queue;
	   
	   public RandomizedQueue() {
		   itemCount = 0;
		   queue = (Item[]) new Object[5];
	   }
	   
	   public boolean isEmpty() {
		   return itemCount == 0;
	   }
	   
	   public int size() {
		   return itemCount;
		 
	   }
	   
	   //if array is full doubles the size of the array
	   private void doubleQueueSize(int newSize) {
		   Item[] temp = (Item[]) new Object[newSize];
		   for (int i = 0; i < queue.length; i++) {
			   temp[i] = queue[i];
		   }
		   queue = temp;
		   return;
	   }
	   
	   //if the number of items is <= 1/4 the array size this method reduces size of array by 1/2
	   private void reduceQueueSize(int newSize) {
		   Item[] temp = (Item[]) new Object[newSize];
		   for (int i = 0; i < newSize; i++) {
			   temp[i] = queue[i];
		   }
		   queue = temp;
		   return;
	   }
	   //add new Item to the array
	   public void enqueue(Item item) {
		   if ( item == null ) throw new java.lang.NullPointerException();
		   queue[itemCount] = item;
		   
	       itemCount++;
	 
		   if (itemCount == queue.length) doubleQueueSize(queue.length * 2);
		   return;
	   }
	   
	   //after removing a random item with dequeue,  restructures the array so there are no null locations from [0...itemCount)
	   private void restructureQue(int nullLocation) {
		   for (int i = nullLocation; i < itemCount; i++) {
			   queue[i] = queue[i+1];
		   }
		   itemCount--;
		   return;
	   }
	   
	   //removes a random item frome the array using StdRandom lib
	   public Item dequeue() {
		   if (isEmpty()) throw new java.util.NoSuchElementException();
		   
		   int random = StdRandom.uniform(itemCount);
		   Item item = queue[random];
		   restructureQue(random);
		   if (itemCount <= queue.length / 8) reduceQueueSize(queue.length / 2);
		   return item;
	   }
	   
	   /**public void pprint() {
		   for (int i = 0; i < itemCount; i++) {
			   System.out.print(queue[i] + " - ");
		   }
		   System.out.println();
	   }*/
	   
	   //same as dequeue without actually removing the item from the array
	   public Item sample()  {
		   if (isEmpty()) throw new java.util.NoSuchElementException();
		   
		   int random = StdRandom.uniform(itemCount);
		   
		   return queue[random];
		   
	   }
	   
	   public Iterator<Item> iterator() {
		   return new RandomQueueIterator();
	   }
	   
	   private class RandomQueueIterator implements Iterator<Item> {
		   private int iterLocation;
		   
		   public RandomQueueIterator() {
			   iterLocation = 0;
			   StdRandom.shuffle(queue, 0, itemCount-1);
		   }
		   
		   public boolean hasNext() {
			   return queue[iterLocation] != null;
		   }
		   
		   public void remove() {
			   throw new java.lang.UnsupportedOperationException();
		   }
		   public Item next() {
			   if (! hasNext() ) throw new java.util.NoSuchElementException();
			   Item temp = queue[iterLocation];
			   iterLocation++;
			   return temp;   
		   }
		   
		   
	   }
	   
	   public static void main(String[] args)  {
		   RandomizedQueue rq = new RandomizedQueue<>();
		   
		   for (int i = 0; i < 25; i++) {
			   rq.enqueue(i);
			   if (i % 4 == 0) {
				   System.out.println("DQ : " + rq.dequeue());
			   }
			   //rq.pprint();
		   }
		   
		   Iterator iter = rq.iterator();
		   Iterator iter2 = rq.iterator();
		  
		   
		   while (iter.hasNext() && iter2.hasNext()) {
			   System.out.print(iter.next() + " ^ " + iter2.next() + " ||");
			   
		   }
		   
		   
		   
	   }
}
