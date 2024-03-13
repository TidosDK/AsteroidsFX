package dk.sdu.mmmi.cbse.common.player;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 * @author tidosdk
 */
public interface PlayerSPI {
    Entity createPlayer(Entity e, GameData gamedata);
}
