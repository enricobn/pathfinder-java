package jpathfinder;

public class Point implements Shape, Cloneable {
    private int _x;
    private int _y;
    private boolean _moving = false;
    
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

    @Override
    public boolean contains(Point point) {
        return equals(point);
    }
    
    public int distance(Point point) {
        return (int) Math.sqrt((_x - point._x) ^ 2 + (_y - point._y) ^ 2);  
    }
    
//    @Override
//    public Point getLocation() {
//        return this;
//    }
    
    @Override
    public boolean isMoving() {
        return _moving;
    }
    
    @Override
    public void move(int xDiff, int yDiff) {
        _x += xDiff;
        _y += yDiff;
        _moving = true;
    }
    
    @Override
    public void setLocation(Point location) {
        _x = location._x;
        _y = location._y;
        _moving = true;
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
