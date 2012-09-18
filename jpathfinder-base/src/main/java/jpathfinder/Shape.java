package jpathfinder;

public interface Shape {

//    Point getLocation();
//    
    void setLocation(Point location);
    
    boolean contains(Point point);
    
    void move(int xDiff, int yDiff);
    
    boolean isMoving();
        
}
