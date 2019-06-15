import java.util.Arrays;
import java.util.NoSuchElementException;

public class HashMapTest<T, K> {

  private static final float DEFAULT_LOAD_FACTOR = 0.75f;
  private static final int DEFAULT_CAPACITY = 16;
  private Node<T, K>[] values;
  private Node<T, K> currentNode;
  private Node<T, K> nodeBuffer;
  private int currentCapacity;
  private int size;

  public void put(T key, K value) {
    if (isEmpty()) {
      arrayInitialization();
    }
    if (size > (currentCapacity * DEFAULT_LOAD_FACTOR)) {
      arrayIncrease();
    }
    currentNode = new Node<>(key, value);
    int index = (currentNode.hashKey() % currentCapacity);
    if (values[index] == null) {
      values[index] = currentNode;
      size++;
    } else if (values[index].getNextNode() == null || currentNode.getKey().equals(values[index].getKey())) {
      if (currentNode.getKey().equals(values[index].getKey())) {
        values[index].setValue(currentNode.getValue());
      } else {
        values[index].setNextNode(currentNode);
      }
    } else {
      nodeBuffer = values[index].getNextNode();
      while (nodeBuffer != null) {
        if (currentNode.getKey().equals(nodeBuffer.getKey())) {
          nodeBuffer.setValue(currentNode.getValue());
          return;
        }
        if (nodeBuffer.getNextNode() == null) {
          nodeBuffer.setNextNode(currentNode);
          return;
        }
        nodeBuffer = nodeBuffer.getNextNode();
      }
    }
  }

  public K get(T key) {
    int index = key.hashCode() % currentCapacity;
    if (values[index].getKey().equals(key)) {
      return values[index].getValue();
    } else {
      currentNode = values[index].getNextNode();
      while (currentNode != null) {
        if (key.equals(currentNode.getKey())) {
          return currentNode.getValue();
        }
        currentNode = currentNode.getNextNode();
      }
      throw new NoSuchElementException();
    }
  }

  public int size() {
    return size;
  }

  private boolean isEmpty() {
    return size == 0;
  }

  private void arrayInitialization() {
    if (currentCapacity == 0) {
      values = new Node[DEFAULT_CAPACITY];
      currentCapacity = DEFAULT_CAPACITY;
      for (int i = 0; i < DEFAULT_CAPACITY; i++) {
        values[i] = null;
      }
    }
  }

  private void arrayIncrease() {
    Node<T, K>[] tempArray = Arrays.copyOf(values, currentCapacity);
    for (int i = 0; i < currentCapacity; i++) {
      values[i] = null;
    }
    int oldCapacity = currentCapacity;
    currentCapacity = currentCapacity << 1;
    values = Arrays.copyOf(values, currentCapacity);
    size = 0;
    for (int i = 0; i < oldCapacity; i++) {
      if (tempArray[i] == null) {
        continue;
      }
      if (tempArray[i].getNextNode() != null) {
        nodeBuffer = tempArray[i].getNextNode();
        put(tempArray[i].getKey(), tempArray[i].getValue());
        while (nodeBuffer != null) {
          put(nodeBuffer.getKey(), nodeBuffer.getValue());
          nodeBuffer = nodeBuffer.getNextNode();
        }
      } else {
        put(tempArray[i].getKey(), tempArray[i].getValue());
      }
    }
  }

  private static class Node<T, K> {
    private T key;
    private K value;
    private Node<T, K> next;

    public Node(T key, K value) {
      this.key = key;
      this.value = value;
    }

    public void setNextNode(Node<T, K> object) {
      next = object;
    }

    public Node<T, K> getNextNode() {
      return this.next;
    }

    public int hashKey() {
      return key.hashCode();
    }

    public T getKey() {
      return key;
    }

    public K getValue() {
      return value;
    }

    public void setValue(K value) {
      this.value = value;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Node<?, ?> node = (Node<?, ?>) o;
      return key != null ? key.equals(node.key) : node.key == null;
    }

    @Override
    public int hashCode() {
      return key != null ? key.hashCode() : 0;
    }
  }
}
