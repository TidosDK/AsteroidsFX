package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.player.CommonPlayer;
import dk.sdu.mmmi.cbse.common.services.IEntityCircleCollision;
import dk.sdu.mmmi.cbse.common.services.IEntityHealthPoints;

public class Player extends CommonPlayer implements IEntityCircleCollision, IEntityHealthPoints {

    private double boundingCircleRadius;
    private int healthPoints;

    public Player() {
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
