package jpathfinder.gl;

import jpathfinder.FieldShape;
import jpathfinder.Point;
import jpathfinder.PointFieldShape;

import javax.media.opengl.GL2;

public class GLPoint implements GLShape {
    private final GLField _field;
    private final GLColor _color;
    private final PointFieldShape _pointFieldShape; 
    private Point _point;

    public GLPoint(GLField field, GLColor color, int x, int y) {
        _field = field;
        _color = color;
        _point = new Point(x, y);
        _pointFieldShape = new PointFieldShape(field.toPathFieldX(x), field.toPathFieldY(y));
    }

    public Point getPoint() {
        return _point;
    }
    
    public void move(int xDiff, int yDiff) {
        _point.move(xDiff, yDiff);
        _pointFieldShape.move(_field.toPathFieldX(xDiff), _field.toPathFieldY(yDiff));
    }

    public void setLocation(Point location) {
        _point.setLocation(location);
        _pointFieldShape.setLocation(_field.toPathField(location));
    }
    
    @Override
    public FieldShape getFieldShape() {
        return _pointFieldShape;
    }

    public String toString() {
        return _point.toString();
    }
    
    @Override
    public void render(GL2 gl) {
        gl.glPushAttrib(GL2.GL_ALL_ATTRIB_BITS);
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        _color.render(gl);
        gl.glRectf(_point.getX(), _point.getY(), _point.getX() + 1, _point.getY() + 1);
        gl.glPopAttrib();
    }

}
