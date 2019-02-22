package simulation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class Population {
  private Organism[] organism;
  private Point[] orgToPoint;
  private int[][] pointToOrg;
  private int height;
  private int width;
  private int size; //current population size
  
  public Population(Pair<String, Integer>[] counts, int height, int width) throws IllegalArgumentException {
    /* Convert Pair into organism[] */
    organism = new Organism[height * width];
    int pointer = 0;
    for (Pair<String, Integer> pair : counts) {
      for (int i = 0; i < pair.right; i++) {
        if (pair.left.equals("Cooperator")) {
          organism[pointer++] = new Cooperator();
        } else if (pair.left.equals("Defector")) {
          organism[pointer++] = new Defector();
        } else if (pair.left.equals("PartialCooperator")) {
          organism[pointer++] = new PartialCooperator();
        } else {
          throw new IllegalArgumentException("non-exist organism: " + pair.getLeft());
        }
      }
    }
    this.size = pointer;

    /* establish org->point and point->org mapping */
    this.height = height;
    this.width = width;
    orgToPoint = new Point[height * width];
    pointToOrg = new int[height][width];
    /* intialize pointToOrg */
    for (int h = 0; h < pointToOrg.length; h++)
      for (int w = 0; w < pointToOrg[0].length; w++) {
        pointToOrg[h][w] = -1;
      }
    /* insert org into grid in randomly */
    LinkedList<Point> pointList = new LinkedList<>();
    Random rand = new Random();
    for (int i = 0; i < height; i++)
      for (int j = 0; j < width; j++) {
        pointList.add(new Point(i, j));
      }
    for (int i = 0; i < size; i++) {
      Point p = pointList.remove(rand.nextInt(pointList.size()));
      orgToPoint[i] = p;
      pointToOrg[p.getX()][p.getY()] = i;
    }
  }
  
  public void update() {
    Random rand = new Random();
    for (int i = 0; i < size; i++) {
      System.out.println("Organism Location: [" + orgToPoint[i].getX() + ", " + orgToPoint[i].getY() + "]");
      System.out.println("Name: " + organism[i].getType());
      System.out.println("Energy Level: " + organism[i].getEnergy());
      /* update all of the organism */
      organism[i].update();

      Point currentPoint = orgToPoint[i];
      /* check cooperation */
      if (organism[i].cooperates() && organism[i].getEnergy() >= 1) {
        // cooperate with other organism
        organism[i].decrementEnergy();

        /* update organism around it */
        int currentX = currentPoint.getX();
        int currentY = currentPoint.getY();
        for (int x = -1; x <= 1; x++)
          for (int y = -1; y <= 1; y++) {
            if (x != 0 && y != 0) {
              int xToUpdate = Math.abs((currentX + x) % height);
              int yToUpdate = Math.abs((currentY + y) % width);
              if (pointToOrg[xToUpdate][yToUpdate] != -1) {
                organism[pointToOrg[xToUpdate][yToUpdate]].incrementEnergy();
              }
            }
          }
      }
      
      /* check reproduce */
      if (organism[i].getEnergy() >= 10) {
        /* initialize point with least energy as a random point around current point */
//        int startX = Math.abs((currentPoint.getX() - 1) % height);
//        int startY = Math.abs((currentPoint.getY() - 1) % width);
//        Point pointWithLeastEnergy = new Point(startX, startY);

        /* find the point with least energy */

//        for (int x = -1; x <= 1; x++)
//          for (int y = -1; y <= 1; y++) {
//            if (x != 0 && y != 0) {
//              int xToCompare = Math.abs((currentPoint.getX() + x) % height);
//              int yToCompare = Math.abs((currentPoint.getY() + y) % width);
//              /* if this point has no organism on it, skip it */
//              if (pointToOrg[xToCompare][yToCompare] == -1) continue;
//              if (pointToOrg[pointWithLeastEnergy.getX()][pointWithLeastEnergy.getY()] == -1) {
//                pointWithLeastEnergy = new Point(xToCompare, yToCompare)
//              }
//              if (organism[pointToOrg[xToCompare][yToCompare]].getEnergy() <
//                      organism[pointToOrg[pointWithLeastEnergy.getX()][pointWithLeastEnergy.getY()]].getEnergy()) {
//                pointWithLeastEnergy = new Point(xToCompare, yToCompare);
//              }
//            }
//          }

        int leastEnergy = 100;
        Point pointWithLeastEnergy = new Point(-1, -1);
        LinkedList<Point> emptyPoint = new LinkedList<>();

        for (int x = -1; x <= 1; x++)
          for (int y = -1; y <= 1; y++)
            if (x != 0 || y != 0) {
              int thisX = Math.abs((currentPoint.getX() + x) % height);
              int thisY = Math.abs((currentPoint.getY() + y) % width);
              if (pointToOrg[thisX][thisY] == -1) {
                emptyPoint.add(new Point(thisX, thisY));
              } else {
                if (organism[pointToOrg[thisX][thisY]].getEnergy() < leastEnergy) {
                  leastEnergy = organism[pointToOrg[thisX][thisY]].getEnergy();
                  pointWithLeastEnergy = new Point(thisX, thisY);
                }
              }
            }

        System.out.println("Point with least energy: " + pointWithLeastEnergy.toString());
        /* replace the point with least energy */
        if (!emptyPoint.isEmpty()) {
          Point newOrg = emptyPoint.remove(rand.nextInt(emptyPoint.size()));
          System.out.println("number of empty point: " + emptyPoint.size());
          System.out.println("reproduce at: " + newOrg.toString());
          organism[size] = organism[i].reproduce();
          orgToPoint[size] = newOrg;
          pointToOrg[newOrg.getX()][newOrg.getY()] = size;
          size += 1;
        } else {
          organism[pointToOrg[pointWithLeastEnergy.getX()][pointWithLeastEnergy.getY()]] = organism[i].reproduce();
        }
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
    Pair<String, Integer>[] orgPair = (Pair<String, Integer>[]) new Pair[dic.keySet().size()];
    int index = 0;
    for (String name : dic.keySet()) {
      Integer num = dic.get(name);
      orgPair[index] = new Pair<String, Integer>(name, num);
      index++;
    }
    
    return orgPair;
  }

  public String getName(int x, int y) {
    if (pointToOrg[x][y] == -1) return "None";
    return organism[pointToOrg[x][y]].getType();
  }

  private static int[] getRandomNumExcept(int scope, int num, int except) {
    int[] randArray = new int[num];
    Random rand = new Random();
    
    for (int i = 0; i < num; i++) {
      int randInt = rand.nextInt(scope);
      while (contains(randArray, randInt) || randInt == except) {
        randInt = rand.nextInt(scope);
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

