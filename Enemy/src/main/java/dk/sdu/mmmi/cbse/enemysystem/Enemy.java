package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.enemy.CommonEnemy;
import dk.sdu.mmmi.cbse.common.services.IEntityCircleCollision;
import dk.sdu.mmmi.cbse.common.services.IEntityHealthPoints;

public class Enemy extends CommonEnemy implements IEntityCircleCollision, IEntityHealthPoints {

    private double boundingCircleRadius;
    private int healthPoints;

    public Enemy() {
    }

    @Override
    public double getBoundingCircleRadius() {
        return this.boundingCircleRadius;
    }

    public void setBoundingCircleRadius(double newBoundingCircleRadius) {
        this.boundingCircleRadius = newBoundingCircleRadius;
    }

    @Override
    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int newHealthPoints) {
        this.healthPoints = newHealthPoints;
    }

    @Override
    public void takeDamage(int damagePoints) {
        this.healthPoints -= damagePoints;

        if (this.healthPoints <= 0) {
            this.setDestroyed(true);
        }
    }
}
