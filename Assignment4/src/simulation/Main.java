package simulation;

public class Main {

  public static void main(String[] args) {
    PartialCooperator p = new PartialCooperator();
    
    for(int i = 0; i < 10; i++)
      System.out.println(p.cooperates());
  }
}
