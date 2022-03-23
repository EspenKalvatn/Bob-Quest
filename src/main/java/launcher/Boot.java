package launcher;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import model.GameModel;
import view.GameCamera;
import view.StartScreen;

public class Boot extends Game {

    public static Boot INSTANCE;
    private int screenWidth, screenHeight;
    private GameCamera camera;
    private GameModel gameModel;

    public Boot() {
        INSTANCE = this;
    }

    @Override
    public void create() {
        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();
        this.gameModel = new GameModel();
        this.camera = new GameCamera(gameModel);
        this.camera.setToOrtho(false, screenWidth, screenHeight);

        setScreen(new StartScreen(gameModel));
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public GameCamera getCamera() {
        return camera;
    }
}
