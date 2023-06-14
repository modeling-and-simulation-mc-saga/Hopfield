package simpleExample;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;
import model.Hopfield;

/**
 *
 * @author tadaki
 */
public class SimplePatternMain {

    public static void main(String args[]) throws IOException {
        int maxT = 10;
        SimplePattern pattern = new SimplePattern();
        double lambda = 1. / pattern.getSize();
        long seed = 48L;
        Random myRandom = new Random(seed);
        Hopfield sys = new Hopfield(pattern, lambda, myRandom);
        try ( PrintStream out = new PrintStream("SimplePattern.txt")) {
            for (int t = 0; t < maxT; t++) {
                //one Monte Carlo step update at zero temperature

                //Overlapping between current and memorized patterns

                //Energy

                //Output of t, overlap, energy

                //out.println(str);
            }
        }
    }
}
