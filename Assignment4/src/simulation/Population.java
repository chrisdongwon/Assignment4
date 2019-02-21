package simulation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class Population {
  private Organism[] organism;
  
  public Population(Pair<String, Integer>[] counts) throws IllegalArgumentException {
    LinkedList<Organism> list = new LinkedList<>();

    for (Pair<String, Integer> pair : counts) {
      for (int i = 0; i < pair.right; i++) {
        if (pair.left.equals("Cooperator")) {
          list.add(new Cooperator());
        } else if (pair.left.equals("Defector")) {
          list.add(new Defector());
        } else if (pair.left.equals("PartialCooperator")) {
          list.add(new PartialCooperator());
        } else {
          throw new IllegalArgumentException("non-exist organism: " + pair.getLeft());
        }
      }
    }
        
    organism = (Organism[]) list.toArray();
  }
  
  public void update() {
    Random rand = new Random();
    for (int i = 0; i < organism.length; i++) {
      /* update all of the organism */
      organism[i].update();
      
      /* check cooperation */
      if (organism[i].cooperates() && organism[i].getEnergy() >= 1) {
        // cooperate with other organism
        organism[i].decrementEnergy();
        int[] randOrg = getRandomNumExcept(organism.length, 8, i);
        for (int j = 0; j < randOrg.length; j++) {
          organism[randOrg[j]].incrementEnergy();
        }
      }
      
      /* check reproduce */
      if (organism[i].getEnergy() >= 10) {
        int organismToReplace = rand.nextInt(organism.length + 1);
        organism[organismToReplace] = organism[i].reproduce();
      }
    }
  }
  
  // to do: overflow potential
  public double calculateCooperationMean() {
    double average = 0.0;
    for (int i = 0; i < organism.length; i++) {
      average += organism[i].getCooperationProbability();
    }
    return average / organism.length;
  }
  
  public Pair<String, Integer>[] getPopulationCounts() {
    /* map all organism into a dictionary */
    HashMap<String, Integer> dic = new HashMap<String, Integer>();
    for (int i = 0; i < organism.length; i++) {
      String orgName = organism[i].getType();
      if (dic.containsKey(orgName)) {
        Integer num = dic.get(orgName);
        dic.put(orgName, num + 1);
      } else {
        dic.put(orgName, 1);
      }
    }
    
    /* generate pair array */
    Pair<String, Integer>[] orgPair = (Pair<String, Integer>[]) new Object[dic.keySet().size()];
    int index = 0;
    for (String name : dic.keySet()) {
      Integer num = dic.get(name);
      orgPair[index] = new Pair<String, Integer>(name, num);
      index++;
    }
    
    return orgPair;
  }
  
  private static int[] getRandomNumExcept(int scope, int num, int except) {
    int[] randArray = new int[num];
    Random rand = new Random();
    
    for (int i = 0; i < num; i++) {
      int randInt = rand.nextInt(scope + 1);
      while (contains(randArray, randInt) || randInt == except) {
        randInt = rand.nextInt(scope + 1);
      }
      randArray[i] = randInt;
    }
    
    return randArray;
  }
  
  private static boolean contains(int[] array, int val) {
    for (int i = 0; i < array.length; i++) {
      if (array[i] == val)
        return true;
    }
    return false;
  }
}

