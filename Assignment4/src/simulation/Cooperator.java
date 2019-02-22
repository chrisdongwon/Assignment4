package simulation;

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
