package Server.Database;

import java.io.Serializable;

public class KeyPair<K, V> implements Serializable{
    public K key;
    public V value;


    public KeyPair(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
