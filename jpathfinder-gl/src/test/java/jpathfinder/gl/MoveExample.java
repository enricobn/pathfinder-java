package jpathfinder.gl;

import com.jogamp.opengl.util.Animator;
import jpathfinder.AStarPathFinder;
import jpathfinder.Dimension;
import jpathfinder.FieldShape;
import jpathfinder.PathField;
import jpathfinder.Point;
import jpathfinder.Rectangle;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/*
 * 
 * Intel(R) Core(TM) i7 CPU       M 620  @ 2.67GHz
 * millis: 5370
 *
 */
public class MoveExample extends JFrame{
    private static final int SIZE_COEFF = 5;
    private static final int MOVING_SHAPES_COUNT = 50;
        
    public static void main(String[] args) {
        PathField pathField = new PathField(new Dimension(100, 100));
        
        GLField glField = new GLField(pathField, new Dimension(100 * SIZE_COEFF, 100 *SIZE_COEFF));
        glField.add(new GLRectangle(glField, GLColor.WHITE, new Point(10 * SIZE_COEFF, 10 * SIZE_COEFF), 10 * SIZE_COEFF, 10 * SIZE_COEFF));
        glField.add(new GLRectangle(glField, GLColor.WHITE, new Point(40 * SIZE_COEFF, 20 * SIZE_COEFF), 20 * SIZE_COEFF, 20 * SIZE_COEFF));
        glField.add(new GLRectangle(glField, GLColor.WHITE, new Point(40 * SIZE_COEFF, 60 * SIZE_COEFF), 20 * SIZE_COEFF, 20 * SIZE_COEFF));
        glField.add(new GLRectangle(glField, GLColor.WHITE, new Point(75 * SIZE_COEFF, 75 * SIZE_COEFF), 10 * SIZE_COEFF, 10 * SIZE_COEFF));

        Collection<MovingShape> movingShapes = new ArrayList<>();
        
        for (int i = 0; i < MOVING_SHAPES_COUNT ; i++) {
            Point start = new Point(0 , SIZE_COEFF * (MOVING_SHAPES_COUNT - i));
            Point end = new Point(90 * SIZE_COEFF, (99 - i) * SIZE_COEFF);
            MovingShape movingShape = new MovingShape(
                    glField,
                    new GLRectangle(glField, GLColor.BLUE, new Rectangle(start, 5, 5)),
//                    new GLArrow(glField, GLColor.RED, start.getX(), start.getY(), 5, start.angle(end)),
                    end);
            movingShapes.add(movingShape);

            movingShape = new MovingShape(
                    glField,
//                    new GLArrow(glField, GLColor.BLUE, end.getX(), end.getY(), 5, end.angle(start)),
                    new GLRectangle(glField, GLColor.RED, new Rectangle(end, 5, 5)),
                    start);
            movingShapes.add(movingShape);
        }
        
        MoveExample frame = new MoveExample(glField, movingShapes);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    private final PathField _pathField;
    private final GLField _field;
    private final List<GLRenderer> _renderers = Collections.synchronizedList(new ArrayList<>());
    private Collection<MovingShape> _movingShapes;
    private final long startTime = System.currentTimeMillis();

    public MoveExample(GLField field, Collection<MovingShape> movingShapes){
        _field = field;
        _pathField = field.getPathField();
        _movingShapes = movingShapes;

        _renderers.add(_field);
        
        for (MovingShape movingShape : movingShapes) {
            field.add(movingShape.getGlShape());
        }
        
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
        Animator animator = new Animator(canvas); //new FPSAnimator(canvas, 10);
        animator.start();
    }
    
    public class GraphicsListener implements GLEventListener{
        private boolean ended = false;

        public void display(GLAutoDrawable arg0) {
            if (!ended) {
                boolean exit = true;
                for (MovingShape movingShape : _movingShapes) {
                    movingShape.next(_pathField);
                    exit &= movingShape.isArrived();
                }
                if (exit) {
                    ended = true;
                    System.out.println("millis: " + (System.currentTimeMillis() - startTime));
                }
            }
            
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

        @Override
        public void dispose(GLAutoDrawable glAutoDrawable) {
        }

        public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
        }
        
    }

    private static class MovingShape {
        private final GLField _glField;
        private final GLShape _glShape;
        private final FieldShape _fieldShape;
        private final Point _end;
        
        public MovingShape(GLField glField, GLShape glShape, Point end) {
            super();
            _glField = glField;
            _glShape = glShape;
            _fieldShape = glShape.getFieldShape();
            _end = end;
        }

        public void next(PathField pathField) {
            if (!isArrived()) {
                AStarPathFinder finder = new AStarPathFinder(pathField, _fieldShape.getLocation(),  _glField.toPathField(_end));
                List<Point> path = finder.getPath();
                if (path != null && !path.isEmpty()) {
                    Point next = path.get(path.size() - 1);
                    _glShape.setLocation(_glField.fromPathField(next));
                }
            }
        }
        
        public boolean isArrived() {
            return _fieldShape.getLocation().equals(_glField.toPathField(_end));
        }
        
        public GLShape getGlShape() {
            return _glShape;
        }
        
    }
    
}
