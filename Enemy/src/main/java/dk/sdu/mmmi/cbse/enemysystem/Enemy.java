package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.enemy.CommonEnemy;
import dk.sdu.mmmi.cbse.common.services.IEntityCircleCollision;

public class Enemy extends CommonEnemy implements IEntityCircleCollision {

    private double boundingCircleRadius;

    public Enemy() {
    }

    @Override
    public double getBoundingCircleRadius() {
        return this.boundingCircleRadius;
    }

    public void setBoundingCircleRadius(double newBoundingCircleRadius) {
        this.boundingCircleRadius = newBoundingCircleRadius;
    }
}
