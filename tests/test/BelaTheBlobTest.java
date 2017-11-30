import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntArray;
import hu.marazmarci.belatheblob.desktop.GdxTestRunner;
import hu.marazmarci.belatheblob.gui.BoundingBox;
import hu.marazmarci.belatheblob.handlers.input.GameInputAdapter;
import hu.marazmarci.belatheblob.handlers.input.GameInputHandler;
import hu.marazmarci.belatheblob.utils.DoublePeekableStack;
import org.junit.*;
import org.junit.function.ThrowingRunnable;
import org.junit.runner.RunWith;

import java.util.EmptyStackException;

@RunWith(GdxTestRunner.class)
public class BelaTheBlobTest {

    //private GameMain gameMain;
    //private GameStateManager gsm;

    @BeforeClass
    public static void beforeClass() {

    }

    @Before
    public void before() {
        System.out.println("== before ==");
        System.out.println();
        //this.gameMain = new GameMain(false);
        //this.gsm = new GameStateManager(gameMain);
    }

    @After
    public void after() {
        System.out.println();
        System.out.println("== after ===");
    }

    @Test
    public void testTest() {
        System.out.println("test1");
    }


    /**
     * 1 osztály 1 metódusa
     */
    @Test
    public void stackTest() {

        System.out.println("stackTest");

        Integer i1 = 1;
        Integer i2 = 2;
        Integer i3 = 3;

        final DoublePeekableStack<Integer> stack = new DoublePeekableStack<Integer>();

        stack.push(i1);
        stack.push(i2);
        stack.push(i3);

        Assert.assertEquals(stack.peek(), i3);
        Assert.assertEquals(stack.peekSecond(), i2);
        stack.pop();
        Assert.assertEquals(stack.peek(), i2);
        Assert.assertEquals(stack.peekSecond(), i1);
        stack.pop();
        Assert.assertEquals(stack.peek(), i1);
        Assert.expectThrows(EmptyStackException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                stack.peekSecond();
            }
        });

    }

    /**
     * 1 osztály 3 metódusa
     */
    @Test
    public void boundingBoxTest() {

        System.out.println("boundingBoxTest");

        BoundingBox b10 = new BoundingBox(10,10,10,10);
        BoundingBox b10_expanded_1 = new BoundingBox(9,9,12,12);
        BoundingBox b10_repeat = new BoundingBox(10,10,10,10);
        BoundingBox b200 = new BoundingBox(200,200,200,200);

        Assert.assertNotEquals(b10, b200);
        Assert.assertEquals(b10, b10);
        Assert.assertEquals(b10, b10_repeat);

        b10.expand(1);

        Assert.assertNotEquals(b10, b10_repeat);
        Assert.assertEquals(b10, b10_expanded_1);

        Vector3 touchPoint1 = new Vector3(1,1,1);
        Vector3 touchPoint15 = new Vector3(15,15,15);

        Assert.assertFalse(b10.isPointInside(touchPoint1));
        Assert.assertFalse(b200.isPointInside(touchPoint1));
        Assert.assertTrue(b10.isPointInside(touchPoint15));
        Assert.assertTrue(b10_expanded_1.isPointInside(touchPoint15));
        Assert.assertTrue(b10_repeat.isPointInside(touchPoint15));

        BoundingBox b10_clone = b10.clone();

        Assert.assertFalse(b10 == b10_clone);
        Assert.assertEquals(b10, b10_clone);

    }





    private boolean handleKeyDown;
    private boolean handleKeyUp;
    private boolean handleKeysHeld;
    //private int heldKeysCount;
    private IntArray heldKeys;
    private boolean handleTouchDown;
    private Vector3 touchPoint;


    /**
     * Tesztelt metódusok:
     *
     * GameInputHandler
     *      .handleInput() ✓
     *      .keyDown() ✓
     *      .handleKeyDown() ✓
     *      .handleKeyUp() ✓
     *      .keyUp() ✓
     *      .handleKeysHeld() ✓
     *      .touchDown() ✓
     *      .handleTouchDown() ✓
     */
    @Test
    public void inputHandlerTest() {
        System.out.println("inputHandlerTest");
        GameInputHandler input = new GameInputAdapter() {
            @Override
            protected boolean handleKeyDown(int keyCode) {
                handleKeyDown = true;
                return false;
            }
            @Override
            protected void handleKeyUp(int keyCode) {
                handleKeyUp = true;
            }

            @Override
            protected void handleKeysHeld(IntArray heldKeys) {
                BelaTheBlobTest.this.heldKeys = heldKeys;
                handleKeysHeld = true;
            }

            @Override
            protected void handleTouchDown(Vector3 touchPoint) {
                handleTouchDown = true;
                BelaTheBlobTest.this.touchPoint = touchPoint;
            }


        };

        Assert.assertFalse(handleKeysHeld);
        input.keyDown(42);
        Assert.assertFalse(handleKeysHeld);
        Assert.assertTrue(handleKeyDown);
        Assert.assertNull(heldKeys);
        input.handleInput();
        Assert.assertTrue(handleKeysHeld);
        Assert.assertNotNull(heldKeys);
        Assert.assertEquals(heldKeys.size, 1);
        input.keyDown(55);
        input.handleInput();
        Assert.assertEquals(heldKeys.size, 2);
        input.keyDown(99);
        input.handleInput();
        Assert.assertEquals(heldKeys.size, 3);

        input.keyUp(42);
        Assert.assertTrue(handleKeyUp);
        input.handleInput();
        Assert.assertEquals(heldKeys.size, 2);
        input.keyUp(55);
        input.keyUp(99);
        Assert.assertEquals(heldKeys.size, 0);


        //Vector3 touchPoint1 = new Vector3(1,1,1);
        //Vector3 touchPoint2 = new Vector3(2,2,2);

        Assert.assertFalse(handleTouchDown);
        input.touchDown(4242,4242,0,0);
        Assert.assertTrue(handleTouchDown);

        Assert.assertEquals(touchPoint.x, 4242, 0);
        Assert.assertEquals(touchPoint.y, 4242, 0);

    }


}
