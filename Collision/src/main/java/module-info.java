import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.collisionsystem.CollisionControlSystem;
import dk.sdu.mmmi.cbse.common.services.IEntityCircleCollision;

module Collision {
    exports dk.sdu.mmmi.cbse.collisionsystem;
    requires Common;
    uses IEntityCircleCollision;
    provides IPostEntityProcessingService with CollisionControlSystem;
}