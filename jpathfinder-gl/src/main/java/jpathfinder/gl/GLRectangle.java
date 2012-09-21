package jpathfinder.gl;

import javax.media.opengl.GL;

import jpathfinder.FieldShape;
import jpathfinder.Point;
import jpathfinder.Rectangle;
import jpathfinder.RectangleFieldShape;

public class GLRectangle implements GLShape {
    private final GLField _field;
    private final Rectangle _recRectangle;
    private final RectangleFieldShape _recRectangleFieldShape;

    public GLRectangle(GLField field, Rectangle rectangle) {
        _field = field;
        _recRectangle = rectangle;
        _recRectangleFieldShape = new RectangleFieldShape(field.toPathField(rectangle.getPoint()), field.toPathFieldX(rectangle.getWidth()), 
                field.toPathFieldY(rectangle.getHeight()));
    }
    
    public GLRectangle(GLField field, Point point, int width, int height) {
        this(field, new Rectangle(point, width, height));
    }

    public void move(int xDiff, int yDiff) {
        _recRectangle.move(xDiff, yDiff);
    }

    public void setLocation(Point location) {
        _recRectangle.setLocation(location);
    }

    public Point getPoint() {
        return _recRectangle.getPoint();
    }

    public int getMaxX() {
        return _recRectangle.getMaxX();
    }

    public int getMaxY() {
        return _recRectangle.getMaxY();
    }

    public Rectangle getRectangle() {
        return _recRectangle;
    }
    
    @Override
    public FieldShape getFieldShape() {
        return _recRectangleFieldShape;
    }
    
    public String toString() {
        return _recRectangle.toString();
    }

    public void render(GL gl) {
        gl.glRectf((float)getPoint().getX(), (float)getPoint().getY(), (float)getMaxX() + 1, 
                (float)getMaxY() + 1);
    }
}
