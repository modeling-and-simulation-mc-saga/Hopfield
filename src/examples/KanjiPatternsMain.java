package examples;

import java.io.BufferedWriter;
import java.io.IOException;
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
     */
    public static void main(String[] args) throws IOException {
        int maxT = 100;
        KanjiPatterns pattern = new KanjiPatterns();
        int numPatterns = pattern.getNumPatterns();
        double lambda = 1. / pattern.getSize();
        Random myRandom = new Random(29874L);
        Hopfield sys = new Hopfield(pattern, lambda, myRandom);
        try ( BufferedWriter out 
                = Hopfield.openWriter("kanjiPatterns.txt")) {
            for (int t = 0; t < maxT; t++) {
                sys.updateZero();
                double m[] = sys.overlap();
                double energy = sys.getEnergy();
                StringJoiner sj = new StringJoiner(" ");
                for (double x : m) {
                    sj.add(String.valueOf(x));
                }
                String str = t + " " + sj.toString() + " " + energy;
                out.write(str);
                out.newLine();
            }
        }

        sys.randomize();
        double temperature = 10;
        maxT = 1000;
        int tt = 0;
        try ( BufferedWriter out = 
                Hopfield.openWriter("kanjiPatternsFinite.txt")) {
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
                    out.write(str);
                    out.newLine();
                    tt++;
                }
                temperature /= 2.;
            }
        }
    }

}
