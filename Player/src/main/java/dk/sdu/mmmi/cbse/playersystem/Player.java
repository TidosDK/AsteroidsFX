package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.player.CommonPlayer;
import dk.sdu.mmmi.cbse.common.services.IEntityCircleCollision;

public class Player extends CommonPlayer implements IEntityCircleCollision {

    private double boundingCircleRadius;

    public Player() {
    }

    @Override
    public double getBoundingCircleRadius() {
        return this.boundingCircleRadius;
    }

    public void setBoundingCircleRadius(double newBoundingCircleRadius) {
        this.boundingCircleRadius = newBoundingCircleRadius;
    }
}
