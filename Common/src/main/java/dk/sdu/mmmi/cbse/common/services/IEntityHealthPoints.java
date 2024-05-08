package dk.sdu.mmmi.cbse.common.services;

/**
 * This interface is implemented by entity components which should have health points.
 * The health points should be retrived by {@code getHealthPoints()} method,
 * and the reduction of health points should occur by calling the {@code takeDamage()} method.
 */
public interface IEntityHealthPoints {

    /**
     * Called to get the Entities health points.
     *
     * <p>Pre-conditions:</p>
     * <ul>
     *     <li>The entity has some kind of {@code HealthPoint} variable.</li>
     * </ul>
     *
     * <p>Post-conditions:</p>
     * <ul>
     *     <li>The entity's health has been retrieved.</li>
     * </ul>
     *
     * @return The current health points of the entity.
     */
    int getHealthPoints();

    /**
     * Called to reduce the Entities health points.
     *
     * <p>Pre-conditions:</p>
     * <ul>
     *     <li>The entity has some kind of {@code HealthPoint} variable.</li>
     *     <li>The entity can have any amount of health points.</li>
     * </ul>
     *
     * <p>Post-conditions:</p>
     * <ul>
     *     <li>The entity's health has been reduced by {@code damagePoints} amount.</li>
     * </ul>
     *
     * @param damagePoints
     */
    void takeDamage(int damagePoints);
}
