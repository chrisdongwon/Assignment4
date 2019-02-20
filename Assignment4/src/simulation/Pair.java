package simulation;

public class Pair<T,U> {
  T left;
  U right;

  public Pair(T left, U right) {
    this.left = left;
    this.right = right;
  } // Pair

  public T getLeft() {
    return this.left;
  }

  public U getRight() {
    return this.right;
  }
} // class Pair
