package jpathfinder;

import org.junit.Assert;
import org.junit.Test;

public class PointTest {

    @Test
    public void angle() {
        Assert.assertEquals(-Math.PI / 4, new Point(0,0).angle(new Point(10, 10)), 0.01);
        Assert.assertEquals(-Math.PI * 3 / 4, new Point(0,0).angle(new Point(-10, 10)), 0.01);
        Assert.assertEquals(Math.PI * 3 / 4, new Point(0,0).angle(new Point(-10, -10)), 0.01);
        Assert.assertEquals(Math.PI / 4, new Point(0,0).angle(new Point(10, -10)), 0.01);
    }
    
}
