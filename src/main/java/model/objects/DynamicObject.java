package model.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import model.GameModel;
import model.helper.ContactType;

public class DynamicObject extends GameObject {

    public DynamicObject(String name, GameModel gameModel, float x, float y, float density, ContactType contactType, short categoryBits, short maskBits) {
        super(name, gameModel, x, y, density, contactType, BodyDef.BodyType.DynamicBody, categoryBits, maskBits, false);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch batch) {

    }
}
