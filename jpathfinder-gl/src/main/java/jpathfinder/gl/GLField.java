package jpathfinder.gl;

import java.util.ArrayList;
import java.util.Collection;

import javax.media.opengl.GL;

import jpathfinder.Dimension;
import jpathfinder.PathField;
import jpathfinder.FieldShape;
import jpathfinder.PointFieldShape;
import jpathfinder.RectangleFieldShape;

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
//        for (FieldShape fieldShape : _pathField.getShapes()) {
//            if (fieldShape instanceof RectangleFieldShape) {
//                RectangleFieldShape rectangleFieldShape = (RectangleFieldShape) fieldShape;
//                new GLRectangle(rectangleFieldShape.getPoint(), rectangleFieldShape.getWidth(), rectangleFieldShape.getHeight()).render(gl);
//            } else if (fieldShape instanceof PointFieldShape) {
//                PointFieldShape pointFieldShape = (PointFieldShape) fieldShape;
//                new GLPoint(GLColor.BLUE, pointFieldShape.getX(), pointFieldShape.getY()).render(gl);
//            }
//        }
    }
    
    public Dimension getSize() {
        return _dimension;
    }
    
    public PathField getPathField() {
        return _pathField;
    }
    
}
