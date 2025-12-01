package com.hazebyte.utils.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * This is a basic implementation of a trie.
 * BaseTrie supports search, insert, and delete operations in O(L) time
 * where L is the size of the key.
 *
 * @param <V> the type of object to store.
 */
public class BaseTrie<V> implements Trie<V> {

    /**
     * The root node.
     */
    protected TrieNode<V> root;

    /**
     * BaseTrie constructor.
     */
    public BaseTrie() {
        root = new TrieNode();
    }

    /**
     * The implementation.
     * @param key
     * @param value
     */
    @Override
    public void insert(final String key, final V value) {
        if (key == null || key.length() == 0) {
            return;
        }

        TrieNode<V> current = root;

        for (Character c: key.toCharArray()) {
            // If the children map is missing the char, add in a new TrieNode and return it.
            current = current.children.computeIfAbsent(c, character -> new TrieNode(character));
        }

        current.value = value;
        current.isEndOfWord = true;
    }

    private TrieNode searchPrefix(final String key) {
        if (key == null) {
            return null;
        }

        TrieNode<V> current = root;

        // iterate through all key characters
        for (Character c: key.toCharArray()) {
            if (!current.children.containsKey(c)) {
                return null;
            }

            current = current.children.get(c);
        }

        return current;
    }

    @Override
    public V searchExact(final String key) {
        if (key == null) {
            return null;
        }

        TrieNode<V> current = searchPrefix(key);

        if (current != null && current.isEndOfWord) {
            return current.value;
        }

        return null;
    }

    @Override
    public Collection<V> startsWith(final String prefix) {
        if (prefix == null) {
            return null;
        }

        // starting trie node to begin indexing
        TrieNode<V> current = searchPrefix(prefix);
        if (current == null) {
            return null;
        }

        // the values that are valid part of the prefix
        List<V> values = new ArrayList<>();

        // queue to hold all children to iterate through
        Queue<TrieNode> queue = new LinkedList<>();
        // add the starting node
        queue.add(current);

        while (!queue.isEmpty()) {
            TrieNode<V> currentNode = queue.poll();
            // add all of the children nodes
            queue.addAll(currentNode.children.values());

            // add to the return list if it is a valid end of word
            if (currentNode.isEndOfWord) {
                values.add(currentNode.value);
            }
        }

        return values;
    }

    /**
     * This implementation uses a lazy delete where we just set isEndOfWord
     * to false. The nodes are still valid and may be a memory hindrance.
     */
    @Override
    public boolean delete(final String key) {
        if (key == null || key.length() == 0) {
            return false;
        }

        TrieNode<V> node = searchPrefix(key);

        if (node != null && node.isEndOfWord) {
            node.value = null;
            node.isEndOfWord = false;
            return true;
        }

        return false;
    }

    private Map<String, V> dfs(TrieNode<V> node, String word, Map<String, V> words) {
        if (node.isEndOfWord) {
            words.put(word + node.ch, node.value);
        }

        for (TrieNode n: node.children.values()) {
            String newWord = node.ch == 0 ? word : word + node.ch;
            dfs(n, newWord, words);
        }
        return words;
    }

    @Override
    public Map<String, V> getMap() {
        return dfs(root, "", new HashMap<>());
    }

    @Override
    public int hashCode() {
        int hashCode = 1;

        // TODO: TrieNode should implement hashCode and equals
        Queue<TrieNode> levels = new LinkedList<>();
        levels.add(root);

        while (!levels.isEmpty()) {
            TrieNode<V> current = levels.poll();
            levels.addAll(current.children.values());

            hashCode = 31 * hashCode + (current.value != null ? current.value.hashCode() : 0);
        }
        return hashCode;
    }

    /**
     * The equals function is currently based on hashCode and may return the
     * incorrect value based on the hashCode function. This will be updated.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof BaseTrie)) {
            return false;
        }

        BaseTrie trie = (BaseTrie) obj;
        return trie.hashCode() == this.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BaseTrie{\n");

        Queue<TrieNode> levels = new LinkedList<>();
        levels.add(root);

        while (!levels.isEmpty()) {
            int levelSize = levels.size();
            for (int i = 0; i < levelSize; i++) {
                TrieNode<V> current = levels.poll();
                builder.append(current.children.keySet());
                levels.addAll(current.children.values());
                builder.append(String.format("TrieNode{value=%s,isEndOfWord=%s} ", current.value, current.isEndOfWord));
            }
            builder.append("\n");
        }

        builder.append("}");
        return builder.toString();
    }
}
