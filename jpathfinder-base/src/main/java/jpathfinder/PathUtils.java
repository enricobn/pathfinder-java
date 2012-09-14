package jpathfinder;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

public class PathUtils {

    private static Point[] adjacents = new Point[8];
    
    public static Point[] getAdjacents(Point point) {
        adjacents[0] = new Point(point.x +1, point.y);
        adjacents[1] = new Point(point.x +1, point.y + 1);
        adjacents[2] = new Point(point.x , point.y + 1);
        adjacents[3] = new Point(point.x -1, point.y +1);
        adjacents[4] = new Point(point.x -1, point.y);
        adjacents[5] = new Point(point.x -1, point.y -1);
        adjacents[6] = new Point(point.x , point.y -1);
        adjacents[7] = new Point(point.x +1, point.y -1);
        return adjacents;
    }

//    public static Collection<Point> getAdjacents(Point point) {
//        Collection<Point> adjacents = new ArrayList<Point>();
//        adjacents.add(new Point(point.x +1, point.y));
//        adjacents.add(new Point(point.x +1, point.y + 1));
//        adjacents.add(new Point(point.x , point.y + 1));
//        adjacents.add(new Point(point.x -1, point.y +1));
//        adjacents.add(new Point(point.x -1, point.y));
//        adjacents.add(new Point(point.x -1, point.y -1));
//        adjacents.add(new Point(point.x , point.y -1));
//        adjacents.add(new Point(point.x +1, point.y -1));
//        return adjacents;
//    }

}
