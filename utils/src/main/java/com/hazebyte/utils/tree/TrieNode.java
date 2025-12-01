package com.hazebyte.utils.tree;

import java.util.HashMap;
import java.util.Map;

/**
 * Every node of a Trie consists of multiple branches. These branches represents the possible
 * set of words. A TrieNode is used to represent the nodes of the English Alphabet
 */
class TrieNode<V> {

    protected char ch;

    protected Map<Character, TrieNode<V>> children;

    protected boolean isEndOfWord;

    protected V value;

    protected TrieNode() {
        children = new HashMap<>();
    }

    protected TrieNode(char ch) {
        this.ch = ch;
        children = new HashMap<>();
    }

    protected V getValue() {
        return value;
    }
}
