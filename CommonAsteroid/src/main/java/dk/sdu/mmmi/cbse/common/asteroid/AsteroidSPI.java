package dk.sdu.mmmi.cbse.common.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * This interface is implemented by Asteroid component implementations.
 * To split asteroids the {@code splitAsteroid()} method should be called.
 */
public interface AsteroidSPI {

    /**
     * Splits an Asteroid into 1 or more asteroids.
     *
     * <p>Pre-conditions:</p>
     * <ul>
     *     <li></li>
     *     <li>{@code targetEntity} is an instance of an Asteroid and isn't {@code null}</li>
     *     <li>{@code world} isn't {@code null}</li>
     * </ul>
     *
     * <p>Post-conditions:</p>
     * <ul>
     *     <li>The asteroid has been split into 1 or more new asteroids and has been added to the world.</li>
     * </ul>
     *
     * @param targetEntity
     * @param world
     */
    void splitAsteroid(Entity targetEntity, World world);
}
