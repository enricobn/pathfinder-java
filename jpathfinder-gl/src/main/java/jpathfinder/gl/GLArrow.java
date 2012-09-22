package jpathfinder.gl;

import javax.media.opengl.GL;

import jpathfinder.FieldShape;
import jpathfinder.Point;
import jpathfinder.PointFieldShape;

public class GLArrow implements GLShape {
    private static final double BASE_ANGLE = Math.PI / 12;
    private final GLField _field;
    private final GLColor _color;
    private final PointFieldShape _pointFieldShape; 
    private final float _size;
    private Point _point;
    private double _angle;

    public GLArrow(GLField field, GLColor color, int x, int y, float size, double angle) {
        _field = field;
        _color = color;
        _pointFieldShape = new PointFieldShape(field.toPathFieldX(x), field.toPathFieldY(y));
        _size = size;
        _point = new Point(x, y);
        _angle = angle;
    }

    public Point getPoint() {
        return _point;
    }
    
    public void move(int xDiff, int yDiff) {
        _point.move(xDiff, yDiff);
        _pointFieldShape.move(_field.toPathFieldX(xDiff), _field.toPathFieldY(yDiff));
    }

    public void setLocation(Point location) {
        _angle = _point.angle(location);
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
    public void render(GL gl) {
        gl.glPushAttrib(GL.GL_ALL_ATTRIB_BITS);
        gl.glEnable(GL.GL_COLOR_MATERIAL);
        _color.render(gl);

//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//        }
//        System.out.println("Angle=" + _angle);
        
        gl.glBegin(GL.GL_TRIANGLES);
            gl.glVertex2f(_point.getX(), _point.getY());
            glVertex2(gl, _point, 1);
            glVertex2(gl, _point, -1);
        gl.glEnd();
        
        gl.glPopAttrib();
    }
    
    private void glVertex2(GL gl, Point point, int sign) {
        double distance = _size;
        gl.glVertex2d((float)point.getX() + distance * Math.cos(Math.PI - (_angle + sign * BASE_ANGLE)), 
                (float)point.getY() + distance * Math.sin(Math.PI - (_angle + sign * BASE_ANGLE)));
    }

}
