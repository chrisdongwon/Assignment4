package simulation;

import java.util.Random;

public class Defector extends Organism {
  public Defector() {
    super();
  }
  
  @Override
  public String getType() {
    return "Defector";
  }

  @Override
  public Organism reproduce() {
    this.energy = 0;
    Random rand = new Random();
    if (rand.nextDouble() < 0.05)
      return rand.nextBoolean() ? new Cooperator() : new PartialCooperator();
    return new Defector();
  }

  @Override
  public double getCooperationProbability() {
    return 0.0;
  }

  @Override
  boolean cooperates() {
    return false;
  }
}
