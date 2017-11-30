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

    public boolean isPointInside(float px, float py) {
        return this.x <= px && this.x + this.width >= px && this.y <= py && this.y + this.height >= py;
    }

    public BoundingBox clone() {
        return new BoundingBox(x,y,width,height);
    }

    public void expand(int border) {
        x -= border;
        y -= border;
        height += 2 * border;
        width  += 2 * border;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoundingBox that = (BoundingBox) o;
        if (x != that.x) return false;
        if (y != that.y) return false;
        if (width != that.width) return false;
        return height == that.height;
    }
}
