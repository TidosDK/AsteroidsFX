package dk.sdu.mmmi.cbse.common.data;

public class GameData {

    private int displayWidth = 800;
    private int displayHeight = 800;
    private final GameKeys keys = new GameKeys();

    private long delta;
    private long deltaStacking;

    public GameKeys getKeys() {
        return keys;
    }

    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public long getDelta() {
        return this.delta;
    }

    public void setDelta(long delta) {
        this.delta = delta;
        this.deltaStacking += delta;
    }

    public double getDeltaSeconds() {
        return this.delta * 0.000000001;
    }

    public long getDeltaStacking() {
        return this.deltaStacking;
    }

    public void setDeltaStacking(long deltaStacking) {
        this.deltaStacking = deltaStacking;
    }

}
