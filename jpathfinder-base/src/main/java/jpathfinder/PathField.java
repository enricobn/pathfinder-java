package jpathfinder;

import java.util.ArrayList;
import java.util.Collection;

public class PathField {
    private final Collection<FieldShape> _fieldShapes = new ArrayList<FieldShape>();
    private final Dimension _size;
    private final Rectangle _rectangle;
    
    public PathField(Dimension size) {
        super();
        _size = size;
        _rectangle = new Rectangle(new Point(0,0), size.width, size.height);
    }

    public void add(FieldShape fieldShape) {
        _fieldShapes.add(fieldShape);
    }
    
    // TODO
    public void remove(FieldShape fieldShape) {
        throw new UnsupportedOperationException();
    }
    
    public boolean isOccupied(Point point, Point from) {
        boolean near = from == null || point.distance(from) < 3;
        for (FieldShape fieldShape : _fieldShapes) {
            if (fieldShape.contains(point)) {
                if (near) {
                    return true;
                }
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
    
    public Collection<FieldShape> getShapes() {
        return _fieldShapes;
    }

    
}
