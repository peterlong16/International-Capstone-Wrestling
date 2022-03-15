package game;

public class Settings extends Action{
    Settings(Character c) {
        super(c);
        type = "menu";
        name = "Play";
    }

    @Override
    void Execute() {
        Map.ToggleSettings();
    }
}
