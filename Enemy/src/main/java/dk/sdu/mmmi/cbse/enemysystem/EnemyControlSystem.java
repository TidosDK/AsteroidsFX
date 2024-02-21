package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;


public class EnemyControlSystem implements IEntityProcessingService {

    static float rotationSpeed = 2;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            // When the LEFT arrow button is pressed, rotates to the left.

            int rotationRandomNumber = (int) Math.round(Math.random() * 3);
            int moveRandomNumber = (int) Math.round(Math.random() * 1);
            int shootRandomNumber = (int) Math.round(Math.random() * 500);

            if (rotationRandomNumber == 1) {
                enemy.setRotation(enemy.getRotation() - rotationSpeed);
            } else if (rotationRandomNumber == 2) {
                enemy.setRotation(enemy.getRotation() + rotationSpeed);
            }

            if (moveRandomNumber == 1) {
                double changeX = Math.cos(Math.toRadians(enemy.getRotation()));
                double changeY = Math.sin(Math.toRadians(enemy.getRotation()));
                enemy.setX(enemy.getX() + changeX);
                enemy.setY(enemy.getY() + changeY);
            }

            if (shootRandomNumber == 1) {
                for (BulletSPI bulletSPI : getBulletSPIs()) {
                    world.addEntity(bulletSPI.createBullet(enemy, gameData));
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
