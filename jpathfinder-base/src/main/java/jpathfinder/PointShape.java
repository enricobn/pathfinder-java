package jpathfinder;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class PointShape extends RectangleShape {
    private static final Dimension DIM1x1 = new Dimension(1,1);
    
    public PointShape(Point point) {
        super(new Rectangle(point, DIM1x1));
    }
    
}
