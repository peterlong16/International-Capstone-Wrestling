package game;

public class resetGame extends Action{
    resetGame(Character c) {
        super(c);
        type = "reset";
    }

    void Execute(){
        Map.resetGame();
    }
}
