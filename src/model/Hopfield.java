package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Random;

/**
 * Hopfield model
 *
 * @author tadaki
 */
public class Hopfield {

    private final AbstractPatterns patterns;
    private final double J[];//interaction
    private final int numNeurons;
    private final Random random;
    private final Neuron neurons[];
    private int numPatterns;
    private double lambda;

    public Hopfield(AbstractPatterns patterns, double lambda,
            Random random) {
        this.patterns = patterns;
        numNeurons = patterns.size;
        J = new double[numNeurons * numNeurons];
        numPatterns = patterns.numPatterns;
        this.lambda = lambda;
        generateJ(numPatterns);
        this.random = random;
        neurons = new Neuron[numNeurons];
        for (int i = 0; i < numNeurons; i++) {
            neurons[i] = new Neuron();
            if (random.nextBoolean()) {
                neurons[i].setFire();
            }
        }
    }

    /**
     * randomize states of neurons
     */
    public void randomize() {
        for (int i = 0; i < numNeurons; i++) {
            if (random.nextBoolean()) {
                neurons[i].setFire();
            } else {
                neurons[i].setRest();
            }
        }
    }

    /**
     * generate interactions from patterns to be retrieved
     *
     * @param numPatterns
     */
    private void generateJ(int numPatterns) {
        if (numPatterns < 1 || numPatterns > patterns.numPatterns) {
            numPatterns = patterns.numPatterns;
        }
        this.numPatterns = numPatterns;
        for (int i = 0; i < numNeurons * numNeurons; i++) {
            J[i] = 0.;
        }
        for (int k = 0; k < numPatterns; k++) {
            for (int j = 0; j < patterns.getSize(); j++) {
                for (int i = 0; i < patterns.getSize(); i++) {
                    int r = j * numNeurons + i;
                    J[r] += patterns.getValue(k, j)
                            * patterns.getValue(k, i);
                }
                J[j * numNeurons + j] = 0.;//対角成分をゼロに
            }
        }
        for (int j = 0; j < J.length; j++) {
            double v = J[j] * lambda;
            J[j] = v;
        }

    }

    /**
     * compute overlap between neuron states and patterns
     *
     * @return
     */
    public double[] overlap() {
        double m[] = new double[numPatterns];
        for (int i = 0; i < numPatterns; i++) {
            for (int j = 0; j < patterns.getSize(); j++) {
                m[i] += patterns.getValue(i, j)
                        * neurons[j].getState().value();
            }
            m[i] /= numNeurons;
        }
        return m;
    }

    /**
     * 温度ゼロの更新
     */
    public void updateZero() {
        for (int t = 0; t < numNeurons; t++) {
            int j = random.nextInt(numNeurons);
            updateOneZero(j);
        }
    }

    private void updateOneZero(int j) {
        double h = 0;
        for (int i = 0; i < numNeurons; i++) {
            h += J[j * numNeurons + i] * neurons[i].getState().value();
        }
        if (h >= 0.) {
            neurons[j].setFire();
        } else {
            neurons[j].setRest();
        }
    }

    /**
     * 有限温度での更新
     *
     * @param temperature
     */
    public void update(double temperature) {
        for (int t = 0; t < numNeurons; t++) {
            int j = random.nextInt(numNeurons);
            updateOne(j, temperature);
        }
    }

    private void updateOne(int j, double temperature) {
        double u = 0;
        double p;
        for (int i = 0; i < numNeurons; i++) {
            int r = j * numNeurons + i;
            u += J[r] * neurons[i].getState().value();
        }
        switch (neurons[j].getState()) {
            case Fire:
                p = 1. / (1 + Math.exp(2 * u / temperature));
                if (random.nextDouble() < p) {
                    neurons[j].setRest();
                }
                break;
            case Rest:
                p = 1. / (1 + Math.exp(-2 * u / temperature));
                if (random.nextDouble() < p) {
                    neurons[j].setFire();
                }
                break;
            default:
                break;
        }
    }

    public double getEnergy() {
        double en = 0.;
        for (int j = 0; j < numNeurons; j++) {
            for (int i = 0; i < numNeurons; i++) {
                int r = j * numNeurons + i;
                en -= J[r] * neurons[i].getState().value()
                        * neurons[j].getState().value();
            }
        }
        return en / 2.;
    }

    public int getValue(int k) {
        return neurons[k].getState().value();
    }

    public void setNumPatterns(int numPatterns) {
        generateJ(numPatterns);
    }

    public static BufferedWriter openWriter(String filename)
            throws FileNotFoundException {
        FileOutputStream fStream = new FileOutputStream(new File(filename));
        return new BufferedWriter(new OutputStreamWriter(fStream));
    }

}
