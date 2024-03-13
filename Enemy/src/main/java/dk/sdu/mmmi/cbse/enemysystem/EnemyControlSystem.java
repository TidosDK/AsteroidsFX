package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;


public class EnemyControlSystem implements IEntityProcessingService {

    private static final double SPEED = 0.75;
    private static final double ROTATIONSPEED = 1.75;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {

            // Constant movement handling
            double changeX = Math.cos(Math.toRadians(enemy.getRotation())) * SPEED * (1 + gameData.getDeltaSeconds());
            double changeY = Math.sin(Math.toRadians(enemy.getRotation())) * SPEED * (1 + gameData.getDeltaSeconds());

            enemy.setX(enemy.getX() + changeX);
            enemy.setY(enemy.getY() + changeY);


            // Rotation handling
            for (Entity player : world.getEntities()) {
                if (player.isPlayer()) {
                    double distanceX = player.getX() - enemy.getX();
                    double distanceY = player.getY() - enemy.getY();

                    double angle = Math.toDegrees(Math.atan2(distanceY, distanceX));

                    angle = (angle + 360) % 360;

                    if (angle > 180) {
                        angle -= 360;
                    }

                    double delta = enemy.getRotation() - angle;
                    if (delta > 180) {
                        delta -= 360;
                    } else if (delta <= -180) {
                        delta += 360;
                    }

                    if (Math.abs(delta) > ROTATIONSPEED) {
                        if (delta > 0) {
                            enemy.setRotation(enemy.getRotation() - ROTATIONSPEED);
                        } else {
                            enemy.setRotation(enemy.getRotation() + ROTATIONSPEED);
                        }
                    } else {
                        enemy.setRotation(angle);
                    }


                    // Shooting handling
                    if (angle - 7.5 < enemy.getRotation() && angle + 7.5 > enemy.getRotation()) {
                        for (BulletSPI bulletSPI : getBulletSPIs()) {
                            world.addEntity(bulletSPI.createBullet(enemy, gameData));
                            // TODO: Cooldown for shooting
                        }
                    }
                }
            }


            // TODO: Add configuration for disabling the border.
            // Ensures the enemy doesn't go out of bound.
            if (enemy.getX() < 0) { // LEFT BORDER
                enemy.setX(1);
            }
            if (enemy.getX() > gameData.getDisplayWidth()) { // RIGHT BORDER
                enemy.setX(gameData.getDisplayWidth() - 1);
            }
            if (enemy.getY() < 0) { // TOP BORDER
                enemy.setY(1);
            }
            if (enemy.getY() > gameData.getDisplayHeight()) { // BOTTOM BORDER
                enemy.setY(gameData.getDisplayHeight() - 1);
            }
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
