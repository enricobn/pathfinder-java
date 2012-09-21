package jpathfinder;

import static org.junit.Assert.*;

import org.junit.Test;

public class PathFieldTest {

    @Test
    public void test() {
        Dimension dimension = new Dimension(100,  100);
        PathField pathField = new PathField(dimension);

        assertTrue(pathField.contains(new Point(50, 50))); 
        assertTrue(pathField.contains(new Point(99, 99)));
        assertFalse(pathField.contains(new Point(150, 150))); 
        assertFalse(pathField.contains(new Point(-1, -1)));
        assertFalse(pathField.contains(new Point(100, 100)));
        
        pathField.add(new RectangleFieldShape(new Point(0, 0), 10, 10));
        pathField.add(new RectangleFieldShape(new Point(20, 20), 10, 10));
        
        assertTrue(pathField.isOccupied(new Point(5, 5), null));
        assertFalse(pathField.isOccupied(new Point(15, 15), null));
     
    }

}
