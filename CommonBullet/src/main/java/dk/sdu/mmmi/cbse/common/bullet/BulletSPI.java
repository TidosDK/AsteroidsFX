package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 * Used to create a bullet entity.
 * Can be called as an SPI to initialize a bullet.
 */
public interface BulletSPI {

    /**
     * Creates a bullet entity.
     *
     * @param e        The entity to create a bullet from.
     * @param gameData Holds non-game related data such as key inputs. Must not be {@code null}
     * @return The new bullet entity.
     */
    Entity createBullet(Entity e, GameData gameData);
}
