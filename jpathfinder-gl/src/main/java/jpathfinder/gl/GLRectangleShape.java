package jpathfinder.gl;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.media.opengl.GL;

import jpathfinder.RectangleShape;

public class GLRectangleShape extends RectangleShape implements GLRenderer {
    
    public GLRectangleShape(Rectangle rectangle) {
        super(rectangle);
    }

    public void render(GL gl) {
        gl.glRectf((float)getLocation().x, (float)getLocation().y, (float)getRectangle().getMaxX(), 
                (float)getRectangle().getMaxY());
    }
}
