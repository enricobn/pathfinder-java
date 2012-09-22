package jpathfinder;

public class Point implements Cloneable {
    private int _x;
    private int _y;
    
    public Point(int x, int y) {
        super();
        this._x = x;
        this._y = y;
    }
    
    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    public int distance(Point point) {
        return (int) Math.sqrt((_x - point._x) ^ 2 + (_y - point._y) ^ 2);  
    }

    public double angle(Point point) {
        int dx = point._x - _x;
        int dy = point._y - _y;
        // - since 0,0 is up left
        return -Math.atan2(dy, dx);
    }

    
    public void move(int xDiff, int yDiff) {
        _x += xDiff;
        _y += yDiff;
    }
    
    public void setLocation(Point location) {
        _x = location._x;
        _y = location._y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + _x;
        result = prime * result + _y;
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
        Point other = (Point) obj;
        if (_x != other._x)
            return false;
        if (_y != other._y)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "(" + _x + "," + _y + ")";
    }
    
    @Override
    public Point clone() throws CloneNotSupportedException {
        return (Point) super.clone();
    }
    
}
