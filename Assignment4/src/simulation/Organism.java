package simulation;

//source: https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html

public abstract class Organism {
  protected int energy;
  
  public Organism() {
    this.energy = 0;
  }
  
  public void update() {
    this.energy++;
  }
  
  public int getEnergy() {
    return this.energy;
  }
  
  public void incrementEnergy() {
    this.energy++;
  }
  
  public void decrementEnergy() {
    this.energy = Math.max(0, this.energy - 1);
  }
  
  abstract String getType();
  abstract Organism reproduce();
  abstract double getCooperationProbability();
  abstract boolean cooperates();
}
