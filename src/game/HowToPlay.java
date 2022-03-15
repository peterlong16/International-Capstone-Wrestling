package game;

public class HowToPlay extends Action{
    HowToPlay(Character c) {
        super(c);
        type = "menu";
        name = "How to play";
    }

    @Override
    void Execute() {
        Map.howToPlay = true;
        Map.ToggleSettings();
    }
}
