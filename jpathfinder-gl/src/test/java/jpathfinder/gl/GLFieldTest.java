package jpathfinder.gl;

import static org.junit.Assert.assertEquals;
import jpathfinder.Dimension;
import jpathfinder.PathField;
import jpathfinder.Point;

import org.junit.Test;

public class GLFieldTest {

    @Test
    public void conversions() {
        PathField pathField = new PathField(new Dimension(10, 10));
        
        GLField glField = new GLField(pathField, new Dimension(20, 20));
        
        assertEquals(new Point(5, 5), glField.toPathField(new Point(10, 10)));
        
        assertEquals(new Point(10, 10), glField.toPathField(new Point(20, 20)));
        
        assertEquals(new Point(10, 10), glField.fromPathField(new Point(5, 5)));
        
        assertEquals(new Point(20, 20), glField.fromPathField(new Point(10, 10)));
    }
    
}

