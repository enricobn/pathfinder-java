package jpathfinder;

public class Rectangle implements Cloneable {
    private Point _point;
    private int _width;
    private int _height;

    public Rectangle(Point point, int width, int height) {
        super();
        this._point = point;
        this._width = width;
        this._height = height;
    }

    public void move(int xDiff, int yDiff) {
        _point.move(xDiff, yDiff);
    }
    
    public boolean contains(Point point) {
        return point.getX() >= this._point.getX() && point.getX() <= getMaxX()
            && point.getY() >= this._point.getY() && point.getY() <= getMaxY();
    }
    
    public void setLocation(Point location) {
        _point = location;
    }
    
    public Point getPoint() {
        return _point;
    }

    public int getHeight() {
        return _height;
    }
    
    public int getWidth() {
        return _width;
    }
    
    public int getMaxX() {
        return _point.getX() + _width -1;
    }

    public int getMaxY() {
        return _point.getY() + _height -1;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + _height;
        result = prime * result + ((_point == null) ? 0 : _point.hashCode());
        result = prime * result + _width;
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
        Rectangle other = (Rectangle) obj;
        if (_height != other._height)
            return false;
        if (_point == null) {
            if (other._point != null)
                return false;
        } else if (!_point.equals(other._point))
            return false;
        if (_width != other._width)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "(" + _point + ", " + _width + ", " + _height + ")";
    }
    
    @Override
    public Rectangle clone() throws CloneNotSupportedException {
        Rectangle cloned = (Rectangle) super.clone();
        cloned._point = _point.clone();
        return cloned;
    }


}
