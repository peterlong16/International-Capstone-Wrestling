package game;

public class Path {
    Tile[] Tiles;
    int cost;

    public Path(Tile t){
        Tiles = new Tile[1];
        Tiles[0] = t;
    }

    public void prependStep(Tile t){
        Tile[] newTiles = new Tile[Tiles.length+1];
        newTiles[0] = t;
        System.arraycopy(Tiles, 0, newTiles, 1, Tiles.length);
        Tiles = newTiles;
    }
}
