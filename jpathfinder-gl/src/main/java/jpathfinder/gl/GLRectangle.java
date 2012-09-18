package jpathfinder.gl;

import javax.media.opengl.GL;

import jpathfinder.Point;
import jpathfinder.Rectangle;

public class GLRectangle implements GLRenderer {
    private Rectangle _recRectangle;

    public GLRectangle(Point point, int width, int height) {
        _recRectangle = new Rectangle(point, width, height);
    }

    public void move(int xDiff, int yDiff) {
        _recRectangle.move(xDiff, yDiff);
    }

    public void setLocation(Point location) {
        _recRectangle.setLocation(location);
    }

    public Point getPoint() {
        return _recRectangle.getPoint();
    }

    public int getMaxX() {
        return _recRectangle.getMaxX();
    }

    public int getMaxY() {
        return _recRectangle.getMaxY();
    }

    public Rectangle getRectangle() {
        return _recRectangle;
    }
    
    public String toString() {
        return _recRectangle.toString();
    }

    public void render(GL gl) {
        gl.glRectf((float)getPoint().getX(), (float)getPoint().getY(), (float)getMaxX() + 1, 
                (float)getMaxY() + 1);
    }
}
