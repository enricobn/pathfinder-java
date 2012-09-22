package jpathfinder.gl;

import javax.media.opengl.GL;

import jpathfinder.FieldShape;
import jpathfinder.Point;
import jpathfinder.Rectangle;
import jpathfinder.RectangleFieldShape;

public class GLRectangle implements GLShape {
    private final GLField _field;
    private final Rectangle _rectangle;
    private final RectangleFieldShape _rectangleFieldShape;

    public GLRectangle(GLField field, Rectangle rectangle) {
        _field = field;
        _rectangle = rectangle;
        _rectangleFieldShape = new RectangleFieldShape(field.toPathField(rectangle.getPoint()), field.toPathFieldX(rectangle.getWidth()), 
                field.toPathFieldY(rectangle.getHeight()));
    }
    
    public GLRectangle(GLField field, Point point, int width, int height) {
        this(field, new Rectangle(point, width, height));
    }

    public void move(int xDiff, int yDiff) {
        _rectangle.move(xDiff, yDiff);
        _rectangleFieldShape.move(_field.toPathFieldX(xDiff), _field.toPathFieldY(yDiff));
    }

    public void setLocation(Point location) {
        _rectangle.setLocation(location);
        _rectangleFieldShape.setLocation(_field.toPathField(location));
    }

    public Point getPoint() {
        return _rectangle.getPoint();
    }

    public int getMaxX() {
        return _rectangle.getMaxX();
    }

    public int getMaxY() {
        return _rectangle.getMaxY();
    }

    public Rectangle getRectangle() {
        return _rectangle;
    }
    
    @Override
    public FieldShape getFieldShape() {
        return _rectangleFieldShape;
    }
    
    public String toString() {
        return _rectangle.toString();
    }

    public void render(GL gl) {
        gl.glRectf((float)getPoint().getX(), (float)getPoint().getY(), (float)getMaxX() + 1, 
                (float)getMaxY() + 1);
    }
}
