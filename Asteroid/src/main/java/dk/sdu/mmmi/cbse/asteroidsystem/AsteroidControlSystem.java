package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

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

        // Shape:
        int minEdges = 6;
        int maxEdges = 12;
        int numberOfEdges = minEdges + (int) (Math.random() * ((maxEdges - minEdges) + 1));
        double minRadius = 15;
        double maxRadius = 30;

        double[] coordinates = new double[numberOfEdges * 2];
        for (int i = 0; i < numberOfEdges; i++) {
            double radius = minRadius + (Math.random() * (maxRadius - minRadius)); // random radius
            double theta = 2.0 * Math.PI * i / numberOfEdges;
            coordinates[i * 2] = radius * Math.cos(theta);
            coordinates[i * 2 + 1] = radius * Math.sin(theta);
        }

        asteroid.setPolygonCoordinates(coordinates);


        // Position:
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

        // Setting speed:
        asteroid.setSpeed((float) (Math.random() * 2));

        // Setting collision radius:
        asteroid.setBoundingCircleRadius(maxRadius);

        return asteroid;
    }

    @Override
    public void splitAsteroid(Entity entity, World world) {
        throw new UnsupportedOperationException();
    }

    // TODO: Collision


    public static int getAsteroidSpawnAmount() {
        return asteroidSpawnAmount;
    }

    public static void setAsteroidSpawnAmount(int asteroidSpawnAmount) {
        AsteroidControlSystem.asteroidSpawnAmount = asteroidSpawnAmount;
    }


}
