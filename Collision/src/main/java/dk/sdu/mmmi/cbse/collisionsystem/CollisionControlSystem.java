package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.asteroid.CommonAsteroid;
import dk.sdu.mmmi.cbse.common.bullet.CommonBullet;
import dk.sdu.mmmi.cbse.common.bullet.IBulletShooter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.enemy.CommonEnemy;
import dk.sdu.mmmi.cbse.common.player.CommonPlayer;
import dk.sdu.mmmi.cbse.common.services.IEntityCircleCollision;
import dk.sdu.mmmi.cbse.common.services.IEntityHealthPoints;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class CollisionControlSystem implements IPostEntityProcessingService {

    private HttpClient httpClient = HttpClient.newHttpClient();

    @Override
    public void process(GameData gameData, World world) {
        Set<String> checkedEntityPairsHashCode = new HashSet<>();

        for (Entity firstEntity : world.getEntities()) {
            for (Entity secondEntity : world.getEntities()) {
                // Assures
                if (firstEntity == secondEntity) continue;

                if (checkedEntityPairsHashCode.contains(secondEntity.hashCode() + ":" + firstEntity.hashCode()))
                    continue;

                if (checkCircleCollision(firstEntity, secondEntity)) {
                    handleCollision(firstEntity, secondEntity, world);
                }

                checkedEntityPairsHashCode.add(firstEntity.hashCode() + ":" + secondEntity.hashCode());
            }
        }
    }

    private void handleCollision(Entity firstEntity, Entity secondEntity, World world) {

        // Player and Asteroid collision
        if ((firstEntity instanceof CommonPlayer && secondEntity instanceof CommonAsteroid) || (firstEntity instanceof CommonAsteroid && secondEntity instanceof CommonPlayer)) {
            if (firstEntity instanceof CommonPlayer) {
                firstEntity.setDestroyed(true);
            } else {
                secondEntity.setDestroyed(true);
            }
        }


        // Player and Enemy collision
        else if ((firstEntity instanceof CommonPlayer && secondEntity instanceof CommonEnemy) || (firstEntity instanceof CommonEnemy && secondEntity instanceof CommonPlayer)) {
            firstEntity.setDestroyed(true);
            secondEntity.setDestroyed(true);
        }


        // Player and Bullet collision
        else if ((firstEntity instanceof CommonPlayer && secondEntity instanceof CommonBullet) || (firstEntity instanceof CommonBullet && secondEntity instanceof CommonPlayer)) {
            if (firstEntity instanceof CommonBullet) {
                if (((IBulletShooter) firstEntity).getShooter() != secondEntity) {
                    ((IEntityHealthPoints) secondEntity).takeDamage(1);
                    firstEntity.setDestroyed(true);
                }
            } else {
                if (((IBulletShooter) secondEntity).getShooter() != firstEntity) {
                    ((IEntityHealthPoints) firstEntity).takeDamage(1);
                    secondEntity.setDestroyed(true);
                }
            }
        }


        // Enemy and Asteroid collision
        else if ((firstEntity instanceof CommonEnemy && secondEntity instanceof CommonAsteroid) || (firstEntity instanceof CommonAsteroid && secondEntity instanceof CommonEnemy)) {
            if (firstEntity instanceof CommonEnemy) {
                firstEntity.setDestroyed(true);
            } else {
                secondEntity.setDestroyed(true);
            }
        }


        // Enemy and Bullet collision
        else if ((firstEntity instanceof CommonEnemy && secondEntity instanceof CommonBullet) || (firstEntity instanceof CommonBullet && secondEntity instanceof CommonEnemy)) {
            if (firstEntity instanceof CommonBullet) {
                if (((IBulletShooter) firstEntity).getShooter() != secondEntity) {
                    ((IEntityHealthPoints) secondEntity).takeDamage(1);
                    firstEntity.setDestroyed(true);
                }
            } else {
                if (((IBulletShooter) secondEntity).getShooter() != firstEntity) {
                    ((IEntityHealthPoints) firstEntity).takeDamage(1);
                    secondEntity.setDestroyed(true);
                }
            }
        }


        // Asteroid and Bullet collision
        else if ((firstEntity instanceof CommonAsteroid && secondEntity instanceof CommonBullet) || (firstEntity instanceof CommonBullet && secondEntity instanceof CommonAsteroid)) {
            if (firstEntity instanceof CommonBullet) {
                this.getAsteroidSPI().stream().findFirst().ifPresent(spi -> spi.splitAsteroid(secondEntity, world));
                firstEntity.setDestroyed(true);
                secondEntity.setDestroyed(true);

                if (((IBulletShooter) firstEntity).getShooter() instanceof CommonPlayer) {
                    this.addToScore(1);
                }
            } else {
                this.getAsteroidSPI().stream().findFirst().ifPresent(spi -> spi.splitAsteroid(firstEntity, world));
                secondEntity.setDestroyed(true);
                firstEntity.setDestroyed(true);
                if (((IBulletShooter) secondEntity).getShooter() instanceof CommonPlayer) {
                    this.addToScore(1);
                }
            }
        }
    }

    private void addToScore(int points) {
        HttpRequest requestAddToScore = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/scoresystem/score/add/" + points))
                .PUT(HttpRequest.BodyPublishers.ofString(""))
                .build();

        try {
            httpClient.send(requestAddToScore, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException ignored) {
        }
    }

    private boolean checkCircleCollision(Entity firstEntity, Entity secondEntity) {
        if (!(firstEntity instanceof IEntityCircleCollision) && !(secondEntity instanceof IEntityCircleCollision)) {
            return false;
        }

        double firstEntityCircleRadius = ((IEntityCircleCollision) firstEntity).getBoundingCircleRadius();
        double secondEntityCircleRadius = ((IEntityCircleCollision) secondEntity).getBoundingCircleRadius();

        double dx = firstEntity.getX() - secondEntity.getX();
        double dy = firstEntity.getY() - secondEntity.getY();

        double distance = Math.sqrt(dx * dx + dy * dy);

        return distance < (firstEntityCircleRadius + secondEntityCircleRadius);
    }

    private Collection<? extends AsteroidSPI> getAsteroidSPI() {
        return ServiceLoader.load(AsteroidSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
