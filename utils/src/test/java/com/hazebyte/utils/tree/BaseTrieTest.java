package com.hazebyte.utils.tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseTrieTest {

    private BaseTrie<String> trie;
    final String key1 = "key1", key2 = "key2", key3 = "random", simple1 = "a";
    final String value1 = "value1", value2 = "value2", value3 = "value3", simpleValue1 = "simple1";

    @BeforeEach
    public void setup() {
        trie = new BaseTrie<>();
        trie.insert(key1, value1);
        trie.insert(key2, value2);
        trie.insert(key3, value3);
        trie.insert(simple1, simpleValue1);
    }

    @Nested
    class Constructor {

        @Test
        public void shouldCreateSimpleTrie() {
            Trie trie = new BaseTrie();
            assertNotNull(trie);
        }

    }

    @Nested
    class Insert {

        @Test
        public void shouldHaveOneChildrenWithSingleInsert() {
            BaseTrie trie = new BaseTrie();
            trie.insert("key1", "value1");
            assertNotNull(trie);
            assertNotNull(trie.root);
            assertNotNull(trie.root.children);
            assertEquals(1, trie.root.children.size());
        }

        @Test
        public void shouldHaveOneChildrenWithSimilarStartingKey() {
            BaseTrie trie = new BaseTrie();
            trie.insert("key1", "will");
            trie.insert("key2", "test");
            assertEquals(1, trie.root.children.size());
        }

        @Test
        public void shouldHaveTwoChildrenWithDifferentStartingKey() {
            BaseTrie trie = new BaseTrie();
            trie.insert("key1", "will");
            trie.insert("key2", "test");
            trie.insert("rey", "test2");
            assertEquals(2, trie.root.children.size());
        }

        @Test
        public void shouldAssignEndingWithValue() {
            BaseTrie<String> trie = new BaseTrie();
            char c = 'a';
            String value = "123";
            trie.insert("a", value);
            assertEquals(trie.root.children.get(c).ch, c);
            assertTrue(trie.root.children.get(c).isEndOfWord);
            assertEquals(trie.root.children.get(c).getValue(), value);
        }

        @Test
        public void shouldNotAssignNonEndingWithValue() {
            BaseTrie<String> trie = new BaseTrie();
            String value = "123";
            trie.insert("key1", value);
            assertFalse(trie.root.children.get('k').isEndOfWord);
            assertNull(trie.root.children.get('k').getValue());
        }

    }

    @Nested
    class Search {

        @Test
        public void shouldReturnNullWithInvalidKey() {
            String resp = trie.searchExact(null);
            assertNull(resp);
        }

        @Test
        public void shouldReturnWithValue() {
            String resp = trie.searchExact(key1);
            assertEquals(value1, resp);
        }

        @Test
        public void shouldReturnNullWithNonExactKey() {
            String resp = trie.searchExact("key");
            assertNull(resp);
        }

    }

    @Nested
    public class startsWith {

        @Test
        public void shouldReturnNullWithInvalidKey() {
            Collection<String> resp = trie.startsWith(null);
            assertNull(resp);
        }

        @Test
        public void shouldReturnTwoValues() {
            Collection<String> resp = trie.startsWith("key");
            assertEquals(2, resp.size());
            assertTrue(resp.contains(value1));
            assertTrue(resp.contains(value2));
        }

    }

    @Nested
    public class Delete {

        @Test
        public void shouldNotBeDeleted() {
            char c = simple1.toCharArray()[0];
            assertTrue(trie.root.children.get(c).isEndOfWord);
        }

        @Test
        public void shouldBeDeleted() {
            trie.delete(simple1);
            char c = simple1.toCharArray()[0];
            assertFalse(trie.root.children.get(c).isEndOfWord);
        }

    }

    @Nested
    class Mapping {

        @Test
        public void shouldReturnMapping() {
            Map<String, String> map = trie.getMap();
            assertEquals(value1, map.get(key1));
            assertEquals(value2, map.get(key2));
            assertEquals(value3, map.get(key3));
            assertEquals(simpleValue1, map.get(simple1));
        }

    }

    @Nested
    class Equals {
        @Test
        void shouldReturnTrueForEqualTries() {
            BaseTrie<String> trie1 = new BaseTrie<>();
            BaseTrie<String> trie2 = new BaseTrie<>();

            trie1.insert("test", "value1");
            trie1.insert("testing", "value2");

            trie2.insert("test", "value1");
            trie2.insert("testing", "value2");

            assertEquals(trie1, trie2);
        }

        @Test
        void shouldReturnFalseForDifferentTries() {
            BaseTrie<String> trie1 = new BaseTrie<>();
            BaseTrie<String> trie2 = new BaseTrie<>();

            trie1.insert("test", "value1");
            trie2.insert("test", "value2");

            assertNotEquals(trie1, trie2);
        }

        @Test
        void shouldReturnFalseForNull() {
            BaseTrie<String> trie = new BaseTrie<>();
            trie.insert("test", "value");

            assertNotEquals(null, trie);
        }

        @Test
        void shouldReturnFalseForDifferentType() {
            BaseTrie<String> trie = new BaseTrie<>();
            trie.insert("test", "value");

            assertNotEquals("string", trie);
        }

        @Test
        void shouldReturnTrueForSameInstance() {
            BaseTrie<String> trie = new BaseTrie<>();
            trie.insert("test", "value");

            assertEquals(trie, trie);
        }
    }

    @Nested
    class HashCode {
        @Test
        void shouldReturnSameHashCodeForEqualTries() {
            BaseTrie<String> trie1 = new BaseTrie<>();
            BaseTrie<String> trie2 = new BaseTrie<>();

            trie1.insert("test", "value1");
            trie1.insert("testing", "value2");

            trie2.insert("test", "value1");
            trie2.insert("testing", "value2");

            assertEquals(trie1.hashCode(), trie2.hashCode());
        }

        @Test
        void shouldReturnDifferentHashCodeForDifferentValues() {
            BaseTrie<String> trie1 = new BaseTrie<>();
            BaseTrie<String> trie2 = new BaseTrie<>();

            trie1.insert("test", "value1");
            trie2.insert("test", "value2");

            assertNotEquals(trie1.hashCode(), trie2.hashCode());
        }

        @Test
        void shouldReturnConsistentHashCode() {
            BaseTrie<String> trie = new BaseTrie<>();
            trie.insert("test", "value");

            int hash1 = trie.hashCode();
            int hash2 = trie.hashCode();

            assertEquals(hash1, hash2);
        }
    }

}