package jpathfinder;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractPathFinder implements PathFinder {
    
    protected static Collection<Point> getAdjacents(Point point) {
        Collection<Point> adjacents = new ArrayList<Point>();
        adjacents.add(new Point(point.getX() +1, point.getY()));
        adjacents.add(new Point(point.getX() +1, point.getY() + 1));
        adjacents.add(new Point(point.getX() , point.getY() + 1));
        adjacents.add(new Point(point.getX() -1, point.getY() +1));
        adjacents.add(new Point(point.getX() -1, point.getY()));
        adjacents.add(new Point(point.getX() -1, point.getY() -1));
        adjacents.add(new Point(point.getX() , point.getY() -1));
        adjacents.add(new Point(point.getX() +1, point.getY() -1));
        return adjacents;
    }

}
