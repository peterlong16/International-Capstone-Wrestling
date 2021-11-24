package game;


public class Button {
    int x1, x2;
    int y1, y2;
    int height;
    int width;
    Action action;
    boolean active;



    Button(Action action, int height, int width) {
        this.height = height;
        this.width = width;
        this.action = action;
    }

    Button(Action action,int height, int width, int x, int y) {
        this.x1 = x;
        this.x2 = (x1 + width);
        this.y1 = y;
        this.y2 = (y1 + height);
        this.height = height;
        this.width = width;
        this.action = action;
    }

    void setX(int x){
        this.x1 = x;
        this.x2 = (x1 + width);
    }

    void setY(int y){
        this.y1 = y;
        this.y2 = (y1 + height);
    }

    @Override
    public String toString() {
        return "Button{" +
                "x1=" + x1 +
                ", x2=" + x2 +
                ", y1=" + y1 +
                ", y2=" + y2 +
                ", height=" + height +
                ", width =" + width+
                ", action=" + action +
                '}';
    }
}
