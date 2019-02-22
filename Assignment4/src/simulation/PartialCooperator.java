package simulation;

// source: https://docs.oracle.com/javase/8/docs/api/java/util/Random.html

import java.util.Random;

public class PartialCooperator extends Organism {
  public PartialCooperator() {
    super();
  }
  
  @Override
  public String getType() {
    return "PartialCooperator";
  }

  @Override
  public Organism reproduce() {
    this.energy = 0;
    Random rand = new Random();
    if (rand.nextDouble() < 0.05)
      return rand.nextBoolean() ? new Cooperator() : new Defector();
    return new PartialCooperator();
  }

  @Override
  public double getCooperationProbability() {
    return 0.5;
  }

  @Override
  boolean cooperates() {
    Random seed = new Random();
    return 0 == (seed.nextInt() % 2);
  }
}
