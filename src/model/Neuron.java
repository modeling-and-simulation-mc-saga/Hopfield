package model;

/**
 * Model neuron
 *
 * @author tadaki
 */
public class Neuron {

    public static enum State {
        Fire(1), Rest(-1);
        private final int v;

        State(int v) {
            this.v = v;
        }

        public int value() {
            return v;
        }
    }
    private State state;

    public Neuron(State state) {
        this.state = state;
    }

    public Neuron() {
        this.state = State.Rest;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setFire() {
        state = State.Fire;
    }

    public void setRest() {
        state = State.Rest;
    }

}
