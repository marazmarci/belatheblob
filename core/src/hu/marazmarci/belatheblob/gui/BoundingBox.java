package hu.marazmarci.belatheblob.gui;

import com.badlogic.gdx.math.Vector3;
import hu.marazmarci.belatheblob.Prog3HF;

@Prog3HF
public class BoundingBox {

    float x;
    float y;
    float width;
    float height;

    public BoundingBox(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isPointInside(Vector3 touchPoint) {
        return isPointInside(touchPoint.x, touchPoint.y);
    }

    private boolean isPointInside(float px, float py) {
        return this.x <= px && this.x + this.width >= px && this.y <= py && this.y + this.height >= py;
    }

}
