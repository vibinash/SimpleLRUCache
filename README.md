# SimpleLRUCache
Implements a Simple Least Recently Used Cache in Java

About SimpleLRUCache
========

A simple implementation of Least Recently Used Cache. This current 
implementation uses a queue and a map to store the keys and values 
respectively.
 
The queue is of a fixed size so if there is an addItem() invoked when 
the queue has reached the maximum size, the least used element in the 
queue (at the tail) is removed to make room
 
The map stores the key and the reference of the value in the queue.
This is used because we would prefer an O(1) removal of the item
and not prefer a sequential find of the element from the start of the 
queue and finally remove it. 

API Reference
========

- `SimpleLRUCache()`
- `SimpleLRUCache(initialSize)`
- `boolean addItem(K key, V value)`
- `V getItem(K key)`
- `Set<K> getKeySet()`
- `Set<V> getValues()`
- `void clearAll()`
- `int getSize()`
- `String toString()`
