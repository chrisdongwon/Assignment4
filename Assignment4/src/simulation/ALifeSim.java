package simulation;

public class ALifeSim {
  public static void main(String[] args) {
    int iteration = Integer.valueOf(args[0]);
    int numOfCooperator = Integer.valueOf(args[1]);
    int numOfDefector = Integer.valueOf(args[2]);
    int numOfPartial = Integer.valueOf(args[3]);
    
    Pair<String, Integer>[] pairs = (Pair<String, Integer>[]) new Pair[3];
    pairs[0] = new Pair<String, Integer>("Cooperator", numOfCooperator);
    pairs[1] = new Pair<String, Integer>("Defector", numOfDefector);
    pairs[2] = new Pair<String, Integer>("PartialCooperator", numOfPartial);
    
    Population p = new Population(pairs, 10, 10);
    for (int i = 0; i < iteration; i++) {
      p.update();
    }
    Pair<String, Integer>[] results = p.getPopulationCounts();
    Integer finalCooperator = 0;
    Integer finalDefector = 0;
    Integer finalPartial = 0;
    for (Pair<String, Integer> org : results) {
      if (org.left.equals("Cooperator")) {
        finalCooperator = org.right;
      } else if (org.left.equals("Defector")) {
        finalDefector = org.right;
      } else if (org.left.equals("PartialCooperator")) {
        finalPartial = org.right;
      } else {
        System.out.println("CRITIAL ERROR!");
      }
    }
    
    System.out.println("After " + iteration + " ticks:");
    System.out.println("Cooperator = " + finalCooperator);
    System.out.println("Defector = " + finalDefector);
    System.out.println("PartialCooperator = " + finalPartial);
    System.out.println();
    System.out.println("Mean Cooperation Probability = " + p.calculateCooperationMean());    
  }
}
