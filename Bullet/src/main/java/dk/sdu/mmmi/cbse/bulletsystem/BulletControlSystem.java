package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    private static final double SPEED = 2.75;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            double changeX = Math.cos(Math.toRadians(bullet.getRotation())) * SPEED * (1 + gameData.getDeltaSeconds());
            double changeY = Math.sin(Math.toRadians(bullet.getRotation())) * SPEED * (1 + gameData.getDeltaSeconds());
            bullet.setX(bullet.getX() + changeX);
            bullet.setY(bullet.getY() + changeY);

            if (bullet.getX() < -20) { // LEFT BORDER
                bullet.setDestroyed(true);
            } else if (bullet.getX() > gameData.getDisplayWidth() + 20) { // RIGHT BORDER
                bullet.setDestroyed(true);
            } else if (bullet.getY() < -20) { // TOP BORDER
                bullet.setDestroyed(true);
            } else if (bullet.getY() > gameData.getDisplayHeight() + 20) { // BOTTOM BORDER
                bullet.setDestroyed(true);
            }
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Bullet bullet = new Bullet();

        bullet.setPolygonCoordinates(-3, -1, 3, -1, 3, 1, -3, 1);

        bullet.setX(shooter.getX());
        bullet.setY(shooter.getY());

        bullet.setRotation(shooter.getRotation());
        bullet.setBoundingCircleRadius(3);

        bullet.setShooter(shooter);

        return bullet;
    }

    private void setShape(Entity entity) {
    }

}
