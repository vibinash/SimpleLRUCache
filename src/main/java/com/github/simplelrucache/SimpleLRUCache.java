package com.github.simplelrucache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * A simple implementation of Least Recently Used Cache. This current 
 * implementation uses a queue and a map to store the keys and values 
 * respectively.
 * 
 * The queue is of a fixed size so if there is an addItem() invoked when 
 * the queue has reached the maximum size, the least used element in the 
 * queue (at the tail) is removed to make room for the new item.
 * 
 * The map stores the key and the reference of the value in the queue.
 * This is used because we would prefer an O(1) removal of the item
 * and not prefer a sequential find of the element from the start of the 
 * queue and then remove it. 
 */
public class SimpleLRUCache<K,V> {
	// Default start size is 10
	private final static int DEFAULT_SIZE_LIMIT = 10;
	private int sizeLimit;
	DoublyLinkedList<V> queue;
	Map<K, DoublyLinkedNode<V>> map; 
	
	public SimpleLRUCache(){
		this(DEFAULT_SIZE_LIMIT);
	}
	
	public SimpleLRUCache(int size){
		this.sizeLimit = size;
		queue = new DoublyLinkedList<V>();
		map = new HashMap<K, DoublyLinkedNode<V>>();
	}
	
	/*
	 * Adds the item to the front of the queue and if the size has reached
	 * the limit, it would remove the least recently used item and make room
	 * for the new item.
	 */
	public boolean addItem(K key, V value){
		if (queue.size() < sizeLimit) {
			if (key != null && (map.get(key) == null || map.get(key).data != value)){
				DoublyLinkedNode<V> refValue = new DoublyLinkedNode<V>(value);
				map.put(key, refValue);
				queue.add(refValue);
				return true;
			} 
		} else {
			//remove the Least Used Value (from the tail)
			queue.remove(queue.tail);
			return addItem(key, value);
		}
		return false;
	}
	
	/*
	 * Checks if the item is at the front of the queue, if not removes the
	 * item from the current position and adds it to the front of the queue
	 * and returns the value
	 */
	public V getItem(K key){
		if (key != null && map.containsKey(key)){
			DoublyLinkedNode<V> ref = map.get(key);
			if (queue.peek() != ref){
				queue.remove(ref);
				queue.add(ref);
				return ref.data;
			} else{
				return queue.peek().data;
			}
		}
		return null;
	}
	
	public Set<K> getKeySet(){
		return this.map.keySet();
	}
	
	public Set<V> getValues(){
		Set<V> result = new HashSet<V>();
		DoublyLinkedNode<V> current = queue.head;
		while(current != null){
			result.add(current.data);
			current = current.next;
		}
		return result;
	}
	
	/*
	 * Clears all the data in cache
	 */
	public void clearAll(){
		int len = queue.size();
		for(int i=0; i<len; i++) {
			queue.remove(queue.head);
		}
	}
	
	public int getSize(){
		return queue.size();
	}
	
	public String toString(){
		return queue.toString();
	}
	
	public static class DoublyLinkedNode<V> {
		public V data;
		public DoublyLinkedNode<V> next;
		public DoublyLinkedNode<V> previous;
		
		public DoublyLinkedNode(V data){
			this(data, null, null);
		}
		
		public DoublyLinkedNode(V data, DoublyLinkedNode<V> next, DoublyLinkedNode<V> prev){
			this.data = data;
			this.next = null;
			this.previous = null;
		}
		
		public String toString(){
			return this.data + ((this.next != null) ? " -> " : "");
		}
	}
	
	public static class DoublyLinkedList<V> {
		public DoublyLinkedNode<V> head;
		public DoublyLinkedNode<V> tail;
		private int size;
		
		public DoublyLinkedList() {
			head = null;
			tail = null;
			size =0;
		}
		
		// Add to the head of the queue
		public void add(DoublyLinkedNode<V> node){
			if (head == null){
				head = node;
				tail = head;
				head.next = null;
				head.previous = null;
			} else {
				DoublyLinkedNode<V> oldHead = head;
				head.previous = node;
				head = head.previous;
				head.next = oldHead;
			}
			size++;
		}
		
		// Removes the element from the list
		public void remove(DoublyLinkedNode<V> elem){
			if (elem == head){
				DoublyLinkedNode<V> oldFront = head;
				head = head.next;
				oldFront.next = null;
				if (head != null){
					head.previous = null;
				}
			} else if (elem == tail){
				tail = tail.previous;
				tail.next.previous = null;
				tail.next = null;
			} else {
				elem.previous.next = elem.next;
				elem.next.previous = elem.previous;
				elem.next = null;
				elem.previous = null;
			}
			size--;
		}
		
		public DoublyLinkedNode<V> peek(){
			return head;
		}
		
		public int size(){
			return this.size;
		}
		
		public String toString(){
			if (this.head != null){
				StringBuffer s = new StringBuffer();
				DoublyLinkedNode<V> current = this.head;
				while(current != null){
					s.append(current.toString());
					current = current.next;
				}
				return s.toString();
			}
			return "";
		}
	}
	
	public static void main(String[] args) {
		SimpleLRUCache<String, String> SocialWebsiteCache = new SimpleLRUCache<String, String>(3);
		SocialWebsiteCache.addItem("facebook", "thefacebook");
		SocialWebsiteCache.addItem("google", "google+");
		SocialWebsiteCache.addItem("yahoo", "tumblr");
		System.out.println(SocialWebsiteCache.toString());
		
		SocialWebsiteCache.getItem("google");
		System.out.println(SocialWebsiteCache.toString());
		
		SocialWebsiteCache.addItem("oldgoogle", "orkut"); 
		System.out.println(SocialWebsiteCache.toString());
		
		SocialWebsiteCache.getItem("oldgoogle");
		System.out.println(SocialWebsiteCache.toString());
		
		SocialWebsiteCache.clearAll();
		System.out.println(SocialWebsiteCache.toString());
	}
}
