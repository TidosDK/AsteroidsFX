import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.collisionsystem.CollisionControlSystem;
import dk.sdu.mmmi.cbse.common.services.IEntityCircleCollision;

module Collision {
    exports dk.sdu.mmmi.cbse.collisionsystem;
    requires Common;
    requires CommonAsteroid;
    requires CommonBullet;
    requires CommonEnemy;
    requires CommonPlayer;
    requires java.net.http;
    uses IEntityCircleCollision;
    uses dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;
    provides IPostEntityProcessingService with CollisionControlSystem;
}