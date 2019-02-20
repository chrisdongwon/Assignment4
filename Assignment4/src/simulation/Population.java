package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {
  private Organism[] organism;
  
  public Population(Pair<String, Integer>[] counts) throws IllegalArgumentException {
    List<Organism> arrayList = new ArrayList<>();

    for (Pair<String, Integer> pair : counts) {
      for (int i = 0; i < pair.getRight(); i++) {
        if (pair.getLeft().equals("Cooperator")) {
          arrayList.add(new Cooperator());
        } else if (pair.getLeft().equals("Defector")) {
          arrayList.add(new Defector());
        } else if (pair.getLeft().equals("PartialCooperator")) {
          arrayList.add(new PartialCooperator());
        } else {
          throw new IllegalArgumentException("non-exist organism: " + pair.getLeft());
        }
      }
    }
    
    organism = (Organism[])arrayList.toArray();
  }
  
  public void update() {
    for (int i = 0; i < organism.length; i++) {
      organism[i].update();
      if (organism[i].cooperates()) {
        organism[i].decrementEnergy();
        
      }
    }
  }
  
  private static int[] getNonDuplicateRandom(int capacity, int num) {
    int[] random = new int[capacity];
    for (int i = 0; i < capacity; i++) {
      random[i] = i;
    }
    
    Random r = new Random();
    for (int i = random.length - 1; i >= 0; i--) {
      int indexToSwap = r.nextInt(i + 1);
      swap(random, indexToSwap, i);
    }
  }
  
  
}

