package jpathfinder.gl;

import javax.media.opengl.GL;

import jpathfinder.Dimension;
import jpathfinder.PathField;
import jpathfinder.FieldShape;
import jpathfinder.PointFieldShape;
import jpathfinder.RectangleFieldShape;

public class GLField implements GLRenderer {
    private final PathField _pathField;
    private final Dimension _dimension;

    public GLField(PathField pathField, Dimension dimension) {
        super();
        _pathField = pathField;
        _dimension = dimension;
    }

    @Override
    public void render(GL gl) {
        for (FieldShape fieldShape : _pathField.getShapes()) {
            if (fieldShape instanceof RectangleFieldShape) {
                RectangleFieldShape rectangleFieldShape = (RectangleFieldShape) fieldShape;
                new GLRectangle(rectangleFieldShape.getPoint(), rectangleFieldShape.getWidth(), rectangleFieldShape.getHeight()).render(gl);
            } else if (fieldShape instanceof PointFieldShape) {
                PointFieldShape pointFieldShape = (PointFieldShape) fieldShape;
                new GLPoint(GLColor.BLUE, pointFieldShape.getX(), pointFieldShape.getY()).render(gl);
            }
        }
    }
    
    public Dimension getSize() {
        return _dimension;
    }
    

}
