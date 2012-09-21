package jpathfinder.gl;

import javax.media.opengl.GL;

import jpathfinder.FieldShape;
import jpathfinder.Point;
import jpathfinder.PointFieldShape;

public class GLPoint implements GLShape {
    private final GLColor _color;
    private final PointFieldShape _pointFieldShape; 
    private Point _point;

    public GLPoint(GLColor color, int x, int y) {
        _color = color;
        _point = new Point(x, y);
        _pointFieldShape = new PointFieldShape(x, y);
    }

    public Point getPoint() {
        return _point;
    }
    
    public void move(int xDiff, int yDiff) {
        _point.move(xDiff, yDiff);
    }

    public void setLocation(Point location) {
        _point.setLocation(location);
        _pointFieldShape.setLocation(location);
    }
    
    @Override
    public FieldShape getFieldShape() {
        return _pointFieldShape;
    }

    public String toString() {
        return _point.toString();
    }
    
    @Override
    public void render(GL gl) {
        gl.glPushAttrib(GL.GL_ALL_ATTRIB_BITS);
        gl.glEnable(GL.GL_COLOR_MATERIAL);
        _color.render(gl);
        gl.glRectf(_point.getX(), _point.getY(), _point.getX() + 1, _point.getY() + 1);
        gl.glPopAttrib();
    }

}
