package helper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;


public class TiledMapHelper {

    private TiledMap tiledMap;
    private TiledMapTileLayer backgroundLayer;
    private TiledMapTileLayer playerLayer;
    private Texture player;
    private TiledMapTileLayer.Cell playerCell;

    public TiledMapHelper() {
        tiledMap = new TmxMapLoader().load("maps/level0.tmx");
        backgroundLayer = getBoardLayer("Background");
        playerLayer = getBoardLayer("Player");
        player = new Texture("player_stick.png");
        playerCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(TextureRegion.split(player, 64, 64)[0][0]));
    }

    public TiledMapTileLayer getBoardLayer(String layer) {
        return (TiledMapTileLayer) tiledMap.getLayers().get(layer);
    }

    public TiledMapTileLayer.Cell getPlayerCell() {
        return playerCell;
    }

    public OrthogonalTiledMapRenderer setupMap() {
        return new OrthogonalTiledMapRenderer(tiledMap); // TODO: Use proper unit scale
    }
}
