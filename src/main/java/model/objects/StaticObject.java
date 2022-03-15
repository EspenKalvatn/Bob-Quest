package model.objects;


import com.badlogic.gdx.physics.box2d.BodyDef;
import model.GameModel;
import model.helper.ContactType;

public abstract class StaticObject extends GameObject {


    public StaticObject(String name, GameModel gameModel, float x, float y, float density, ContactType contactType, short categoryBits, short maskBits, boolean isSensor, boolean polygon) {
        super(name, gameModel, x, y, density, contactType, BodyDef.BodyType.StaticBody, categoryBits, maskBits, isSensor, polygon);
    }

}
