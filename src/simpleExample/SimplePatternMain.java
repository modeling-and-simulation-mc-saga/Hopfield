package simpleExample;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;
import model.AbstractPatterns;
import model.Hopfield;

/**
 *
 * @author tadaki
 */
public class SimplePatternMain {

    public static void main(String args[]) throws IOException {
        int maxT = 100;
        SimplePattern pattern = new SimplePattern();
        double lambda = 1. / pattern.getSize();
        long seed = 48L;
        Random myRandom = new Random(seed);
        Hopfield sys = new Hopfield(pattern, lambda, myRandom);
        try ( BufferedWriter out = 
                Hopfield.openWriter("SimplePattern.txt")) {
            for (int t = 0; t < maxT; t++) {
                sys.updateZero();
                //パターンとのoverlapを求める
                //Energyを求める
                //時間t, パターンとのoverlap、エネルギーをoutへ出力
            }
        }
    }
}
