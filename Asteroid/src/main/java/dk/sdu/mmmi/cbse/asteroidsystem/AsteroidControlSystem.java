package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Arrays;

public class AsteroidControlSystem implements IEntityProcessingService, AsteroidSPI {

    private static int asteroidSpawnAmount = 5;

    @Override
    public void process(GameData gameData, World world) {
        int asteroidCount = 0;
        for (Entity commonAsteroid : world.getEntities(Asteroid.class)) {
            // Passes CommonAsteroid into a Asteroid.
            Asteroid asteroid = (Asteroid) commonAsteroid;
            asteroidCount++;

            double changeX = Math.cos(Math.toRadians(asteroid.getRotation())) * asteroid.getSpeed() * (1 + gameData.getDeltaSeconds());
            double changeY = Math.sin(Math.toRadians(asteroid.getRotation())) * asteroid.getSpeed() * (1 + gameData.getDeltaSeconds());
            asteroid.setX(asteroid.getX() + changeX);
            asteroid.setY(asteroid.getY() + changeY);

            if (asteroid.getX() < 0) { // LEFT BORDER
                asteroid.setX(gameData.getDisplayWidth());
            }
            if (asteroid.getX() > gameData.getDisplayWidth()) { // RIGHT BORDER
                asteroid.setX(0);
            }
            if (asteroid.getY() < 0) { // TOP BORDER
                asteroid.setY(gameData.getDisplayHeight());
            }
            if (asteroid.getY() > gameData.getDisplayHeight()) { // BOTTOM BORDER
                asteroid.setY(0);
            }
        }


        if (asteroidCount == 0) {
            for (int i = 0; i < AsteroidControlSystem.asteroidSpawnAmount; i++) {
                world.addEntity(this.createAsteroid(gameData));
            }
        }
    }

    private Entity createAsteroid(GameData gameData) {
        Asteroid asteroid = new Asteroid();

        // Setting collision radius:
        asteroid.setBoundingCircleRadius((float) (30 + Math.random() * 10));

        // Shape:
        this.setAsteroidPolygon(asteroid);

        // Position:
        this.setAsteroidPosition(asteroid, gameData);

        // Setting speed:
        asteroid.setSpeed((float) (Math.random() * 1.25));

        // Sets health:
        asteroid.setHealthPoints(2);

        return asteroid;
    }

    private void setAsteroidPolygon(Asteroid asteroid) {
        int minEdges = 6;
        int maxEdges = 12;
        int numberOfEdges = minEdges + (int) (Math.random() * ((maxEdges - minEdges) + 1));
        double boundingCircleRadius = asteroid.getBoundingCircleRadius();
        double radiusOffset = boundingCircleRadius / 2;

        double[] coordinates = new double[numberOfEdges * 2];

        for (int i = 0; i < numberOfEdges; i++) {
            double radius = (boundingCircleRadius - radiusOffset) + (Math.random() * ((boundingCircleRadius + radiusOffset) - (boundingCircleRadius - radiusOffset)));
            double theta = 2.0 * Math.PI * i / numberOfEdges;
            coordinates[i * 2] = radius * Math.cos(theta);
            coordinates[i * 2 + 1] = radius * Math.sin(theta);
        }

        asteroid.setPolygonCoordinates(coordinates);
    }

    private void setAsteroidPosition(Asteroid asteroid, GameData gameData) {
        boolean verticalBorder = Math.random() < 0.5;
        if (verticalBorder) {
            asteroid.setX(Math.random() < 0.5 ? 0 : gameData.getDisplayWidth());
            asteroid.setY(Math.random() * gameData.getDisplayHeight());
            asteroid.setRotation(Math.random() * 180);
        } else {
            asteroid.setX(Math.random() * gameData.getDisplayWidth());
            asteroid.setY(Math.random() < 0.5 ? 0 : gameData.getDisplayHeight());
            asteroid.setRotation(Math.random() * -180);
        }
    }

    @Override
    public void splitAsteroid(Entity targetEntity, World world) {
        ((Asteroid) targetEntity).takeDamage(1);

        if (!checkHealthy((Asteroid) targetEntity)) {
            return;
        }

        int asteroidsSplit = 2;
        Asteroid[] asteroids = new Asteroid[asteroidsSplit];

        for (int i = 0; i < asteroidsSplit; i++) {
            Asteroid asteroid = new Asteroid();

            asteroid.setBoundingCircleRadius(((Asteroid) targetEntity).getBoundingCircleRadius() / 2);

            this.setAsteroidPolygon(asteroid);
            // TODO: Der smides en error når polygons bliver tegnet. No idea why.

            asteroid.setX(targetEntity.getX());
            asteroid.setY(targetEntity.getY());
            if (i == 0) {
                asteroid.setRotation(Math.random() * 90);
            } else {
                asteroid.setRotation(asteroids[i - 1].getRotation() + 20 + Math.random() * 50);
            }

            asteroid.setHealthPoints(((Asteroid) targetEntity).getHealthPoints());

            asteroids[i] = asteroid;
        }

        for (Asteroid asteroid : asteroids) {
            world.addEntity(asteroid);
        }
    }

    private boolean checkHealthy(Asteroid asteroid) {
        return asteroid.getHealthPoints() > 0;
    }
}
