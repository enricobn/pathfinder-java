package jpathfinder.gl;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import jpathfinder.FieldShape;
import jpathfinder.Point;
import jpathfinder.Rectangle;
import jpathfinder.RectangleFieldShape;

public class GLRectangle implements GLShape {
    private final GLField _field;
    private final GLColor _color;
    private final Rectangle _rectangle;
    private final RectangleFieldShape _rectangleFieldShape;

    public GLRectangle(GLField field, GLColor color, Rectangle rectangle) {
        _field = field;
        _color = color;
        _rectangle = rectangle;
        _rectangleFieldShape = new RectangleFieldShape(field.toPathField(rectangle.getPoint()), field.toPathFieldX(rectangle.getWidth()), 
                field.toPathFieldY(rectangle.getHeight()));
    }
    
    public GLRectangle(GLField field, GLColor color, Point point, int width, int height) {
        this(field, color, new Rectangle(point, width, height));
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

    public void render(GL2 gl) {
        gl.glPushAttrib(GL2.GL_ALL_ATTRIB_BITS);
            gl.glEnable(GL2.GL_COLOR_MATERIAL);
            _color.render(gl);
            gl.glRectf((float)getPoint().getX(), (float)getPoint().getY(), (float)getMaxX() + 1, 
                    (float)getMaxY() + 1);
        gl.glPopAttrib();
    }
}
