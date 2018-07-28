package jpathfinder.gl;

import javax.media.opengl.GL2;

import jpathfinder.AStarPathFinder;
import jpathfinder.Point;

public class GLAStarPathFinder extends AStarPathFinder implements GLRenderer {
    private final GLField _field;
    
    public GLAStarPathFinder(GLField field, Point from, Point to) {
        super(field.getPathField(), from, to);
        _field = field;
    }

    @Override
    public void render(GL2 gl) {
        GLPoint shape  = new GLPoint(_field, new GLColor(java.awt.Color.YELLOW), 0, 0);
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
