package game;

public class Quitgame extends Action{
    Quitgame(Character c) {
        super(c);
        type = "menu";
        name = "Quit Game";
    }

    @Override
    void Execute() {
        System.exit(0);
    }
}
