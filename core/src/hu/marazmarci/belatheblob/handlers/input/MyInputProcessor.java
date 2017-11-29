package hu.marazmarci.belatheblob.handlers.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

import static hu.marazmarci.belatheblob.states.levels.Level1.height;
import static hu.marazmarci.belatheblob.states.levels.Level1.width;
import static hu.marazmarci.belatheblob.handlers.input.MyInput.*;

@Deprecated
public class MyInputProcessor extends InputAdapter {

    @Override
    public boolean mouseMoved(int x, int y) {
        return true;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
		/*jump = false;
		slinkinessMinus = false;
		slinkinessPlus = false;
		left = false;
		right = false;*/

        //System.out.println("touch!");
        //System.out.println(width+"x"+height);

        if(x < width/3) {
            if (x < width/6) {
                left = true;
                right = false;
            } else {
                right = true;
                left = false;
            }
        } else if (x < 2*width/3) {
            if (y < height/2) {
                slinkinessPlus = true;
            } else {
                slinkinessMinus = true;
            }
        } else if (x >= 2*width/3) {
            jump = true;
        }

        return true;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
		/*jump = false;
		slinkinessMinus = false;
		slinkinessPlus = false;
		left = false;
		right = false;*/

        if(x < width/3) {
            if (x < width/6) {
                left = true;
                right = false;
            } else {
                right = true;
                left = false;
            }
        } else if (x < 2*width/3) {
            if (y < height/2) {
                slinkinessPlus = true;
            } else {
                slinkinessMinus = true;
            }
        } else if (x > 2*width/3) {
            jump = true;
        }

        return true;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        if(x < width/3) {
            if (x < width/6) {
                left = false;
            } else {
                right = false;
            }
        } else if (x < 2*width/3) {
            if (y < height/2) {
                slinkinessPlus = false;
            } else {
                slinkinessMinus = false;
            }
        } else if (x > 2*width/3) {
            jump = false;
        }

        return true;
    }

    @Override
    public boolean keyDown(int k) {
        switch(k) {
            case Keys.RIGHT: MyInput.setKey(MyInput.BTN_RIGHT, true); MyInput.right = true; break;
            case Keys.LEFT: MyInput.setKey(MyInput.BTN_LEFT, true); MyInput.left = true; break;
            case Keys.SPACE: MyInput.setKey(MyInput.BTN_SPACE, true); MyInput.jump = true; break;
            case Keys.ENTER: MyInput.setKey(MyInput.BTN_ENTER, true); break;
            case Keys.UP: MyInput.setKey(MyInput.BTN_UP, true); MyInput.slinkinessPlus = true; break;
            case Keys.DOWN: MyInput.setKey(MyInput.BTN_DOWN, true); MyInput.slinkinessMinus = true; break;
            case Keys.X: MyInput.setKey(MyInput.BTN_X, true); MyInput.right = true; break;
            case Keys.Y: MyInput.setKey(MyInput.BTN_Y, true); MyInput.left = true; break;
			/*case Keys.W: MyInput.setKey(MyInput.BTN_W, true); break;
			case Keys.A: MyInput.setKey(MyInput.BTN_A, true); break;
			case Keys.S: MyInput.setKey(MyInput.BTN_S, true); break;
			case Keys.D: MyInput.setKey(MyInput.BTN_D, true); break;*/
            case Keys.TAB: MyInput.setKey(MyInput.BTN_TAB, true); break;
            case Keys.SHIFT_LEFT: MyInput.setKey(MyInput.BTN_SHIFT_LEFT, true); break;
            case Keys.CONTROL_RIGHT: MyInput.setKey(MyInput.BTN_CTRL_RIGHT, true); break;
            case Keys.CONTROL_LEFT: MyInput.setKey(MyInput.BTN_CTRL_LEFT, true); break;
            case Keys.SHIFT_RIGHT: MyInput.setKey(MyInput.BTN_SHIFT_RIGHT, true); break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int k) {
        switch(k) {
            case Keys.RIGHT: MyInput.setKey(MyInput.BTN_RIGHT, false); MyInput.right = false; break;
            case Keys.LEFT: MyInput.setKey(MyInput.BTN_LEFT, false); MyInput.left = false; break;
            case Keys.SPACE: MyInput.setKey(MyInput.BTN_SPACE, false); MyInput.jump = false; break;
            case Keys.ENTER: MyInput.setKey(MyInput.BTN_ENTER, false); break;
            case Keys.UP: MyInput.setKey(MyInput.BTN_UP, false); MyInput.slinkinessPlus = false; break;
            case Keys.DOWN: MyInput.setKey(MyInput.BTN_DOWN, false); MyInput.slinkinessMinus = false; break;
            case Keys.X: MyInput.setKey(MyInput.BTN_X, false); MyInput.right = false; break;
            case Keys.Y: MyInput.setKey(MyInput.BTN_Y, false); MyInput.left = false; break;
			/*case Keys.W: MyInput.setKey(MyInput.BTN_W, false); break;
			case Keys.A: MyInput.setKey(MyInput.BTN_A, false); break;
			case Keys.S: MyInput.setKey(MyInput.BTN_S, false); break;
			case Keys.D: MyInput.setKey(MyInput.BTN_D, false); break;*/
            case Keys.TAB: MyInput.setKey(MyInput.BTN_TAB, false); break;
            case Keys.SHIFT_LEFT: MyInput.setKey(MyInput.BTN_SHIFT_LEFT, false); break;
            case Keys.CONTROL_RIGHT: MyInput.setKey(MyInput.BTN_CTRL_RIGHT, false); break;
            case Keys.CONTROL_LEFT: MyInput.setKey(MyInput.BTN_CTRL_LEFT, false); break;
            case Keys.SHIFT_RIGHT: MyInput.setKey(MyInput.BTN_SHIFT_RIGHT, false); break;
        }
        return true;
    }

	/*public boolean scrolled(int amount) {
		return super.scrolled(amount);
	}*/

}