package jpathfinder;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public interface Shape {

    Point getLocation();
    
    void setLocation(Point point);
    
    Dimension getSize();
    
    Rectangle getRectangle();
    
    boolean contains(Point point);
    
    void move(int xDiff, int yDiff);
        
}
