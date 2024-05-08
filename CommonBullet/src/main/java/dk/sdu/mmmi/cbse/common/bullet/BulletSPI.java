package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 * This interface is implemented by bullet controller component implementations.
 * Can be called as an SPI to initialize a bullet by calling the {@code createBullet()} method.
 */
public interface BulletSPI {

    /**
     * Creates a bullet entity.
     *
     * <p>Pre-conditions:</p>
     * <ul>
     *     <li>{@code shooter} is an instance of an Entity and isn't {@code null}</li>
     *     <li>{@code world} isn't {@code null}</li>
     * </ul>
     *
     * <p>Post-conditions:</p>
     * <ul>
     *     <li>The bullet has been created and returned</li>
     * </ul>
     *
     * @param shooter  The entity to create a bullet from.
     * @param gameData Holds non-game related data such as key inputs. Must not be {@code null}
     * @return The new bullet entity.
     */
    Entity createBullet(Entity shooter, GameData gameData);
}
