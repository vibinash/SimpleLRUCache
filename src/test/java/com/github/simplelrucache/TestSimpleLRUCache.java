package com.github.simplelrucache;

import com.github.simplelrucache.SimpleLRUCache;
import com.github.simplelrucache.SimpleLRUCache.DoublyLinkedList;
import com.github.simplelrucache.SimpleLRUCache.DoublyLinkedNode;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
/*
 * Added very basic tests just to make sure the cache is working
 * for basic inputs
 * //TODO Need to add more extensive tests
 */
public class TestSimpleLRUCache {
	SimpleLRUCache<String, String> socialWebsiteCache;
	
	@BeforeClass
	public void init(){
		socialWebsiteCache = new SimpleLRUCache<String, String>(3);
	}
	
	@Test
	public void testAddingToCache(){
		socialWebsiteCache.addItem("facebook", "thefacebook");
		socialWebsiteCache.addItem("google", "google+");
		socialWebsiteCache.addItem("yahoo", "tumblr");
		
		assertEquals(socialWebsiteCache.getSize(), 3);
		System.out.println(socialWebsiteCache.toString());
	}
	
	@Test(dependsOnMethods={"testAddingToCache"})
	public void testRetreivingFromCache(){
		socialWebsiteCache.getItem("google");
		assertEquals(socialWebsiteCache.queue.peek().data, "google+");
		System.out.println(socialWebsiteCache.toString());
		
		socialWebsiteCache.addItem("oldgoogle", "orkut"); 
		assertEquals(socialWebsiteCache.getSize(), 3);
		System.out.println(socialWebsiteCache.toString());
		
		socialWebsiteCache.getItem("oldgoogle");
		assertEquals(socialWebsiteCache.queue.peek().data, "orkut");
		System.out.println(socialWebsiteCache.toString());
	}
	
	@Test(dependsOnMethods={"testRetreivingFromCache"})
	public void testClearingTheCache() {
		socialWebsiteCache.clearAll();
		assertEquals(socialWebsiteCache.getSize(), 0);
		System.out.println(socialWebsiteCache.toString());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testAddingAndRemovingFromList() {
		DoublyLinkedList<Integer> list = new DoublyLinkedList<Integer>();
		DoublyLinkedNode one = new DoublyLinkedNode(1, null, null);
		DoublyLinkedNode two = new DoublyLinkedNode(2, null, null);
		DoublyLinkedNode three = new DoublyLinkedNode(3, null, null);
		DoublyLinkedNode four = new DoublyLinkedNode(4, null, null);
		DoublyLinkedNode five = new DoublyLinkedNode(5, null, null);
		
		list.add(one);
		list.add(two);
		list.add(three);
		list.add(four);
		list.add(five);
		
		list.remove(five);
		list.remove(one);
		list.remove(three);
		list.remove(two);
		list.remove(four);
	
		System.out.println(list.toString());
	}
}
