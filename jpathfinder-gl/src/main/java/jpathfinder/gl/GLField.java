package jpathfinder.gl;

import javax.media.opengl.GL;

import jpathfinder.Dimension;
import jpathfinder.Field;
import jpathfinder.Shape;

public class GLField extends Field implements GLRenderer {

    public GLField(Dimension size) {
        super(size);
    }

    @Override
    public void render(GL gl) {
        for (Shape shape : getShapes()) {
            if (shape instanceof GLRenderer) {
                ((GLRenderer)shape).render(gl);
            }
        }
    }

}
