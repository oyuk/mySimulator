/**
 * Created by oky on 14/11/30.
 */
public class MN_state {

    private int x;
    private int y;
    private boolean isActive;

    public MN_state(int x, int y, boolean isActive) {
        this.x = x;
        this.y = y;
        this.isActive = isActive;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isActive() {
        return isActive;
    }
}
