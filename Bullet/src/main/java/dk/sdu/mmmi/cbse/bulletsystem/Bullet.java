package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.CommonBullet;
import dk.sdu.mmmi.cbse.common.services.IEntityCircleCollision;

public class Bullet extends CommonBullet implements IEntityCircleCollision {

    private double boundingCircleRadius;

    public Bullet() {
    }

    @Override
    public double getBoundingCircleRadius() {
        return this.boundingCircleRadius;
    }

    public void setBoundingCircleRadius(double newBoundingCircleRadius) {
        this.boundingCircleRadius = newBoundingCircleRadius;
    }
}
