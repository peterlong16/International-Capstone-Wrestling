package game;

public class closeHelp extends Action{
    closeHelp(Character c) {
        super(c);
        type = "menu";
        name = "Close Help";
    }

    @Override
    void Execute() {
        Map.howToPlay = false;

    }
}
