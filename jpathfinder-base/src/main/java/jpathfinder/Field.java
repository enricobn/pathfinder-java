package jpathfinder;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;


public class Field {
    private final Collection<Shape> _shapes = new ArrayList<Shape>();
    private final Dimension _size;
    private final Rectangle _rectangle;
    
    public Field(Dimension size) {
        super();
        _size = size;
        _rectangle = new Rectangle(new Point(0,0), size);
    }

    public void add(Shape shape) {
        _shapes.add(shape);
    }
    
    public void remove(Shape shape) {
        throw new UnsupportedOperationException();
    }
    
    public boolean isOccupied(Point point) {
        for (Shape shape : _shapes) {
            if (shape.contains(point)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean contains(Point point) {
        return _rectangle.contains(point);
    }
    
    public Dimension getSize() {
        return _size;
    }
    
    public Collection<Shape> getShapes() {
        return _shapes;
    }

    
}
