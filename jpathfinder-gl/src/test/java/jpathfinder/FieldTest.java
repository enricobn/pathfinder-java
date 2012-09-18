package jpathfinder;

import static org.junit.Assert.*;

import org.junit.Test;

public class FieldTest {

    @Test
    public void test() {
        Dimension dimension = new Dimension(100,  100);
        Field field = new Field(dimension);

        assertTrue(field.contains(new Point(50, 50))); 
        assertTrue(field.contains(new Point(99, 99)));
        assertFalse(field.contains(new Point(150, 150))); 
        assertFalse(field.contains(new Point(-1, -1)));
        assertFalse(field.contains(new Point(100, 100)));
        
        field.add(new Rectangle(new Point(0, 0), 10, 10));
        field.add(new Rectangle(new Point(20, 20), 10, 10));
        
        assertTrue(field.isOccupied(new Point(5, 5), null));
        assertFalse(field.isOccupied(new Point(15, 15), null));
     
    }

}
