package jpathfinder;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractPathFinder implements PathFinder {
    
    protected static Collection<Point> getAdjacents(Point point) {
        Collection<Point> adjacents = new ArrayList<Point>();
        adjacents.add(new Point(point.x +1, point.y));
        adjacents.add(new Point(point.x +1, point.y + 1));
        adjacents.add(new Point(point.x , point.y + 1));
        adjacents.add(new Point(point.x -1, point.y +1));
        adjacents.add(new Point(point.x -1, point.y));
        adjacents.add(new Point(point.x -1, point.y -1));
        adjacents.add(new Point(point.x , point.y -1));
        adjacents.add(new Point(point.x +1, point.y -1));
        return adjacents;
    }

}
