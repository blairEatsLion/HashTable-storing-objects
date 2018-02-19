package hashMap;


public class HashLinkedList<K,V>{
	/*
	 * Fields
	 */
	private HashNode<K,V> head;

	private Integer size;

	/*
	 * Constructor
	 */

	HashLinkedList(){
		head = null;
		size = 0;
	}


	/*
	 *Add (Hash)node at the front of the linked list
	 */

	public void add(K key, V value){
		// ADD CODE BELOW HERE
		HashNode<K,V> newNode = new  HashNode<K,V>(key, value);
		size++;
		HashNode<K,V> tail = null;
		
		if(head==null){		
			head = newNode;
			tail = head; //
		}else{
			newNode.next = head;
			head = newNode;
		}
		// ADD CODE ABOVE HERE
	}

	/*
	 * Get Hash(node) by key
	 * returns the node with key
	 */

	public HashNode<K,V> getListNode(K key){
		// ADD CODE BELOW HERE
		HashNode<K,V> current = head;
		while(current != null){
			if(current.getKey().equals(key)){ //string compare?
				return current; 
			}
			current = current.next; //modified
		}	
		return null;
		
		// ADD CODE ABOVE HERE
	}


	/*
	 * Remove the head node of the list
	 * Note: Used by remove method and next method of hash table Iterator
	 */

	public HashNode<K,V> removeFirst(){
		// ADD CODE BELOW HERE
		if(head!=null){    //
			size--;
			HashNode<K,V> cur = head;
			head = head.next;
		// ADD CODE ABOVE HERE
			return cur; 
		}
		return null;
	}
	

	/*
	 * Remove Node by key from linked list 
	 */

	public HashNode<K,V> remove(K key){
		// ADD CODE BELOW HERE
		
		HashNode<K,V> cur = this.head;
		
		HashNode<K,V> p;
		HashNode<K,V> save;
		
		if(this.head == null) {
			return null;
		}
		
		if(cur.getKey().equals(key)){ //
			save = removeFirst();
			return save;
		}	
		else{
			while(cur.next != null){
				if(cur.next.getKey().equals(key)){
					size--;	
					save = cur.next;
					p = cur.next;
					cur.next = p.next;		
					
					return save;
				}
				cur = cur.next;
			}
			return null;
		}
	}

//		// ADD CODE ABOVE HERE


	/*
	 * Delete the whole linked list
	 */
	public void clear(){
		head = null;
		size = 0;
	}
	/*
	 * Check if the list is empty
	 */

	boolean isEmpty(){
		return size == 0? true:false;
	}

	int size(){
		return this.size;
	}

	//ADD YOUR HELPER  METHODS BELOW THIS
	
	public HashNode<K,V> getHead(){	
		return this.head;
	}
	
	
	public HashNode<K,V> getNodeAtIndex(int index){
		HashNode<K,V> cur = head;
		for(int i=0;i<index;i++){
			cur = cur.next;
		}
		return cur;
	}
	
	
	
//	public V setValue(V newValue){
//		V oldV = value;
//		value = newValue;
//		return oldV;
//	}
	

	//ADD YOUR HELPER METHODS ABOVE THIS


}
