package hu.marazmarci.belatheblob.handlers.input;

import hu.marazmarci.belatheblob.Prog3HF_TODO;

@Prog3HF_TODO
@Deprecated
public class TouchPointWithPointerInt {

    public int x;
    public int y;
    public int pointer;
    public int button;

    public TouchPointWithPointerInt(int x, int y, int pointer, int button) {
        set(x, y, pointer, button);
    }

    public TouchPointWithPointerInt set(int x, int y, int pointer, int button) {
        set(x, y);
        this.button = button;
        this.pointer = pointer;
        return this;
    }

    public TouchPointWithPointerInt set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

}
