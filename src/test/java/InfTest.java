import org.junit.Test;

import static org.junit.Assert.*;

public class InfTest {

    private Inf a = new Inf(1, 2, 3);
    private Inf b = new Inf(null, null, null);
    private Inf c = new Inf(0, 0, 0);
    private Inf d = new Inf(-9, -10, -11);

    @Test
    public void parent() {
        assertEquals((Integer) 1, a.parent());
        assertNull(b.parent());
        assertEquals((Integer) 0, c.parent());
        assertEquals((Integer) (-9), d.parent());
    }

    @Test
    public void left() {
        assertEquals((Integer) 2, a.left());
        assertNull(b.left());
        assertEquals((Integer) 0, c.left());
        assertEquals((Integer) (-10), d.left());
    }

    @Test
    public void right() {
        assertEquals((Integer) 3, a.right());
        assertNull(b.right());
        assertEquals((Integer) 0, c.right());
        assertEquals((Integer) (-11), d.right());
    }

    @Test
    public void equals() {
        assertEquals(new Inf(1, 2, 3), a);
        assertEquals(new Inf(null, null, null), b);
        assertEquals(new Inf(0, 0, 0), c);
        assertEquals(new Inf(-9, -10, -11), d);
    }

    @Test
    public void str() {
        assertEquals(a.str(), "(parent = " + 1 + "; left = " + 2 + "; right = " + 3 + ")");
        assertEquals(b.str(), "(parent = " + null + "; left = " + null + "; right = " + null + ")");
        assertEquals(c.str(), "(parent = " + 0 + "; left = " + 0 + "; right = " + 0 + ")");
        assertEquals(d.str(), "(parent = " + -9 + "; left = " + -10 + "; right = " + -11 + ")");
    }
}