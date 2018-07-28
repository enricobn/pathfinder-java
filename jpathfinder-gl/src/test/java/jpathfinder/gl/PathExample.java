package jpathfinder.gl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import com.jogamp.opengl.util.Animator;
import jpathfinder.Dimension;
import jpathfinder.PathField;
import jpathfinder.Point;
import jpathfinder.PointFieldShape;
import jpathfinder.Rectangle;
import jpathfinder.RectangleFieldShape;

public class PathExample extends JFrame{
//    private static final int STENCIL_MASK = 0x1;
    private final PathField _field;
    private final GLPoint _me;
//    private int _xDir = 1;
//    private int _yDir = 1;
//    private final Point _startPoint;
//    private final Point _endPoint;
    
    private static final int SIZE_COEFF = 1;
    
    public static void main(String[] args) {
        Collection<GLRenderer> renderers = new ArrayList<GLRenderer>();
        PathField pathField = new PathField(new Dimension(100, 100));
        
        GLField glfield = new GLField(pathField, new Dimension(100 * SIZE_COEFF, 100 * SIZE_COEFF));
        
        Rectangle rect = rectangle(10, 10 , 10 , 10);
        GLRectangle glrect = new GLRectangle(glfield, GLColor.WHITE, rect); 
        renderers.add(glrect);
        pathField.add(new RectangleFieldShape(rect));
        
        rect = rectangle(40, 20, 20, 20);
        glrect = new GLRectangle(glfield, GLColor.WHITE, rect);
        renderers.add(glrect);
        pathField.add(new RectangleFieldShape(rect));
        
        rect = rectangle(40, 60, 20, 20);
        glrect = new GLRectangle(glfield, GLColor.WHITE, rect);
        renderers.add(glrect);
        pathField.add(new RectangleFieldShape(rect));
        
        rect = rectangle(80, 80, 10, 10);
        glrect = new GLRectangle(glfield, GLColor.WHITE, rect);
        renderers.add(glrect);
        pathField.add(new RectangleFieldShape(rect));

        Point startPoint = new Point(0, 0);
        
        Point endPoint = new Point(99 * SIZE_COEFF, 99 * SIZE_COEFF);

//        
//        GLField field = new GLField(new Dimension(8, 6));
//        field.add(new GLRectangleShape(new Point(4, 1), 1, 3));
//
//        Point startPoint = new Point(2, 2);
//        
//        Point endPoint = new Point(6, 2);

        
        PathExample frame = new PathExample(glfield, startPoint, endPoint, renderers);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static Rectangle rectangle(int x, int y, int width, int height) {
        return new Rectangle(new Point(x * SIZE_COEFF, y * SIZE_COEFF), width * SIZE_COEFF, height * SIZE_COEFF);
    }
    
    private final Collection<GLRenderer> _renderers = Collections.synchronizedList(new ArrayList<GLRenderer>());
    private final GLField _glField;
    
    public PathExample(final GLField glField, final Point start, final Point end, Collection<GLRenderer> renderers){
        _glField = glField;
        _field = glField.getPathField();
        _renderers.add(glField);
        _renderers.addAll(renderers);
        
        _me = new GLPoint(glField, new GLColor(Color.RED), start.getX(), start.getY());
        
        // TODO ugly
        _renderers.add(_me);

        _renderers.add(new GLPoint(glField, new GLColor(Color.GREEN), end.getX(), end.getY()));
        _renderers.add(new GLPoint(glField, new GLColor(Color.RED), start.getX(), start.getY()));

//        final SimplePathFinder finder = new SimplePathFinder(_field, start, end);
        final GLAStarPathFinder finder = new GLAStarPathFinder(glField, start, end);
        _renderers.add(finder);
                
        setSize(600,600);
        setTitle("Hello Universe");
        
        GraphicsListener listener=new GraphicsListener();
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities glCapabilities = new GLCapabilities(glp);
        /*
         * the default is 0 !!!!
         * I have lost a night ...   
         */
        glCapabilities.setStencilBits(1);
        
        GLCanvas canvas = new GLCanvas(glCapabilities);
        canvas.addGLEventListener(listener);
        getContentPane().add(canvas);
        
        //Create an Animator linked to the Canvas
        Animator animator = new Animator(canvas);//new FPSAnimator(canvas, 10);
        animator.start();
        
        final long startTime = System.currentTimeMillis();
        
        Thread th = new Thread() {
            public void run() {
                Collection<Point> path = finder.getPath();
                if (path != null) {
                    _renderers.add(new PathRenderer(path));
                }
                System.out.println(System.currentTimeMillis() - startTime);
            }
        };
        
        th.start();
        
        _field.add(new PointFieldShape(_me.getPoint().getX(), _me.getPoint().getY()));
    }
    
    public class GraphicsListener implements GLEventListener{
        

        public void display(GLAutoDrawable arg0) {
            
            GL2 gl=arg0.getGL().getGL2();

            // clear
            gl.glClearStencil(0x0);
            gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT | GL.GL_STENCIL_BUFFER_BIT);

            gl.glMatrixMode(GL2.GL_MODELVIEW);
            gl.glPushMatrix();
                gl.glLoadIdentity();
                synchronized (_renderers) {
                    for (GLRenderer renderer : _renderers) {
                        renderer.render(gl);
                    }
                }
            gl.glPopMatrix();
        }

        public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
        }

        /* init method
         * */
        public void init(GLAutoDrawable arg0) {
            // TODO
            //arg0.setGL(new DebugGL(arg0.getGL()));
            
            GL2 gl = arg0.getGL().getGL2();
            
            gl.glEnable(GL2.GL_LIGHT0);
            gl.glEnable(GL2.GL_LIGHTING);

            // I don't need it in this example
            gl.glDisable(GL.GL_DEPTH_TEST);
            
            gl.glStencilMask(~0);
            
            gl.glMatrixMode(GL2.GL_PROJECTION);
            gl.glLoadIdentity();
            
            gl.glOrtho (0, _field.getSize().width, _field.getSize().height, 0, 0, 1);
        }

        public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
        }

        @Override
        public void dispose(GLAutoDrawable arg0) {
        }
        
    }
    static final long serialVersionUID=100;
    
    private class PathRenderer implements GLRenderer {
        private final Collection<Point> _path;
        
        public PathRenderer(Collection<Point> path) {
            super();
            _path = path;
        }

        @Override
        public void render(GL2 gl) {
            GLPoint shape  = new GLPoint(_glField, new GLColor(Color.GREEN), 0, 0);
            for (Point point : _path) {
                shape.setLocation(point);
                shape.render(gl);
            }
        }
    }

}
