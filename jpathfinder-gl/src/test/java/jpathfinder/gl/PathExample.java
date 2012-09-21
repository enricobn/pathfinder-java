package jpathfinder.gl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.swing.JFrame;

import jpathfinder.Dimension;
import jpathfinder.PathField;
import jpathfinder.Point;
import jpathfinder.PointFieldShape;
import jpathfinder.Rectangle;
import jpathfinder.RectangleFieldShape;

import com.sun.opengl.util.Animator;

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
        
        Rectangle rect = new Rectangle(new Point(10 * SIZE_COEFF, 10 * SIZE_COEFF), 10 * SIZE_COEFF, 10 * SIZE_COEFF);
        
        GLRectangle glrect = new GLRectangle(rect); 
        renderers.add(glrect);
        pathField.add(new RectangleFieldShape(rect));
        
        rect = new Rectangle(new Point(40 * SIZE_COEFF, 20 * SIZE_COEFF), 20 * SIZE_COEFF, 20 * SIZE_COEFF);
        glrect = new GLRectangle(rect);
        renderers.add(glrect);
        pathField.add(new RectangleFieldShape(rect));
        
        
        rect = new Rectangle(new Point(40 * SIZE_COEFF, 60 * SIZE_COEFF), 20 * SIZE_COEFF, 20 * SIZE_COEFF);
        glrect = new GLRectangle(rect);
        renderers.add(glrect);
        pathField.add(new RectangleFieldShape(rect));
        
        rect = new Rectangle(new Point(80 * SIZE_COEFF, 80 * SIZE_COEFF), 10 * SIZE_COEFF, 10 * SIZE_COEFF);
        glrect = new GLRectangle(rect);
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

        
        PathExample frame = new PathExample(pathField, startPoint, endPoint, renderers);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    private final Collection<GLRenderer> _renderers = Collections.synchronizedList(new ArrayList<GLRenderer>());
    
    public PathExample(final PathField field, final Point start, final Point end, Collection<GLRenderer> renderers){
         GLField glfield = new GLField(field, new Dimension(100 * SIZE_COEFF, 100 * SIZE_COEFF));
        

        _field = field;
        _renderers.add(glfield);
        _renderers.addAll(renderers);
        
        _me = new GLPoint(new GLColor(Color.RED), start.getX(), start.getY());
        
        // TODO ugly
        _renderers.add((GLRenderer)_me);

        _renderers.add(new GLPoint(new GLColor(Color.GREEN), end.getX(), end.getY()));
        _renderers.add(new GLPoint(new GLColor(Color.RED), start.getX(), start.getY()));

//        final SimplePathFinder finder = new SimplePathFinder(_field, start, end);
        final GLAStarPathFinder finder = new GLAStarPathFinder(_field, start, end);
        _renderers.add(finder);
                
        setSize(600,600);
        setTitle("Hello Universe");
        
        GraphicsListener listener=new GraphicsListener();
        GLCapabilities glCapabilities = new GLCapabilities();
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
                    _renderers.add(new PathRenderer(finder.getPath()));
                }
                System.out.println(System.currentTimeMillis() - startTime);
            }
        };
        
        th.start();
        
        field.add(new PointFieldShape(_me.getPoint().getX(), _me.getPoint().getY()));
    }
    
    public class GraphicsListener implements GLEventListener{
        

        public void display(GLAutoDrawable arg0) {
            
            GL gl=arg0.getGL();

            // clear
            gl.glClearStencil(0x0);
            gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT | GL.GL_STENCIL_BUFFER_BIT);

            gl.glMatrixMode(GL.GL_MODELVIEW);
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
            arg0.setGL(new DebugGL(arg0.getGL()));
            
            GL gl = arg0.getGL();
            
            gl.glEnable(GL.GL_LIGHT0);
            gl.glEnable(GL.GL_LIGHTING);

            // I don't need it in this example
            gl.glDisable(GL.GL_DEPTH_TEST);
            
            gl.glStencilMask(~0);
            
            gl.glMatrixMode(GL.GL_PROJECTION);
            gl.glLoadIdentity();
            
            gl.glOrtho (0, _field.getSize().width, _field.getSize().height, 0, 0, 1);
        }

        public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
        }
        
    }
    static final long serialVersionUID=100;
    
    private static class PathRenderer implements GLRenderer {
        private final Collection<Point> _path;
        
        public PathRenderer(Collection<Point> path) {
            super();
            _path = path;
        }

        @Override
        public void render(GL gl) {
            GLPoint shape  = new GLPoint(new GLColor(Color.GREEN), 0, 0);
            for (Point point : _path) {
                shape.setLocation(point);
                shape.render(gl);
            }
        }
    }

}
