package game;


public class Button {
    int x1, x2;
    int y1, y2;
    int size;
    Action action;


    Button(Action action) {
        this.size = 20;
        this.action = action;
    }

    void setX(int x){
        this.x1 = x;
        this.x2 = (x1 + size);
    }

    void setY(int y){
        this.y1 = y;
        this.y2 = (y1 + size);
    }

}
