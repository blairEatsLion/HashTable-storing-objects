package hashMap;

import java.util.ArrayList;
import java.util.Iterator;


class MyHashTable<K,V> {
	/*
	 *   Number of entries in the HashTable. 
	 */
	private int entryCount = 0;

	/*
	 * Number of buckets. The constructor sets this variable to its initial value,
	 * which eventually can get changed by invoking the rehash() method.
	 */
	private int numBuckets;

	/**
	 * Threshold load factor for rehashing.
	 */
	private final double MAX_LOAD_FACTOR=0.75;

	/**
	 *  Buckets to store lists of key-value pairs.
	 *  Traditionally an array is used for the buckets and
	 *  a linked list is used for the entries within each bucket.   
	 *  We use an Arraylist rather than an array, since the former is simpler to use in Java.   
	 */

	ArrayList< HashLinkedList<K,V> >  buckets;

	/* 
	 * Constructor.
	 * 
	 * numBuckets is the initial number of buckets used by this hash table
	 */

	MyHashTable(int numBuckets) {

		//  ADD YOUR CODE BELOW HERE
		//arrayList of size
		this.numBuckets = numBuckets;
		buckets = new ArrayList< HashLinkedList<K,V> >(numBuckets);
		//initialize the contents in each bucket
		for(int i=0;i<numBuckets;i++){
			buckets.add(new HashLinkedList<K,V>()); 
		}			
		
		//  ADD YOUR CODE ABOVE HERE

	}

	/**
	 * Given a key, return the bucket position for the key. 
	 */
	private int hashFunction(K key) {

		return  Math.abs( key.hashCode() ) % numBuckets ;
	}

	/**
	 * Checking if the hash table is empty.  
	 */

	public boolean isEmpty()
	{
		if (entryCount == 0)
			return true;
		else
			return(false);
	}

	/**
	 *   return the number of entries in the hash table.
	 */

	public int size()
	{
		return(entryCount);
	}

	/**
	 * Adds a key-value pair to the hash table. If the load factor goes above the 
	 * MAX_LOAD_FACTOR, then call the rehash() method after inserting. 
	 * 
	 *  If there was a previous value for the given key in this hashtable,
	 *  then overwrite it with new value and return the old value.
	 *  Otherwise return null.   
	 */

	public  V  put(K key, V value) {

		//  ADD YOUR CODE BELOW HERE	
		
		int index = hashFunction(key);
//	System.out.println(index);//correct
		V oldValue = null;
		//find the right bucket
		HashLinkedList<K,V> list = buckets.get(index);
		
		if(list.getListNode(key)!=null){ //when there was a previous value 
			oldValue = list.getListNode(key).getValue();
			list.getListNode(key).setValue(value);
		}else{
			//create a new node with the given key and value		
			list.add(key, value);  
			entryCount++;
		}
		
		double toCompare = (double)entryCount/(double)numBuckets;
//	System.out.println(toCompare); //correct
		if(toCompare > MAX_LOAD_FACTOR){
			rehash();
		}

		//  ADD YOUR CODE ABOVE HERE
		return oldValue;
	}

	/**
	 * Retrieves a value associated with some given key in the hash table.
     Returns null if the key could not be found in the hash table)
	 */
	public V get(K key) {

		//  ADD YOUR CODE BELOW HERE
		if(key==null){
			throw new NullPointerException();
		}		
		int index = hashFunction(key);
		//for bucket at index, look for key
		HashLinkedList<K,V> list = buckets.get(index);
		V value = null;
		if(list.getListNode(key)!=null){
			value = list.getListNode(key).getValue();
		}

		//  ADD YOUR CODE ABOVE HERE

		return value;
	}

	/**
	 * Removes a key-value pair from the hash table.
	 * Return value associated with the provided key.   If the key is not found, return null.
	 */
	public V remove(K key) {

		//  ADD YOUR CODE BELOW HERE
		if(key.equals(null)){ //
			throw new NullPointerException();
		}

		int index = hashFunction(key);
		HashLinkedList<K,V> list = buckets.get(index);
		
		V value = null; //
		if(list.getListNode(key) == null){ 
			return null; //if the key is not found
		}
		else{
			value = list.getListNode(key).getValue(); 
			list.remove(key);//remove the node 
			entryCount--;
		}	

		//  ADD  YOUR CODE ABOVE HERE

		return value;
	}

	/*
	 *  This method is used for testing rehash().  Normally one would not provide such a method. 
	 */

	public int getNumBuckets(){
		return numBuckets;
	}

	/*
	 * Returns an iterator for the hash table. 
	 */

	public MyHashTable<K, V>.HashIterator  iterator(){
		return new HashIterator();
	}

	/*
	 * Removes all the entries from the hash table, 
	 * but keeps the number of buckets intact.
	 */
	public void clear()
	{
		for (int ct = 0; ct < buckets.size(); ct++){
			buckets.get(ct).clear();
		}
		entryCount=0;		
	}

	/**
	 *   Create a new hash table that has twice the number of buckets.
	 */


	public void rehash()
	{
		//   ADD YOUR CODE BELOW HERE
		
		//double number of buckets

		Iterator<HashNode<K,V>> iterator = new HashIterator();
		
		for(int i = 0;i < this.numBuckets; i++){
			this.buckets.add(new HashLinkedList<K,V>());
		}
		numBuckets = 2*getNumBuckets(); 
		
		this.clear();
		
		//add node to new bucket position
		while(iterator.hasNext()){
			HashNode<K,V> theNode = iterator.next();
			put(theNode.getKey(),theNode.getValue());
		}
	
		
		//   ADD YOUR CODE ABOVE HERE

	}


	/*
	 * Checks if the hash table contains the given key.
	 * Return true if the hash table has the specified key, and false otherwise.
	 */

	public boolean containsKey(K key)
	{
		int hashValue = hashFunction(key);
		if(buckets.get(hashValue).getListNode(key) == null){
			return false;
		}
		return true;
	}

	/*
	 * return an ArrayList of the keys in the hashtable
	 */

	public ArrayList<K>  keys()
	{

		ArrayList<K>  listKeys = new ArrayList<K>();

		//   ADD YOUR CODE BELOW HERE
		
		Iterator<HashNode<K,V>> iterator = new HashIterator();
		while(iterator.hasNext()){
			HashNode<K,V> theNode = iterator.next();
			listKeys.add(theNode.getKey());
//			System.out.println("Printing"+theNode.getKey().toString()); //test: all correct
		}


		//   ADD YOUR CODE ABOVE HERE

		return listKeys;  
	}

	/*
	 * return an ArrayList of the values in the hashtable
	 */
	public ArrayList <V> values()
	{
		ArrayList<V>  listValues = new ArrayList<V>();

		//   ADD YOUR CODE BELOW HERE
		
		Iterator<HashNode<K,V>> iterator = new HashIterator();
//		int i=0; //for testing
		while(iterator.hasNext()){
			HashNode<K,V> theNode = iterator.next();
			listValues.add(theNode.getValue());
//			System.out.println("Printing now"+listValues.get(i++).toString()); //test: all correct
		}
		
		//   ADD YOUR CODE ABOVE HERE

		return listValues; 
	}

	@Override
	public String toString() {
		/*
		 * Implemented method. You do not need to modify.
		 */
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buckets.size(); i++) {
			sb.append("Bucket ");
			sb.append(i);
			sb.append(" has ");
			sb.append(buckets.get(i).size());
			sb.append(" entries.\n");
		}
		sb.append("There are ");
		sb.append(entryCount);
		sb.append(" entries in the hash table altogether.");
		return sb.toString();
	}

	/*
	 *    Inner class:   Iterator for the Hash Table.
	 */
	public class HashIterator implements  Iterator<HashNode<K,V> > {
		HashLinkedList<K,V>  allEntries;
		
		/**
		 * Constructor:   make a linkedlist (HashLinkedList) 'allEntries' of all the entries in the hash table
		 */
		public  HashIterator()
		{

			//  ADD YOUR CODE BELOW HERE
		
			allEntries=new HashLinkedList<K,V>();
			
			for(int i=0;i<numBuckets;i++){
				HashNode<K,V> cur = buckets.get(i).getHead();
				
				if(cur==null){
					continue;
				}
				while(cur!=null){
					K key = cur.getKey();
					V value = cur.getValue();
					allEntries.add(key, value);
					cur = cur.next; 	
				}
			}
//			System.out.println("Printing"+allEntries.firstNode().next.next.next.next.next.toString());//
			//test correct: all entries are in the list
			
			//  ADD YOUR CODE ABOVE HERE
			

		}

		//  Override
		@Override
		public boolean hasNext()
		{
			return !allEntries.isEmpty();
		}

		//  Override
		@Override
		public HashNode<K,V> next()
		{
			return allEntries.removeFirst();
		}

		@Override
		public void remove() {
			// not implemented,  but must be declared because it is in the Iterator interface

		}		
	}

}
