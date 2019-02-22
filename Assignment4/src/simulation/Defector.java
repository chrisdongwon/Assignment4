package simulation;

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
