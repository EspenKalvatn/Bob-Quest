package model;

import com.badlogic.gdx.physics.box2d.*;
import model.helper.ContactType;
import model.helper.TiledMapHelper;
import model.objects.Coin;
import model.objects.Goomba;
import model.objects.Player;
import java.util.List;

public class GameContactListener implements ContactListener {

    private GameModel gameModel;

    public GameContactListener(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null)
            return;
        if (a.getUserData() == null || b.getUserData() == null)
            return;

        groundContact(a,b,true);
        //headContact(a,b,true);
        horizontalContact(a,b,true,true); // Right contact
        horizontalContact(a,b,true,false); // Left contact

        coinContact(a,b);
        goombaContact(a, b);

        deathContact(a,b);
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null)
            return;
        if (a.getUserData() == null || b.getUserData() == null)
            return;

        groundContact(a,b,false);
        //headContact(a,b,false);
        horizontalContact(a,b,false,true); // Right contact
        horizontalContact(a,b,false,false); // Left contact
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

    /**
     * This function should find and return the correct player
     * involved in a given collision between to Fixtures: a and b.
     * @param a - The first Fixture involved in the contact.
     * @param b - The second Fixture involved in the contact.
     * @return Return the player involved in the contact.
     */
    private Player getContactPlayer(Fixture a, Fixture b) {
        List<Player> players = gameModel.getPlayers();

        Fixture p = a.getUserData() == ContactType.PLAYER ? a : b;

        for (Player player : players) {
            if (player.getBody().equals(p.getBody())) {
                return player;
            }
        }
        return null; // TODO: Handle null player
    }

    private void deathContact(Fixture a, Fixture b) {
        if (a.getUserData() == ContactType.DEATH || b.getUserData() == ContactType.DEATH) {
            if (a.getUserData() == ContactType.PLAYER || b.getUserData() == ContactType.PLAYER) {
                Player player = getContactPlayer(a,b);
                player.setDead();
                System.out.println(player.getCurrentState());
            }
        }
    }

    private void coinContact(Fixture a, Fixture b) {
        if (a.getUserData() == ContactType.COIN || b.getUserData() == ContactType.COIN) {
            if (a.getUserData() == ContactType.PLAYER || b.getUserData() == ContactType.PLAYER) {

                // Finding out which of the fixtures is a Player and Coin.
                Fixture p = a.getUserData() == ContactType.PLAYER ? a : b; // Use the sane for players! ^^^
                Fixture c = p == a ? b : a;

                for (Coin coin : TiledMapHelper.getCoins()) {
                    if (coin.getBody().equals(c.getBody())) {
                        coin.onHit();
                        gameModel.increaseScore(100);
                    }
                }
            }
        }
    }

    /**
     * Checks if a contact is between a Player and a Goomba object.
     * If yes, damages player.
     *
     * @param a - The first Fixture involved in the contact.
     * @param b - The second Fixture involved in the contact.
     */
    private void goombaContact(Fixture a, Fixture b) {
        if (a.getUserData() == ContactType.GOOMBA || b.getUserData() == ContactType.GOOMBA) {
            if (a.getUserData() == ContactType.PLAYER || b.getUserData() == ContactType.PLAYER) {
                Player player = getContactPlayer(a, b);
                player.takeDamage(Goomba.getAttack());
                System.out.println(player.hp);
            }
        }
    }

    private void groundContact(Fixture a, Fixture b, boolean begin) {
        if (a.getUserData().equals("foot") || b.getUserData().equals("foot")) {
            if (a.getUserData() == ContactType.GROUND || b.getUserData() == ContactType.GROUND) {
                getContactPlayer(a,b).setGrounded(begin);
            }
            if (a.getUserData() == ContactType.PLATFORM || b.getUserData() == ContactType.PLATFORM) {
                getContactPlayer(a,b).setGrounded(begin);
            }

        }
    }
/*
    private void headContact(Fixture a, Fixture b, boolean begin) {
        if (a.getUserData() == ContactType.GROUND || b.getUserData() == ContactType.GROUND) {
            if (a.getUserData().equals("head") || b.getUserData().equals("head")) {
                // TODO: implement logic
            }
        }
    }

 */

    private void horizontalContact(Fixture a, Fixture b, boolean begin, boolean right) {
        String direction;
        if (right)
            direction = "right";
        else
            direction = "left";

        if (a.getUserData() == ContactType.GROUND || b.getUserData() == ContactType.GROUND) {
            if (a.getUserData().equals(direction) || b.getUserData().equals(direction)) {
                getContactPlayer(a,b).setSideCollision(begin);
            }
        }

    }

}
