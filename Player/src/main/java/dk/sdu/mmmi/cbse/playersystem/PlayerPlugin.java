package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.player.CommonPlayer;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class PlayerPlugin implements IGamePluginService {

    private Entity player;

    public PlayerPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

        // Add entities to the world
        this.player = createPlayerShip(gameData);
        world.addEntity(this.player);
    }

    private Entity createPlayerShip(GameData gameData) {

        Entity playerShip = new CommonPlayer();
        playerShip.setPolygonCoordinates(-5, -5, 10, 0, -5, 5);
        playerShip.setX(gameData.getDisplayHeight() / 2);
        playerShip.setY(gameData.getDisplayWidth() / 2);
        playerShip.setPlayer(true);
        return playerShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(this.player);
    }

}
