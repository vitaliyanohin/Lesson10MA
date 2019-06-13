import java.util.Arrays;

public class HashMapTest<T, K> {

  private int currentCapacity;
  private int size;
  private Node<T, K>[] arrayOfHashMapTest;
  private Node<T, K> currentNode;
  private Node<T, K> nodeBuffer;
  private static final float DEFAULT_LOAD_FACTOR = 0.75f;
  private static final int DEFAULT_CAPACITY = 16;

  public void put(T key, K value) {
    if (isEmpty()) {
      arrayInitialization();
    }
    if (size > (currentCapacity * DEFAULT_LOAD_FACTOR)) {
      arrayIncrease();
    }
    currentNode = new Node<>(key, value);
    int index = (currentNode.hashKey() % currentCapacity);
    if (arrayOfHashMapTest[index] == null) {
      arrayOfHashMapTest[index] = currentNode;
      size++;
    } else if (!currentNode.getKey().equals(arrayOfHashMapTest[index].getKey())) {
      if (arrayOfHashMapTest[index].getNextNode() == null) {
        arrayOfHashMapTest[index].setNextNode(currentNode);
      } else {
        nodeBuffer = arrayOfHashMapTest[index].getNextNode();
        nodeBuffer.setNextNode(currentNode);
        nodeBuffer = null;
      }
    }
  }

  public K get(T key) {
    int index = key.hashCode() % currentCapacity;
    if (arrayOfHashMapTest[index].getKey().equals(key)) {
      return arrayOfHashMapTest[index].getValue();
    } else {
      currentNode = arrayOfHashMapTest[index].getNextNode();
      while (true) {
        if (key.equals(currentNode.getKey())) {
          return currentNode.getValue();
        }
        currentNode = currentNode.getNextNode();
      }
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
      arrayOfHashMapTest = new Node[DEFAULT_CAPACITY];
      currentCapacity = DEFAULT_CAPACITY;
      for (int i = 0; i < DEFAULT_CAPACITY; i++) {
        arrayOfHashMapTest[i] = null;
      }
    }
  }

  private void arrayIncrease() {
    Node<T, K>[] tempArray = Arrays.copyOf(arrayOfHashMapTest, currentCapacity);
    for (int i = 0; i < currentCapacity; i++) {
      arrayOfHashMapTest[i] = null;
    }
    int oldCapacity = currentCapacity;
    currentCapacity = currentCapacity << 1;
    arrayOfHashMapTest = Arrays.copyOf(arrayOfHashMapTest, currentCapacity);
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
          currentNode = currentNode.getNextNode();
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
