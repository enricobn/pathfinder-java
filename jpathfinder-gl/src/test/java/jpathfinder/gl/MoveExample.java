package jpathfinder.gl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
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
import jpathfinder.Field;
import jpathfinder.RectangleShape;
import jpathfinder.Shape;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

public class MoveExample extends JFrame{
    private final Field _field;
    
    private static final int SIZE_COEFF = 1;
    private static final int MOVING_SHAPES_COUNT = 50;
    
    private static List<MovingShape> movingShapes = new ArrayList<MoveExample.MovingShape>();
    private final long startTime = System.currentTimeMillis();
    
    public static void main(String[] args) {
        
        GLField field = new GLField(new Dimension(100 * SIZE_COEFF, 100 * SIZE_COEFF));
        field.add(new GLRectangleShape(new Rectangle(10 * SIZE_COEFF, 10 * SIZE_COEFF, 10 * SIZE_COEFF, 10 * SIZE_COEFF)));

        field.add(new GLRectangleShape(new Rectangle(40 * SIZE_COEFF, 20 * SIZE_COEFF, 20 * SIZE_COEFF, 20 * SIZE_COEFF)));

        field.add(new GLRectangleShape(new Rectangle(40 * SIZE_COEFF, 60 * SIZE_COEFF, 20 * SIZE_COEFF, 20 * SIZE_COEFF)));

        field.add(new GLRectangleShape(new Rectangle(75 * SIZE_COEFF, 75 * SIZE_COEFF, 10 * SIZE_COEFF, 10 * SIZE_COEFF)));

//        List<MovingShape> movingShapes = new ArrayList<MoveExample.MovingShape>();

        for (int i = 0; i < MOVING_SHAPES_COUNT ; i++) {
            Point start = new Point(0, MOVING_SHAPES_COUNT - i);
            Point end = new Point(90 * SIZE_COEFF, 99 * SIZE_COEFF  - i);
            movingShapes.add(new MovingShape(field, new GLPointShape(new GLColor(Color.RED), start), end));
            
            movingShapes.add(new MovingShape(field, new GLPointShape(new GLColor(Color.BLUE), end), start));
        }
        
        MoveExample frame = new MoveExample(field, movingShapes);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    private final List<GLRenderer> _renderers = Collections.synchronizedList(new ArrayList<GLRenderer>());
    
    public MoveExample(final GLField field, final List<MovingShape> movingShapes){
        _field = field;
        _renderers.add(field);
        
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
        private final GLPointShape _shape;
        private final Point _end;
        private AStarPathFinder _currentFinder = null;
        
        public MovingShape(Field field, GLPointShape startShape, Point end) {
            super();
            _field = field;
            _shape = startShape;
            _end = end;
            _field.add(startShape);
        }

        public void next() {
            if (!isArrived()) {
                _currentFinder = new AStarPathFinder(_field, _shape.getLocation(), _end);
                List<Point> path = _currentFinder.getPath();
                if (path != null && !path.isEmpty()) {
                    _shape.setLocation(path.get(path.size() -1));
                }
            }
        }
        
        public boolean isArrived() {
            return _shape.getLocation().equals(_end);
        }
        
        @Override
        public void render(GL gl) {
            _shape.render(gl);
//            if (_currentFinder != null) {
//                if (_currentFinder instanceof GLRenderer) {
//                    ((GLRenderer)_currentFinder).render(gl);
//                }
//            }
        }
        
    }
    
}
