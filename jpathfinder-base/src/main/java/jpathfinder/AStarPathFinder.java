package jpathfinder;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * thanks to http://www.policyalmanac.org/games/aStarTutorial.htm
 * @author Enrico Benedetti
 *
 */
public class AStarPathFinder implements PathFinder {
    private final Map<Point, Node> _open = java.util.Collections.synchronizedMap(new HashMap<>());

    private final Map<Point, Node> _closed = new HashMap<>();

    private final PathField _field;
    private final Point _from;
    private final Point _to;
    
    public AStarPathFinder(PathField pathField, Point from, Point to) {
        super();
        this._field = pathField;
        this._from = from;
        this._to = to;
    }

    public PathField getPathField() {
        return _field;
    }
    
    private static void print(PrintStream out, Point point) {
        out.println(point);
    }
    
    @Override
    public List<Point> getPath() {
        _open.clear();
        _closed.clear();
        _open.put(_from, new Node(null, _from));
        Node targetNode;
        while (true) {

            if (_open.isEmpty()) {
                return null;
            }

            int min = Integer.MAX_VALUE;
            Node minNode = null;

            synchronized (_open) {
                for (Node node : _open.values()) {
                    int f = node.F();
                    if (minNode == null || f < min) {
                        min = f;
                        minNode = node;
                    }
                }
            }

            if (minNode.point.equals(_to)) {
                targetNode = minNode;
                break;
            }

            for (Point point : PathUtils.getAdjacents(minNode.point)) {
                // I do not consider the end point to be occupied, so I can move towards it
                if (_field.contains(point) && (point.equals(_to) || !_field.isOccupied(point))) {
                    if (!_closed.containsKey(point)) {
                        Node node = new Node(minNode, point);
                        Node got = _open.get(point);
                        if (got == null) {
                            _open.put(point, node);
                        } else {
                            int gToMin = minNode.G(got);
                            if (gToMin < node.G()) {
                                got.setParent(minNode);
                            }
                        }
                    }
                }
            }

            _closed.put(minNode.point, minNode);

            _open.remove(minNode.point);
        }

        // the path can contains occupied points. Normally it can be only the end point
        return targetNode.getPath().stream().filter(it -> !_field.isOccupied(it)).collect(Collectors.toList());
    }

    public Map<Point, Node> getOpen() {
        return _open;
    }
    
    public class Node {
        public final Point point;
        private Node _parent;
        private Integer _g;
        private Integer _h;
        private Integer _f;
        
        public Node(Node parent, Point point) {
            super();
            _parent = parent;
            this.point = point;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((point == null) ? 0 : point.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Node other = (Node) obj;
            if (point == null) {
                return other.point == null;
            } else return point.equals(other.point);
        }
        
        int F() {
            if (_f == null) { 
                _f = G() + H();
            }
            return _f;
        }
        
        int G() {
            if (_g == null) {
                _g = G(_parent);
            }
            return _g;
        }

        int H() {
            if (_h == null) {
                _h = (Math.abs(_to.getX() - point.getX()) + Math.abs(_to.getY() - point.getY())) * 10;
            }
            return _h;
        }

        int G(Node node) {
            if (node == null) {
                return 0;
            }
            int g = node.G();
            if (point.getX() == node.point.getX() || point.getY() == node.point.getY()) {
                g += 10;
            } else {
                g += 14;
            }
            return g;
        }

        void setParent(Node parent) {
            _f = null;
            _g = null;
            _parent = parent;
        }

        public List<Point> getPath() {
            List<Point> result = new ArrayList<>();

            Node targetNode = this;

            while (targetNode._parent != null) {
                result.add(targetNode.point);
                targetNode = targetNode._parent;
            }

            return result;
        }
        
        @Override
        public String toString() {
            return "(" + point.getX() + "," + point.getY() + ")=" + F();
        }
        
    }
    
    private static class NodeComparator implements Comparator<Node> {
          @Override
          public int compare(Node o1, Node o2) {
              return o1.F() - o2.F();
          }
    }
    
}
