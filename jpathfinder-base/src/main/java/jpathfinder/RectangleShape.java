package jpathfinder;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class RectangleShape implements Shape {
    private final Rectangle _rectangle;
    
    public RectangleShape(Rectangle rectangle) {
        super();
        _rectangle = rectangle;
    }

    @Override
    public Point getLocation() {
        return _rectangle.getLocation();
    }

    @Override
    public void setLocation(Point point) {
        _rectangle.setLocation(point);
    }
    
    @Override
    public Dimension getSize() {
        return _rectangle.getSize();
    }

    @Override
    public Rectangle getRectangle() {
        return _rectangle;
    }

    @Override
    public boolean contains(Point point) {
        return _rectangle.contains(point);
    }
    
    public void move(int xDiff, int yDiff) {
        _rectangle.setLocation(_rectangle.getLocation().x + xDiff, _rectangle.getLocation().y + yDiff);
    }

}
