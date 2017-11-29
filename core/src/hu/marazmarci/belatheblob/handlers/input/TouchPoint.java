package hu.marazmarci.belatheblob.handlers.input;

@Deprecated
public class TouchPoint {

    /*public*/ private int x;
    /*public*/ private int y;
    /*public*/ private int button;

    /*public*/ private TouchPoint(int x, int y, int button) {
        set(x, y, button);
    }

    /*public*/ private TouchPoint set(int x, int y, int button) {
        set(x, y);
        this.button = button;
        return this;
    }

    /*public*/ private TouchPoint set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public String toString() {
        return "TouchPoint{" + x + ", " + y + '}';
    }
}
