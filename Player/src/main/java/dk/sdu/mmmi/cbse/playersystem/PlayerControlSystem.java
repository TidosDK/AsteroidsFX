package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.player.Player;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.player.PlayerSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class PlayerControlSystem implements IEntityProcessingService, PlayerSPI {

    private static final double SPEED = 1.25;
    private static final double ROTATIONSPEED = 3;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            // When the LEFT arrow button is pressed, rotates to the left.
            if (gameData.getKeys().isDown(GameKeys.LEFT)) {
                player.setRotation(player.getRotation() - ROTATIONSPEED * SPEED * (1 + gameData.getDeltaSeconds()));
            }

            // Same for the RIGHT.
            if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
                player.setRotation(player.getRotation() + ROTATIONSPEED * SPEED * (1 + gameData.getDeltaSeconds()));
            }

            // When the UP arrow button is pressed, changes the players coordinates to move forward.
            if (gameData.getKeys().isDown(GameKeys.UP)) {
                double changeX = Math.cos(Math.toRadians(player.getRotation())) * SPEED * (1 + gameData.getDeltaSeconds());
                double changeY = Math.sin(Math.toRadians(player.getRotation())) * SPEED * (1 + gameData.getDeltaSeconds());
                player.setX(player.getX() + changeX);
                player.setY(player.getY() + changeY);
            }

            if (gameData.getKeys().isPressed(GameKeys.SPACE)) {
                for (BulletSPI bulletSPI : getBulletSPIs()) {
                    world.addEntity(bulletSPI.createBullet(player, gameData));
                }
            }

            // TODO: Add configuration for disabling the border.
            // Ensures the player doesn't go out of bound.
            if (player.getX() < 0) { // LEFT BORDER
                player.setX(1);
            }
            if (player.getX() > gameData.getDisplayWidth()) { // RIGHT BORDER
                player.setX(gameData.getDisplayWidth() - 1);
            }
            if (player.getY() < 0) { // TOP BORDER
                player.setY(1);
            }
            if (player.getY() > gameData.getDisplayHeight()) { // BOTTOM BORDER
                player.setY(gameData.getDisplayHeight() - 1);
            }
        }
    }

    @Override
    public Entity createPlayer(Entity e, GameData gameData) {
        Entity player = new Player();
        return player;
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

}
