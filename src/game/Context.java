package game;

import static game.Map.findContextual;
import static game.Map.context;

public class Context extends Action{
    Context(Character c) {
        super(c);
        mover = false;
        type = "menu";
        cost = 0;
        desc = "Display all contextual actions";
        name = "Context";
    }

    boolean canAfford(){
        return true;
    }

    void Execute(){
        user.atk = this;
        context = new Button[4];
        if(findContextual()[0]!=null){
            for(int i = 0;i< findContextual().length;i++){
                if(findContextual()[i]!=null) {
                    context[i] = new Button(findContextual()[i], 25, 120);
                }
            }
        }
        Map.MenuSelect = 3;
    }
}
