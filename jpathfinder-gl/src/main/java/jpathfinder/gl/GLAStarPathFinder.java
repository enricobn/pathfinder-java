package jpathfinder.gl;

import javax.media.opengl.GL;

import jpathfinder.AStarPathFinder;
import jpathfinder.PathField;
import jpathfinder.Point;

public class GLAStarPathFinder extends AStarPathFinder implements GLRenderer {

    public GLAStarPathFinder(PathField pathField, Point from, Point to) {
        super(pathField, from, to);
    }

    @Override
    public void render(GL gl) {
        GLPoint shape  = new GLPoint(new GLColor(java.awt.Color.YELLOW), 0, 0);
        synchronized (getOpen()) {
            for (Object node : getOpen().values()) {
                shape.setLocation(((Node)node).point);
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
