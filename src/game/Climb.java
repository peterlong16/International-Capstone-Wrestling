package game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Climb extends Action{


    Climb(Character c) {
        super(c);
        type = "Context";
        name = "Climb";
        cost = 3;
        mover = false;
        sequence = new Boolean[]{true,false,false};
        img = user.sprites[0];
        targets = new Character[1];
    }

    @Override
    boolean canHit(Tile t, int distance) {
        for(Tile x: Map.neighbourTiles(user.CurTile)){
            if(x == t){
                return t.type < 5 || t.type == 7;
            }
        }

        return false;
    }

    @Override
    boolean gotTargets() {
        return targetTile!=null;
    }

    @Override
    void Execute() {
        ArrayList<Tile> neighbours = Map.neighbourTiles(targetTile);
        for(int i = 0; i<neighbours.size(); i++){
           if(neighbours.get(i).type == 8 && i % 2 == 0){
               user.orient(user.FindDir(neighbours.get(i),targetTile));
           }
        }
        user.orientation = user.FindDir(user.CurTile,targetTile);
        user.sprite.setImage(user.rotate((BufferedImage)user.sprites[0], user.rot));
        user.setTile(targetTile);
        user.moving = true;
    }
}
