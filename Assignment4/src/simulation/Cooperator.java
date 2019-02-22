package simulation;

import java.util.Random;

public class Cooperator extends Organism {
  public Cooperator() {
    super();
  }
  
  @Override
  public String getType() {
    return "Cooperator";
  }

  @Override
  public Organism reproduce() {
    this.energy = 0;
    Random rand = new Random();
    if (rand.nextDouble() < 0.05)
      return rand.nextBoolean() ? new Defector() : new PartialCooperator();
    return new Cooperator();
  }

  @Override
  public double getCooperationProbability() {
    return 1.0;
  }

  @Override
  boolean cooperates() {
    return true;
  }
}
