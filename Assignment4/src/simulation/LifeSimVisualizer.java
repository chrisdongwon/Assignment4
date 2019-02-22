package simulation;

public class LifeSimVisualizer {

    // delay for draw, in miliseconds
    private static final int DELAY = 100;

    //draw function
    public static void draw(Population population, int height, int width) {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(-0.05 * height, 1.05 * height);
        StdDraw.setYscale(-0.05 * width, 1.05 * height);

        for (int h = 1; h <= height; h++)
            for (int w = 1; w <= width; w++) {
                String name = population.getName(h - 1, w - 1);
                if (name.equals("Cooperator")) {
                    StdDraw.setPenColor(StdDraw.BLUE);
                } else if (name.equals("Defector")) {
                    StdDraw.setPenColor(StdDraw.RED);
                } else if (name.equals("PartialCooperator")) {
                    StdDraw.setPenColor(StdDraw.MAGENTA);
                } else {
                    StdDraw.setPenColor(StdDraw.GRAY);
                }
                StdDraw.filledSquare(w - 0.5, height - h + 0.5, 0.45);
            }
    }

    public static void main(String[] args) {
        int iteration = Integer.valueOf(args[0]);
        int numOfCooperator = Integer.valueOf(args[1]);
        int numOfDefector = Integer.valueOf(args[2]);
        int numOfPartial = Integer.valueOf(args[3]);
        int height = Integer.valueOf(args[4]);
        int width = Integer.valueOf(args[5]);

        Pair<String, Integer>[] pairs = (Pair<String, Integer>[]) new Pair[3];
        pairs[0] = new Pair<String, Integer>("Cooperator", numOfCooperator);
        pairs[1] = new Pair<String, Integer>("Defector", numOfDefector);
        pairs[2] = new Pair<String, Integer>("PartialCooperator", numOfPartial);

        StdDraw.enableDoubleBuffering();
        Population population = new Population(pairs, height, width);
        draw(population, height, width);
        StdDraw.show();
        StdDraw.pause(DELAY);
        for (int i = 0; i < iteration; i++) {
            population.update();
            draw(population, height, width);
            StdDraw.show();
            StdDraw.pause(DELAY);
        }
    }
}


