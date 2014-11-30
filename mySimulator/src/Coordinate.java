/**
 * Created by oky on 14/11/27.
 */
public class Coordinate {

    private int x;
    private int y;
    private boolean isActive;

    public Coordinate(int x, int y,boolean isActive) {
        this.x = x;
        this.y = y;
        this.isActive = isActive

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
