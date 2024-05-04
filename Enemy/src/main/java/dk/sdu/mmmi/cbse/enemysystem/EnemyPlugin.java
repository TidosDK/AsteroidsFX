package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.enemy.CommonEnemy;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;

    public EnemyPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

        // Add entities to the world
        this.enemy = createEnemyShip(gameData);
        world.addEntity(this.enemy);
    }

    private Entity createEnemyShip(GameData gameData) {

        Entity enemyShip = new CommonEnemy();
        enemyShip.setPolygonCoordinates(-5, -5, 10, 0, -5, 5);
        enemyShip.setX(gameData.getDisplayHeight() / 2);
        enemyShip.setY(gameData.getDisplayWidth() / 2);
        return enemyShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(this.enemy);
    }

}
