package jpathfinder.gl;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import javax.media.opengl.GL;

public class GLPointShape extends GLRectangleShape {
    private static final Dimension DIM1x1 = new Dimension(1,1);

    private final GLColor _color;
    
    public GLPointShape(GLColor color, Point point) {
        super(new Rectangle(point, DIM1x1));
        _color = color;
    }

    @Override
    public void render(GL gl) {
        gl.glPushAttrib(GL.GL_ALL_ATTRIB_BITS);
        gl.glEnable(GL.GL_COLOR_MATERIAL);
        _color.render(gl);
        super.render(gl);
        gl.glPopAttrib();
    }

}
