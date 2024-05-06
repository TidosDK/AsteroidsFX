package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityCircleCollision;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class CollisionControlSystem implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity firstEntity : world.getEntities()) {
            for (Entity secondEntity : world.getEntities()) {
                if (firstEntity == secondEntity) continue;

                if (checkCircleCollision(firstEntity, secondEntity)) {
                    System.out.printf("Collision between %s and %s\n", firstEntity.getClass().getSimpleName(), secondEntity.getClass().getSimpleName());
                    // TODO: Handle collision
                }
            }
        }
    }

    private boolean checkCircleCollision(Entity firstEntity, Entity secondEntity) {
        if (!(firstEntity instanceof IEntityCircleCollision) && !(secondEntity instanceof IEntityCircleCollision)) {
            throw new IllegalStateException("One or more entities do not have a bounding circle radius");
        }

        double firstEntityCircleRadius = ((IEntityCircleCollision) firstEntity).getBoundingCircleRadius();
        double secondEntityCircleRadius = ((IEntityCircleCollision) secondEntity).getBoundingCircleRadius();

        double dx = firstEntity.getX() - secondEntity.getX();
        double dy = firstEntity.getY() - secondEntity.getY();

        double distance = Math.sqrt(dx * dx + dy * dy);

        return distance < (firstEntityCircleRadius + secondEntityCircleRadius);
    }
}
