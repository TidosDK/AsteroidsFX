package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 * This interface allows to get the shooter of a Bullet.
 * To get the shooter the {@code getShooter()} method should be called.
 */
public interface IBulletShooter {

    /**
     * Called to get the shooter of a bullet.
     *
     * <p>Pre-conditions:</p>
     * <ul>
     *     <li>The bullet has a shooter</li>
     *     <li>{@code world} isn't {@code null}</li>
     * </ul>
     *
     * <p>Post-conditions:</p>
     * <ul>
     *     <li>The entity has been processed in the world</li>
     * </ul>
     *
     * @return The shooter of the bullet.
     */
    Entity getShooter();
}
