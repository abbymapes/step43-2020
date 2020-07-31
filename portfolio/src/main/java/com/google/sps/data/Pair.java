package com.google.sps.data;

public class Pair<K, V> {
  private K key;
  private V value;

  public Pair(K key, V value) {
    this.key = key;
    this.value = value;
  }

  public K getKey() {
    return this.key;
  }

  public V getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return "key: " + this.key + "\nvalue: " + this.value;
  }
}
