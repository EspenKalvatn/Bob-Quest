package model.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import model.Level;
import model.helper.Constants;
import model.helper.ContactType;

public class Goal extends StaticObject {

    private final Animation<TextureRegion> idleAnimation;
    private float stateTime;

    public Goal(String name, Level level, float x, float y) {
        super(name + " " + (level.getGameObjects(Goal.class).size() + 1), level, Constants.TILE_SIZE, Constants.TILE_SIZE * 2, x, y, 0, ContactType.GOAL, Constants.GOAL_BIT, Constants.INTERACTIVE_MASK_BITS, true, true);
        texture = new Texture("Multi_Platformer_Tileset_v2/WorldObjects/Green_Portal_Sprite_Sheet.png");

        TextureRegion[] frames = TextureRegion.split(getTexture(), Constants.TILE_SIZE, Constants.TILE_SIZE * 2)[0];
        idleAnimation = new Animation<>(0.1875f, frames); // 1.5s animation duration
    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(getFrame(), x - width / 2, y - height / 2, width, height);
    }

    @Override
    protected TextureRegion getFrame() {
        stateTime += Gdx.graphics.getDeltaTime();
        return idleAnimation.getKeyFrame(stateTime, true);
    }

    public void onHit() {
        level.setLevelCompleted(true);
    }
}
