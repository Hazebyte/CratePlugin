package com.hazebyte.utils.tree;

import java.util.Collection;
import java.util.Map;

/**
 * A trie is an efficient reTRIEval data structure. A trie is a tree
 * that stores strings. The maximum number of children of a node is the size
 * of a alphabet.
 */
public interface Trie<V> {

    /**
     * Insert a value with the following key. Inserting a key that already exists will replace the existing value.
     * @param key
     * @param value
     */
    void insert(String key, V value);

    /**
     * Performs an exact search of the key.
     * @param key
     * @return
     */
    V searchExact(String key);

    /**
     * Performs an index search with a key.
     * @param prefix
     * @return all values that start with the prefix
     */
    Collection<V> startsWith(String prefix);

    /**
     * Remove an index from the trie.
     * @param key
     * @return true if an index is successfully removed.
     */
    boolean delete(String key);

    Map<String, V> getMap();
}
