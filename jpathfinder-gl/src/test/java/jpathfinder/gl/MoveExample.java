package jpathfinder.gl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.swing.JFrame;

import jpathfinder.AStarPathFinder;
import jpathfinder.Dimension;
import jpathfinder.Field;
import jpathfinder.Point;

import com.sun.opengl.util.Animator;

public class MoveExample extends JFrame{
    private final Field _field;
    
    private static final int SIZE_COEFF = 1;
    private static final int MOVING_SHAPES_COUNT = 50;
    
    private static List<MovingShape> movingShapes = new ArrayList<MoveExample.MovingShape>();
    private final long startTime = System.currentTimeMillis();
    
    public static void main(String[] args) {
        Collection<GLRenderer> renderers = new ArrayList<GLRenderer>();
        GLField field = new GLField(new Dimension(100 * SIZE_COEFF, 100 * SIZE_COEFF));
        
        GLRectangle rect = new GLRectangle(new Point(10 * SIZE_COEFF, 10 * SIZE_COEFF), 10 * SIZE_COEFF, 10 * SIZE_COEFF); 
        renderers.add(rect);
        field.add(rect.getRectangle());

        rect = new GLRectangle(new Point(40 * SIZE_COEFF, 20 * SIZE_COEFF), 20 * SIZE_COEFF, 20 * SIZE_COEFF);
        renderers.add(rect);
        field.add(rect.getRectangle());
        
        rect = new GLRectangle(new Point(40 * SIZE_COEFF, 60 * SIZE_COEFF), 20 * SIZE_COEFF, 20 * SIZE_COEFF);
        renderers.add(rect);
        field.add(rect.getRectangle());
        
        rect = new GLRectangle(new Point(75 * SIZE_COEFF, 75 * SIZE_COEFF), 10 * SIZE_COEFF, 10 * SIZE_COEFF);
        renderers.add(rect);
        field.add(rect.getRectangle());

        for (int i = 0; i < MOVING_SHAPES_COUNT ; i++) {
            Point start = new Point(0, MOVING_SHAPES_COUNT - i);
            Point end = new Point(90 * SIZE_COEFF, 99 * SIZE_COEFF  - i);
            MovingShape movingShape = new MovingShape(field, new GLPoint(new GLColor(Color.RED), 
                    start.getX(), start.getY()), 
                    end);
            movingShapes.add(movingShape);
            renderers.add(movingShape);
            field.add(movingShape._glPoint.getPoint());

            movingShape = new MovingShape(field, new GLPoint(new GLColor(Color.BLUE), 
                    end.getX(), end.getY()), 
                    start);
            movingShapes.add(movingShape);
            renderers.add(movingShape);
            field.add(movingShape._glPoint.getPoint());
        }
        
        MoveExample frame = new MoveExample(field, renderers);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    private final List<GLRenderer> _renderers = Collections.synchronizedList(new ArrayList<GLRenderer>());
    
    public MoveExample(final GLField field, final Collection<GLRenderer> renderers){
        _field = field;
        _renderers.add(field);
        _renderers.addAll(renderers);
        
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
        Animator animator = new Animator(canvas); //new FPSAnimator(canvas, 10);  
        animator.start();
    }
    
    public class GraphicsListener implements GLEventListener{
        

        public void display(GLAutoDrawable arg0) {
            boolean exit = true; 
            for (MovingShape movingShape : movingShapes) {
                movingShape.next();
                exit &= movingShape.isArrived();
            }
            if (exit) {
                System.out.println("millis: " + (System.currentTimeMillis() - startTime));
                System.exit(0);
            }
            
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

    private static class MovingShape implements GLRenderer {
        private final Field _field;
        private final GLPoint _glPoint;
        private final Point _end;
        
        public MovingShape(Field field, GLPoint startShape, Point end) {
            super();
            _field = field;
            _glPoint = startShape;
            _end = end;
        }

        public void next() {
            if (!isArrived()) {
                AStarPathFinder finder = new AStarPathFinder(_field, _glPoint.getPoint(), _end);
                List<Point> path = finder.getPath();
                if (path != null && !path.isEmpty()) {
                    _glPoint.setLocation(path.get(path.size() -1));
                }
            }
        }
        
        public boolean isArrived() {
            return _glPoint.getPoint().equals(_end);
        }
        
        @Override
        public void render(GL gl) {
            _glPoint.render(gl);
//            if (_currentFinder != null) {
//                if (_currentFinder instanceof GLRenderer) {
//                    ((GLRenderer)_currentFinder).render(gl);
//                }
//            }
        }
        
    }
    
}
