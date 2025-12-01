package com.hazebyte.utils.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.function.Predicate;

public class WeightedCollectionTest {

  @Test
  public void canConstructWeightedCollection() {
    WeightedCollection<String> collection = new WeightedCollection<>();
    Assertions.assertNotNull(collection);
  }

  @Test
  public void canAddItems() {
    WeightedCollection<String> collection = new WeightedCollection<>();
    collection.add(10, "hello");
    collection.add(10, "test");
    collection.add(10, "fun");
    Assertions.assertEquals(3, collection.size());
  }

  @Test
  public void canPickNextItem() {
    // arrange
    WeightedCollection<String> collection = new WeightedCollection<>();
    collection.add(10, "hello");
    collection.add(10, "test");
    collection.add(10, "fun");

    // act
    String item = collection.next();

    // assert
    Assertions.assertNotNull(item);
  }

  @Test
  public void canPickNextEntry() {
    // arrange
    WeightedCollection<String> collection = new WeightedCollection<>();
    collection.add(10, "hello");
    collection.add(10, "test");
    collection.add(10, "fun");

    // act
    Map.Entry item = collection.nextEntry();

    // assert
    Assertions.assertNotNull(item);
  }

  @Test
  public void nextEntry_shouldSucceed_neverPickingZeroEntries() {
    // arrange
    WeightedCollection<String> collection = new WeightedCollection<>();
    collection.add(0, "hello");
    collection.add(10, "test");
    collection.add(0, "hell");

    // act
    String pick = collection.next();

    // assert
    Assertions.assertNotNull(pick);
    Assertions.assertTrue(!pick.startsWith("hell"));
  }

  @Test
  public void verifyUniformDistribution() {
    WeightedCollection<Integer> collection = new WeightedCollection<>();
    collection.add(10, 0);
    collection.add(10, 1);
    collection.add(10, 2);
    int[] bucket = new int[3];

    int total = 100000;

    for (int i = 0; i < total; i++) {
      bucket[collection.next()]++;
    }

    double approx = 0.33;
    double errorThreshold = 0.01;
    Predicate<Double> doesNotSurpassErrorThreshold = item -> Math.abs(approx - item) < errorThreshold;
    for (int i = 0; i < bucket.length; i++) {
      double itemChance = (double) bucket[i] / total;
      Assertions.assertTrue(doesNotSurpassErrorThreshold.test(itemChance));
    }
  }
}
