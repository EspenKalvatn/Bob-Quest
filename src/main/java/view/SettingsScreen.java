package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import launcher.Boot;
import model.GameModel;

public class SettingsScreen extends AbstractScreen {

    TextButton fullScreen;
    TextButton speedRun;
    boolean speed = false;

    public SettingsScreen(GameModel gameModel) {
        super(gameModel);
    }

    @Override
    public void show() {
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Table table = new Table();
        table.center().top().padTop(Boot.INSTANCE.getScreenHeight() / 4f);
        table.setFillParent(true);
        stage.addActor(table);

        Label settings = new Label("Settings", font);
        settings.setFontScale(4f);

        Label musicVolumeLabel = new Label("Music volume", font);
        musicVolumeLabel.setFontScale(1.5f);

        Slider musicVolumeSlider = new Slider(0, 1, 0.1f, false, skin);
        musicVolumeSlider.setValue(gameModel.getMusicVolume());

        Label soundEffectsVolumeLabel = new Label("Sound effects volume", font);
        soundEffectsVolumeLabel.setFontScale(1.5f);
        Slider soundEffectsVolumeSlider = new Slider(0, 1, 0.1f, false, skin);
        soundEffectsVolumeSlider.setValue(gameModel.getSoundEffectsVolume());

        Label fullScreenLabel = new Label("Full Screen toggle:", font);
        fullScreenLabel.setFontScale(1.5f);
        fullScreen = new TextButton("", skin);

        Label speedRunLabel = new Label("Speedrun toggle:", font);
        speedRunLabel.setFontScale(1.5f);
        speedRun = new TextButton("", skin);


        TextButton back = new TextButton("Back", skin);

        table.add(settings).center().colspan(2);
        table.row();
        table.add(musicVolumeLabel).padTop(20);
        table.add(musicVolumeSlider).padTop(20).minWidth(250).minHeight(50);
        table.row();
        table.add(soundEffectsVolumeLabel).padTop(20);
        table.add(soundEffectsVolumeSlider).padTop(20).minWidth(250).minHeight(50);
        table.row();
        table.add(fullScreenLabel).padTop(20);
        table.add(fullScreen).padTop(20).minWidth(150).minHeight(50).colspan(2);
        table.row();
        table.add(speedRunLabel).padTop(20);
        table.add(speedRun).padTop(20).minWidth(150).minHeight(50).colspan(2);
        table.row();
        table.add(back).padTop(20).minWidth(150).minHeight(50).colspan(2);

        musicVolumeSlider.addListener(Boot.INSTANCE.getGameController().volumeListener(musicVolumeSlider, true));
        soundEffectsVolumeSlider.addListener(Boot.INSTANCE.getGameController().volumeListener(soundEffectsVolumeSlider, false));
        back.addListener(Boot.INSTANCE.getGameController().goBackListener());
        fullScreen.addListener(Boot.INSTANCE.getGameController().fullScreenListener());
        speedRun.addListener(Boot.INSTANCE.getGameController().speedListener());


    }

    @Override
    public void render(float delta) {
        if(Gdx.graphics.isFullscreen()){
            fullScreen.setText("Windowed");
        } else {
            fullScreen.setText("Fullscreen");
        }
        if(gameModel.isSpeedRun()){
            speedRun.setText("Normal Mode");
        } else {
            speedRun.setText("Speed Mode");
        }
        super.render(delta);
        this.renderBackground();
    }

}
