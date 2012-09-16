package jpathfinder.gl;

import java.awt.Point;

import javax.media.opengl.GL;

import jpathfinder.AStarPathFinder;
import jpathfinder.Field;

public class GLAStarPathFinder extends AStarPathFinder implements GLRenderer {

    public GLAStarPathFinder(Field field, Point from, Point to) {
        super(field, from, to);
    }

    @Override
    public void render(GL gl) {
        GLPointShape shape  = new GLPointShape(new GLColor(java.awt.Color.YELLOW), new Point());
        synchronized (getOpen()) {
            for (Object node : getOpen().values()) {
                shape.setLocation(((Node)node)._point);
                shape.render(gl);
            }
        }
//        shape  = new PointShape(new GLColor(Color.GREEN.darker()), new Point());
//        synchronized (_closed) {
//            for (Node node : _closed) {
//                shape.setLocation(node._point);
//                shape.render(gl);
//            }
//        }
    }

}
