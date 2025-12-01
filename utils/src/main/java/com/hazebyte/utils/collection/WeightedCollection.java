package com.hazebyte.utils.collection;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class WeightedCollection<E> {

  private TreeMap<Double, E> map = new TreeMap<>();
  private final Random random;
  private double total = 0;

  public WeightedCollection() {
    random = new Random();
  }

  public void add(double weight, E item) {
    // 0 is a valid entry since it is "un-pickable".
    if (weight <= 0) return;
    total += weight;
    map.put(total, item);
  }

  public E next() {
    if (total == 0) {
      return null;
    }
    double value = random.nextDouble() * total;
    return map.higherEntry(value).getValue();
  }

  public Map.Entry nextEntry() {
    double value = random.nextDouble() * total;
    return map.higherEntry(value);
  }

  public int size() {
    return map.size();
  }

}
