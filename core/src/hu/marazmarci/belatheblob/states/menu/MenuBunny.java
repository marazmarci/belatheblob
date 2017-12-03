package hu.marazmarci.belatheblob.states.menu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.entities.enemies.Bunny;

/**
 * A menüben ugráló nyúl.
 */
@Prog3HF
public class MenuBunny {

    private Vector2 pos;
    private float time = 0;
    private float mouseDistance = 0;
    private boolean animationFrameSelector = false;
    private SpriteBatch spriteBatch;

    MenuBunny(SpriteBatch spriteBatch, float x, float y) {
        this.spriteBatch = spriteBatch;
        pos = new Vector2(x, y);
    }

    void moveTowardsPoint(float mouseX, float deltaTime, float speed) {
        mouseDistance = mouseX - pos.x;
        pos.x += mouseDistance * deltaTime * speed;
        time += deltaTime;
        if (mouseDistance < 0) mouseDistance *= -1;
    }

    public void render() {
        // 1 /(6f  + mouseDistance/10f)
        if (time > 10/(60f + mouseDistance)) {
            time = 0;
            animationFrameSelector = !animationFrameSelector;
        }
        spriteBatch.begin();
        spriteBatch.draw(Bunny.texs[animationFrameSelector ? 0 : 1], pos.x - 16, pos.y);
        spriteBatch.end();
    }

    public Vector2 getPos() {
        return pos;
    }
}