package jpathfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SimplePathFinder extends AbstractPathFinder {
    private final Map<Point, Integer> _queue = new HashMap<Point, Integer>();//Collections.synchronizedMap(new HashMap<Point, Integer>());

    private final Field field;
    private final Point from;
    private final Point to;
    
    public SimplePathFinder(Field field, Point from, Point to) {
        super();
        this.field = field;
        this.from = from;
        this.to = to;
    }

    @Override
    public List<Point> getPath() {
        _queue.clear();
        _queue.put(to, 0);
        while (!_queue.containsKey(from)) {
            Map<Point, Integer> next = new LinkedHashMap<Point, Integer>(_queue);
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//            }
//            synchronized (_queue) {
                for (Map.Entry<Point, Integer> entry : _queue.entrySet() ) {
                    Map<Point, Integer> adjacents = new LinkedHashMap<Point, Integer>();
                    for (Point point : getAdjacents(entry.getKey())) {
                        adjacents.put(point, 1 + entry.getValue());
                    }
                    for (Iterator<Map.Entry<Point, Integer>> i = adjacents.entrySet().iterator(); i.hasNext();  ) {
                        Map.Entry<Point, Integer> aentry = i.next();
                        // since from is in the field
                        if (aentry.getKey().equals(from)) {
                            continue;
                        }
                        if (!field.contains(aentry.getKey())) {
                            i.remove();
                        } else if (field.isOccupied(aentry.getKey(), from)) {
                            i.remove();
                        } else {
                            Integer value = _queue.get(aentry.getKey());
                            if (value != null && value <= aentry.getValue()) {
                                i.remove();
                            }
                        }
                    }
                    next.putAll(adjacents);
                }
                _queue.clear();
                _queue.putAll(next);
//            }
        }
        Point point = from;

        List<Point> result = new ArrayList<Point>();

        while (true) {
            Point minPoint = null;
            int min = Integer.MAX_VALUE;
            for (Point adjacent : getAdjacents(point)) {
                if (!adjacent.equals(from) && _queue.containsKey(adjacent)) {
                    if (minPoint == null || _queue.get(adjacent) < min) {
                        min = _queue.get(adjacent);
                        minPoint = adjacent;
                    }
                }
            }
            result.add(minPoint);
            if (minPoint.equals(to)) {
                break;
            }
            point = minPoint;
        }
        
        System.out.println(result);

        return result;
    }

}
