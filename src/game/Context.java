package game;

import static game.Map.findContextual;
import static game.Map.context;

public class Context extends Action{
    Context(Character c) {
        super(c);
        mover = false;
        type = "menu";
        cost = 0;
        name = "Context";
    }

    boolean canAfford(){
        return true;
    }

    void Execute(){
        context = new Button[1];
        if(findContextual()[0]!=null){
            context[0] = new Button(findContextual()[0], 25,120);
        }
        Map.MenuSelect = 3;
    }
}
