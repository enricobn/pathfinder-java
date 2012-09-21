package jpathfinder.gl;

import javax.media.opengl.GL;

import jpathfinder.Dimension;
import jpathfinder.PathField;
import jpathfinder.FieldShape;

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
            if (fieldShape instanceof GLRenderer) {
                ((GLRenderer)fieldShape).render(gl);
            }
        }
    }
    
    public Dimension getSize() {
        return _dimension;
    }
    

}
