package jpathfinder;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.buffer.PriorityBuffer;

/**
 * thanks to http://www.policyalmanac.org/games/aStarTutorial.htm
 * @author Enrico Benedetti
 *
 */
public class AStarPathFinder implements PathFinder {
//    private final Collection<Node> _open = java.util.Collections.synchronizedList(new ArrayList<Node>());

    private final PriorityBuffer _open = new PriorityBuffer(new NodeComparator());
    
    private final Collection<Node> _closed = new ArrayList<Node>();

    private final Field _field;
    private final Point _from;
    private final Point _to;
    
    public AStarPathFinder(Field field, Point from, Point to) {
        super();
        this._field = field;
        this._from = from;
        this._to = to;
    }

    @Override
    public List<Point> getPath() {
        _open.clear();
        _closed.clear();
        _open.add(new Node(null, _from));
        Node targetNode = null;
        while (true) {
//            System.out.println(_open.size());
            
            if (_open.isEmpty()) {
                return null;
            }
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//                int min = Integer.MAX_VALUE;
//                Node minNode = null;
//            
//                synchronized (_open) {
//                    Node orderderMinNode = _open.iterator().next();
//                    for (Node node : _open) {
//                        int f = node.F();
//                        if (minNode == null || f < min) {
//                            min = f;
//                            minNode = node;
//                        }
//                    }
//                }

//                  synchronized (_open) {
//                      for (Object o : _open) {
//                          System.out.println("point=" + o);
//                      }
//                  }
            
                Node minNode = (Node) _open.remove();

                if (minNode._point.equals(_to)) {
                    targetNode = minNode;
                    break;
                }

                for (Point point : PathUtils.getAdjacents(minNode._point)) {
                    // I do not consider the end point to be occupied, so I can move towards it
                    if (_field.contains(point) && (point.equals(_to) || !_field.isOccupied(point))) {
                        Node node = new Node(minNode, point);
                        if (!_closed.contains(node)) {
                            if (!_open.contains(node)) {
                                _open.add(node);
                            } else {
                                int gToMin = minNode.G(node);
                                if (gToMin < node.G()) {
                                    // to update ordered set
//                                    _open.remove(node);
                                    node.setParent(minNode);
                                   
                                 // to update ordered set
//                                    _open.add(node);
                                }
                            }
                        }
                    }
                }
                
                _closed.add(minNode);
                
//                _open.remove(minNode);
                
//                System.out.println(_open.size());
        }

        List<Point> result = new ArrayList<Point>();

        while (targetNode._parent != null) {
            // the path can contains occupied points. Normally it can be only the end point 
            if (!_field.isOccupied(targetNode._point)) {
                result.add(targetNode._point);
            }
            targetNode = targetNode._parent;
        }
        
        return result;
    }
    
    protected PriorityBuffer getOpen() {
        return _open;
    }
    
    public class Node {
        public final Point _point;
        private Node _parent;
        private Integer _g;
        private Integer _h;
        private Integer _f;
        
        public Node(Node parent, Point point) {
            super();
            _parent = parent;
            _point = point;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((_point == null) ? 0 : _point.hashCode());
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
            if (_point == null) {
                if (other._point != null)
                    return false;
            } else if (!_point.equals(other._point))
                return false;
            return true;
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
                _h = (Math.abs(_to.x - _point.x) + Math.abs(_to.y - _point.y)) * 10;
            }
            return _h;
        }

        int G(Node node) {
            if (node == null) {
                return 0;
            }
            int g = node.G();
            if (_point.x == node._point.x || _point.y == node._point.y) {
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
        
        @Override
        public String toString() {
            return "(" + _point.x + "," + _point.y + ")=" + F();
        }
        
    }
    
    private static class NodeComparator implements Comparator<Node> {
          @Override
          public int compare(Node o1, Node o2) {
              return o1.F() - o2.F();
          }
    }
    
}
