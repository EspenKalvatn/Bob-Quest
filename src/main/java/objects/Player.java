package objects;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import core.Controller;

public class Player {

    final private String name;
    final private Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private Controller controller;
//    private boolean facingRight;
//    private boolean grounded;

//    enum State {
//        IDLE,
//        WALKING,
//        JUMPING
//    }

    public Player(String name, String texturePath, int positionX, int positionY) {
        this.name = name;
        this.texture = new Texture(texturePath);
        this.position = new Vector2(positionX, positionY);
        this.velocity = new Vector2(2, 2);
        this.controller = new Controller(this, Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.DOWN);
//        facingRight = true;
//        grounded = true;
    }


    public String getName() {
        return name;
    }


    public Vector2 getPosition() {
        return position;
    }


    public Vector2 getVelocity() {
        return velocity;
    }


    public Texture getTexture() {
        return texture;
    }


    public void setPosition(Vector2 newPosition) {
        position = newPosition;
    }


    public void setPosition(float x, float y) {
        position = new Vector2(x, y);
    }


    public void setVelocity(Vector2 newVelocity) {
        velocity = newVelocity;
    }


    public void setVelocity(float x, float y) {
        velocity = new Vector2(x, y);
    }

    public void controll(){
        controller.inputListener();
    }

    public void setAnimation(int animation) {
        // TODO: Implement
    }

}
