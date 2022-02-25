package core;

import com.badlogic.gdx.physics.box2d.*;
import helper.Constants;
import helper.ContactType;

public class GameContactListener implements ContactListener {

    private GameScreen gameScreen;

    public GameContactListener(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }


    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null)
            return;
        if (a.getUserData() == null || b.getUserData() == null)
            return;


        if (a.getUserData() == ContactType.GROUND || b.getUserData() == ContactType.GROUND) {

            // Contact between foot-sensor of an object and GROUND
            if (a.getUserData().equals("foot") || b.getUserData().equals("foot")) {
                gameScreen.getPlayer().setGrounded(true);
                //System.out.println("Is on ground: " + value);
            }

            if (a.getUserData().equals("head") || b.getUserData().equals("head")) {
                System.out.println("Collision between head and ground!");
            }

            if (a.getUserData().equals("left") || b.getUserData().equals("left")) {
                gameScreen.getPlayer().setSideCollision(true);
                System.out.println("Collision between player and ground!");
            }

            if (a.getUserData().equals("right") || b.getUserData().equals("right")) {
                gameScreen.getPlayer().setSideCollision(true);
                System.out.println("Collision between player and ground!");
            }

        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null)
            return;
        if (a.getUserData() == null || b.getUserData() == null)
            return;

        if (a.getUserData() == ContactType.GROUND || b.getUserData() == ContactType.GROUND) {
            // Contact between PLAYER and GROUND
            if (a.getUserData().equals("foot") || b.getUserData().equals("foot")) {
                gameScreen.getPlayer().setGrounded(false);
                //System.out.println("Is on ground: " + value);
            }
        }

        if (a.getUserData().equals("left") || b.getUserData().equals("left")) {
            gameScreen.getPlayer().setSideCollision(false);
            System.out.println("Collision between player and ground!");
        }

        if (a.getUserData().equals("right") || b.getUserData().equals("right")) {
            gameScreen.getPlayer().setSideCollision(false);
            System.out.println("Collision between player and ground!");
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
