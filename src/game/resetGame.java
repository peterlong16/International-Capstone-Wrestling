package game;

public class resetGame extends Action{
    resetGame(Character c) {
        super(c);
        type = "reset";
        name = "Reset";
    }

    void Execute(){
        Map.resetGame();
    }
}
