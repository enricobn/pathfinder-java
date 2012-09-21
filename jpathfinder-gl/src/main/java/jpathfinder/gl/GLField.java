package jpathfinder.gl;

import java.util.ArrayList;
import java.util.Collection;

import javax.media.opengl.GL;

import jpathfinder.Dimension;
import jpathfinder.PathField;
import jpathfinder.Point;

public class GLField implements GLRenderer {
    private final PathField _pathField;
    private final Dimension _dimension;
    private final Collection<GLShape> _shapes = new ArrayList<GLShape>();

    public GLField(PathField pathField, Dimension dimension) {
        super();
        _pathField = pathField;
        _dimension = dimension;
    }
    
    public void add(GLShape glShape) {
        _shapes.add(glShape);
        _pathField.add(glShape.getFieldShape());
    }

    @Override
    public void render(GL gl) {
        for (GLShape shape : _shapes) {
            shape.render(gl);
        }
    }
    
    public int toPathFieldX(int glX) {
        return (int) ((float)glX / _dimension.width * _pathField.getSize().width);
    }

    public int toPathFieldY(int glY) {
        return (int) ((float)glY / _dimension.height * _pathField.getSize().height);
    }
    
    public Point toPathField(Point glPoint) {
        return new Point(toPathFieldX(glPoint.getX()), toPathFieldY(glPoint.getY()));
    }
    
    public Dimension getSize() {
        return _dimension;
    }
    
    public PathField getPathField() {
        return _pathField;
    }
    
}
