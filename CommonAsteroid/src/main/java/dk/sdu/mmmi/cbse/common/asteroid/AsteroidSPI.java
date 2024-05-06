package dk.sdu.mmmi.cbse.common.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

public interface AsteroidSPI {

    /**
     *
     * @param entity
     * @param world
     */
    void splitAsteroid(Entity entity, World world);
}
