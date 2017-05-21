
package adt;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class HashMap<K,V> implements Map<K,V>{ 
	
	int index = 0;
	int indexArray[]= new int[7];
	int collision = 0;
	private Node<K, V>[] table;
	int n = 7;
	int alt = -1;	
	int size = 0;
	
@SuppressWarnings("unchecked")
public HashMap()
{
	Node<K, V>[] table = new Node[n];
	
	this.table =  table;
}
@SuppressWarnings("unchecked")
public HashMap(int n)
{
	
	this.n = n;
	this.table = new Node[n];
	this.indexArray = new int[n];

}


@Override
public int size() {
    return size;
}

@Override
public boolean isEmpty() {
    return size() == 0;
}
@SuppressWarnings("unchecked")
public boolean containsKey(Object key) {
	
	int hash = hashCode((K) key);
	
	index = hash  % (n);
	
	if (table[index] == null)
		return false;
	if (table[index].key == key)
		return true;
	if (table[index].next == key)
		return true;
	
	return false;
}
/**
 * Checks if the hashmap contains the value given
 *
 * @param Object - Value
 * @return Boolean.
 */

@Override
public boolean containsValue(Object value) {

	for(int i = 0; i < size; i++){
		if (table[indexArray[i]].value == value)
			return true;
    }
    return false;
}

/**
 * Checks if 2 maps are equal
 *
 * @param Map - map of value
 * @return Boolean
 */
@SuppressWarnings("unchecked")
public boolean equals(Map m)
{
		
	return m.equals(entrySet());
	
}

/**
 * Gets the value at then specific key if its there
 *
 * @param registers - copy of register values
 * @return the value of the key in the map.
 */
@Override
public V get(Object key) {
    
	int hash = hashCode((K) key);
	
	index = hash  % (n);
	
	if (containsKey(key)){
	if (table[index].key == key)
		return table[index].value;
	if (table[index].next == key)
		return table[index].value;
	}
	return null;
	
}

/**
 * Puts a key and value into the map
 *
 * @param K key
 * @param V value
 * 
 * @return Null
 */
@Override
public V put(K key, V value) {
	
	if ((double) size / (double) n >= .8 )
	{
		resize();
	}
	
	Node<K, V> e = new Node<K, V>(key, value);
	
	//Node<K, V> fin = new Node<K, V>(key, value);
	
	
	int hash = hashCode(key);
	
	
	 index = hash  % (n);
	
	//System.out.println("the index is: " + index);
	
	if (table[index] == null)
	{
		table[index] = e;
		indexArray[size] = index;
		size++;
		return null;
	}
	else
	{
		Node<K,V> current = table[index];
		Node<K,V> prev = null;
		
		while(current !=  null)
		{
			if(current.key.equals(key))
			{
				if(prev == null)
				{
					e.next=current.next;
					table[index] = e;
					indexArray[size] = index;
					size++;
					return null;
				}
				else
				{
					e.next=current.next;
					prev.next = e;
					indexArray[size] = index;
					size++;
					return null;
				}
			}
			
			prev = current;
			current = current.next;
		}
		prev.next = e;
	}
	indexArray[size] = index;
    size++;
    return null;
}

/**
 * Removes an object from the hashmap using its key
 *
 * @param Key - key value of the object you want to remove
 * @return The value of the deleted key
 */
@Override
public V remove(Object key) {
	
	@SuppressWarnings("unchecked")
	int hash = hashCode((K) key);
	
	index = hash  % (n);
	
	
	Node<?, ?> temp = table[index];
	
	if (table[index] == null)
		return null;
	
	if (table[index].key == key)
	{
		table[index] = null;
		size--;
		return (V) temp.value;
	}
	
	if (table[index].next == key)
	{
		table[index].next = null;
		size--;
		return (V) temp.value;
	}
	
    return null;
}


@Override
public void putAll(Map<? extends K, ? extends V> m) {
    // TODO Auto-generated method stub
    
}

/**
 * Clears the table 
 *
 */
@Override
public void clear() {
	
	index = 0;
	collision = 0;
	table = new Node[7];
	 size = 0;
    
}

/**
 * Returns the set of keys in the hashmap
 *
 * @return Set<k> A Set of keys
 */
@SuppressWarnings("unchecked")
@Override
public Set<K> keySet() {
	
	K[] keyArray = (K[])new Object[size];
	
	for ( int i = 0; i < size; i++){
		keyArray[i] = ((table[indexArray[i]]).key);
	}
	Set<K> end = new HashSet<K>(Arrays.asList(keyArray));
	
	return end;
}

/**
 * returns a collection of values in the Hashmap
 *
 * 
 * @return Collect<V>, all values in the map
 */
@Override
public Collection<V> values() {
	@SuppressWarnings("unchecked")
	V[] valueArray = (V[])new Object[size];
	
	
	for ( int i = 0; i < size; i++)
	{
		valueArray[i] = ((table[indexArray[i]]).value);
	}
	
	Collection<V> end = new ArrayList<V>(Arrays.asList(valueArray));
	
	return end;
}

/**
 * Creates a number based on the key value in this case a sring
 *
 * @param K - key value
 * @return int from the key given
 */
public int hashCode(K k){
	int end = 0;
	
	String key = k.toString();
	
	for (int i = 0; i < key.length(); i++ ){
		end = end +  key.charAt(i) * (36 ^ (key.length() - i));
	}
	//System.out.println(end);
	return end;
	
}

/**
 * Resizes the hashtable
 */
public void resize(){
	
	int x = n * n;
		
	this.size=0;
	
	System.out.println("RESIZE: " + x);
	
	HashMap<K , V> end = new HashMap<K, V>(x);
	
	int i = 0;
	for ( ; i < table.length - 1; i++){
		
		if (table[i] == null)
		{
			//System.out.println("I is--- " + i + "\n");
			i++;
		}
		
		if (table[i].next == null)
		{
			end.put(table[i].key, table[i].value);
		}
		
		if (table[i] != null && table[i].next != null)		
		{
				Node<K, V> temp = table[i];
				while (temp.next != null )
				{
					end.put(temp.key, temp.value);
					//System.out.println("Collition" + temp.key);
					temp = temp.next;
				}
				
				end.put(temp.key, temp.value);
				//System.out.println("Collition  collision: " + temp.key);
		}
	}
		table = end.table;
		this.n = end.n;
		this.size = end.size;
		this.collision = end.collision;
		this.indexArray = end.indexArray;
		 
		
	}



@SuppressWarnings("unchecked")
@Override
public Set<java.util.Map.Entry<K, V>> entrySet() {
	@SuppressWarnings("unchecked")
	
	
	Map<K,V> map;
	
	Entry<K,V> temp;

	
	Set<java.util.Map.Entry<K, V>> set = new HashSet<java.util.Map.Entry<K, V>>();
	
	for ( int i = 0; i < size; i++)
	{
		temp = (java.util.Map.Entry<K, V>) table[indexArray[i]];
		set.add(temp);
	}
	
	
	return set;
}

}


/**
 * 
 * Node class
 * 
 */
class Node<K,V> implements Map.Entry<K, V>{
	
	K key;
	V value;
	Node<K, V> next;

	public Node(K key, V value, Node<K,V> n){
		this.key = key;
		this.value = value;
		this.next = n;
	}
	
	public Node(K key, V value){
		this.key = key;
		this.value = value;
	}
	
	public V setValue(V value) {
		this.value = value;
		return value;
	}
	
	public V getValue() {
		return value;
	}
	public K getKey() {
		return key;
	}
	public boolean hasNext()
	{
		return (next != null);
	}
	

}