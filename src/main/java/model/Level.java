package model;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import model.helper.AudioHelper;
import model.helper.TiledMapHelper;
import model.objects.GameObjectFactory;
import model.objects.IGameObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Level {

    private static final float gravity = -26f;
    private final String levelName;
    private final String prettyLeveName;
    private final GameModel model;
    private final GameObjectFactory factory;
    private final HashMap<String, ArrayList<IGameObject>> objectMap;
    private final AudioHelper audioHelper;
    private final Music levelMusic;
    private World world;
    private Hud hud;
    private TiledMapHelper tiledMapHelper;
    private boolean levelCompleted;
    private Integer score = 0;
    private Vector2 topLeft;
    private Vector2 bottomRight;


    public Level(String levelName, GameModel model) {
        this.prettyLeveName = camelToSentence(levelName);
        this.levelName = levelName;
        this.model = model;
        factory = new GameObjectFactory(this);
        this.audioHelper = model.getAudioHelper();


        objectMap = new HashMap<>();

        objectMap.put("Player", new ArrayList<>());
        objectMap.put("Goomba", new ArrayList<>());
        objectMap.put("Coin", new ArrayList<>());
        objectMap.put("Goal", new ArrayList<>());
        objectMap.put("Floater", new ArrayList<>());
        objectMap.put("MapEndPoints", new ArrayList<>());
        objectMap.put("MovingPlatform", new ArrayList<>());

        createWorld(levelName);
        createObjects();
        createHUD();
        parseMapEndPoints();

        levelMusic = audioHelper.getLevelMusic(levelName);
    }

    private void createWorld(String level) {
        this.world = new World(new Vector2(0, gravity), false);
        this.world.setContactListener(new GameContactListener(this));
        this.tiledMapHelper = new TiledMapHelper(this, level);
    }

    private void createObjects() {
        for (Map.Entry<String, ArrayList<IGameObject>> set : objectMap.entrySet()) {
            if (set.getKey().equalsIgnoreCase("Player")) {
                List<Rectangle> spawnPoints = tiledMapHelper.parseMapRectangles(set.getKey());
                for (int i = 0; i < Math.min(model.getNumPlayers(), model.getNumControllers()); i++) {
                    set.getValue().add(factory.create(set.getKey(), spawnPoints.get(i)));
                }
            } else {
                for (Rectangle spawn : tiledMapHelper.parseMapRectangles(set.getKey())) {
                    set.getValue().add(factory.create(set.getKey(), spawn));
                }
            }
        }

        ArrayList<IGameObject> enemies = new ArrayList<>();
        enemies.addAll(objectMap.get("Goomba"));
        enemies.addAll(objectMap.get("Floater"));
        objectMap.put("Enemy", enemies);
    }

    private void parseMapEndPoints() {
        List<Vector2> mapEndPoints = tiledMapHelper.parseMapRectangles("MapEndPoints").stream().map((i) -> new Vector2(i.x, i.y)).toList();

        if (mapEndPoints.get(0).x < mapEndPoints.get(1).x) {
            topLeft = mapEndPoints.get(0);
            bottomRight = mapEndPoints.get(1);
        } else {
            topLeft = mapEndPoints.get(1);
            bottomRight = mapEndPoints.get(0);
        }
    }

    private void createHUD() {
        hud = new Hud(new SpriteBatch(), this);
    }

    public void updateHUD() {
        hud.update();
    }

    public Hud getHud() {
        return hud;
    }

    public List<IGameObject> getGameObjects() {
        List<IGameObject> objectList = new ArrayList<>();
        for (Map.Entry<String, ArrayList<IGameObject>> set : objectMap.entrySet()) {
            if (set.getKey().equals("Enemy")) { // We do not want to add enemy objects twice.
                continue;
            }
            objectList.addAll(set.getValue());
        }
        return objectList;
    }

    // Source: https://stackoverflow.com/a/19254882
    public <T extends IGameObject> List<T> getGameObjects(Class<T> type) {
        return (List<T>) objectMap.get(getClassName(type));
    }

    <T extends IGameObject> String getClassName(Class<T> type) {
        return type.toString().substring(type.toString().lastIndexOf('.') + 1);
    }

    public World getWorld() {
        return world;
    }

    public Integer getScore() {
        return score;
    }

    public Vector2 getTopLeft() {
        return topLeft;
    }

    public Vector2 getBottomRight() {
        return bottomRight;
    }

    public void setLevelCompleted(boolean value) {
        levelCompleted = value;
    }

    public OrthogonalTiledMapRenderer setupMap() {
        return tiledMapHelper.setupMap();
    }

    public boolean isCompleted() {
        return levelCompleted;
    }

    public void increaseScore(int value) {
        score += value;
    }

    //from https://dirask.com/posts/Java-convert-camelCase-to-Sentence-Case-jE6PZ1
    private String camelToSentence(String text) {
        if (!text.equals("")) {
            String result = text.replaceAll("([A-Z, 0-9])", " $1");
            return result.substring(0, 1).toUpperCase() + result.substring(1).toLowerCase();
        }
        return null;
    }

    public GameModel getModel() {
        return model;
    }

    public AudioHelper getAudioHelper() {
        return audioHelper;
    }

    public Music getLevelMusic() {
        return levelMusic;
    }

    @Override
    public String toString() {
        String[] splitFileName = levelName.split("/", 2);
        String levelName = splitFileName[splitFileName.length - 1].replace("-", " ");
        return Character.toUpperCase(levelName.charAt(0)) + levelName.substring(1);
    }

    public String getLevelName() {
        return levelName;
    }
}
