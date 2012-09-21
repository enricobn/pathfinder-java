package jpathfinder.gl;

import jpathfinder.FieldShape;
import jpathfinder.Point;

public interface GLShape extends GLRenderer{

    FieldShape getFieldShape();
    
    void setLocation(Point point);

}
