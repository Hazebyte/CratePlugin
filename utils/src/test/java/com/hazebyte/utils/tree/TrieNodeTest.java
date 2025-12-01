package com.hazebyte.utils.tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrieNodeTest {

    @Test
    public void shouldCreateTrieNode() {
        char c = 'c';
        TrieNode node = new TrieNode(c);
        assertNotNull(node);
        assertEquals(c, node.ch);
    }
}
