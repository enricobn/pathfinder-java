package jpathfinder;

public class RectangleFieldShape implements FieldShape, Cloneable {
    private Point _point;
    private int _width;
    private int _height;
    private boolean _moving = false;

    public RectangleFieldShape(Rectangle rectangle) {
        this(rectangle.getPoint(), rectangle.getWidth(), rectangle.getHeight());
    }
    
    public RectangleFieldShape(Point point, int width, int height) {
        super();
        this._point = point;
        this._width = width;
        this._height = height;
    }

    @Override
    public boolean contains(Point point) {
        return point.getX() >= this._point.getX() && point.getX() <= getMaxX()
                && point.getY() >= this._point.getY() && point.getY() <= getMaxY();
    }
    
    @Override
    public boolean isMoving() {
        return _moving;
    }
    
    @Override
    public void move(int xDiff, int yDiff) {
        _point.move(xDiff, yDiff);
        _moving = true;
    }
    
    @Override
    public void setLocation(Point location) {
        _point = location;
        _moving = true;
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
    public Point getLocation() {
        return _point;
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
        RectangleFieldShape other = (RectangleFieldShape) obj;
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
    public RectangleFieldShape clone() throws CloneNotSupportedException {
        RectangleFieldShape cloned = (RectangleFieldShape) super.clone();
        cloned._point = _point.clone();
        return cloned;
    }


}
