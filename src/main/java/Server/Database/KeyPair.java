package Server.Database;

public class KeyPair<K, V> {
    public K key;
    public V value;


    public KeyPair(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
