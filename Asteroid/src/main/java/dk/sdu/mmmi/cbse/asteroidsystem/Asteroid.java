package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroid.CommonAsteroid;
import dk.sdu.mmmi.cbse.common.services.IEntityCircleCollision;
import dk.sdu.mmmi.cbse.common.services.IEntityHealthPoints;

public class Asteroid extends CommonAsteroid implements IEntityCircleCollision, IEntityHealthPoints {

    private float speed = 1;
    private int healthPoints;
    private double boundingCircleRadius;

    public Asteroid() {
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public double getBoundingCircleRadius() {
        return boundingCircleRadius;
    }

    public void setBoundingCircleRadius(double newBoundingCircleRadius) {
        this.boundingCircleRadius = newBoundingCircleRadius;
    }

    @Override
    public int getHealthPoints() {
        return this.healthPoints;
    }

    public void setHealthPoints(int newHealthPoints) {
        this.healthPoints = newHealthPoints;
    }

    @Override
    public void takeDamage(int damagePoints) {
        this.healthPoints -= damagePoints;
    }
}
