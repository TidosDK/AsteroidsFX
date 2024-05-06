package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroid.CommonAsteroid;
import dk.sdu.mmmi.cbse.common.services.IEntityCircleCollision;

public class Asteroid extends CommonAsteroid implements IEntityCircleCollision {

    private float speed = 1;
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
}
