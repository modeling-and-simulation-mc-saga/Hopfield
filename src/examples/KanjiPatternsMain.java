package examples;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;
import java.util.StringJoiner;
import model.*;

/**
 *
 * @author tadaki
 */
public class KanjiPatternsMain {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        int maxT = 100;
        KanjiPatterns pattern = new KanjiPatterns();
        double lambda = 1. / pattern.getSize();
        Random myRandom = new Random(29874L);
        Hopfield sys = new Hopfield(pattern, lambda, myRandom);
        try ( PrintStream out = new PrintStream("kanjiPatterns.txt")) {
            for (int t = 0; t < maxT; t++) {
                sys.updateZero();
                double m[] = sys.overlap();
                double energy = sys.getEnergy();
                StringJoiner sj = new StringJoiner(" ");
                for (double x : m) {
                    sj.add(String.valueOf(x));
                }
                String str = t + " " + sj.toString() + " " + energy;
                out.println(str);
            }
        }

        sys.randomize();
        double temperature = 10;
        maxT = 1000;
        int tt = 0;
        try ( PrintStream out = new PrintStream("kanjiPatternsFinite.txt")) {
            for (int k = 0; k < 10; k++) {
                for (int t = 0; t < maxT; t++) {
                    sys.update(temperature);
                    double m[] = sys.overlap();
                    double energy = sys.getEnergy();
                    StringJoiner sj = new StringJoiner(" ");
                    for (double x : m) {
                        sj.add(String.valueOf(x));
                    }
                    String str = tt + " " + sj.toString() + " " + energy;
                    out.println(str);
                    tt++;
                }
                temperature /= 2.;
            }
        }
    }

}
