package jpathfinder;

public class Dimension {
    public final int width;
    public final int height;
    
    public Dimension(int width, int height) {
        super();
        this.width = width;
        this.height = height;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + height;
        result = prime * result + width;
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Dimension other = (Dimension) obj;
        if (height != other.height)
            return false;
        if (width != other.width)
            return false;
        return true;
    }
    
    
    
}
