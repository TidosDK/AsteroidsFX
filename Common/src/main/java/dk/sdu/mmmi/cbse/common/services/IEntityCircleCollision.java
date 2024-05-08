package dk.sdu.mmmi.cbse.common.services;

/**
 * Implemented by entities that want circle collision.
 * The radius of the collision boundary is retrieved by the {@code getBoundingCircleRadius()} method.
 */
public interface IEntityCircleCollision {

    /**
     * Retrieves the radius of the entity's circular collision boundary.
     *
     * <p>Pre-conditions:</p>
     * <ul>
     *     <li>The entity has defined a circular collision boundary attribute</li>
     * </ul>
     *
     * <p>Post-conditions:</p>
     * <ul>
     *     <li>The radius of the circular collision boundary has been retrieved</li>
     * </ul>
     *
     * @return The bounding circle radius as a double.
     */
    double getBoundingCircleRadius();
}
