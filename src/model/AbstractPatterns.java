package model;

/**
 *
 * @author tadaki
 */
public class AbstractPatterns {

    protected final int size;
    protected final int numPatterns;
    protected int p[][];

    public AbstractPatterns(int size, int numPatterns) {
        this.size = size;
        this.numPatterns = numPatterns;
        p = new int[numPatterns][];
    }

    public int getValue(int k, int j) {
        return 2 * p[k][j] - 1;
    }

    public int getSize() {
        return size;
    }

    public int getNumPatterns() {
        return numPatterns;
    }

}
