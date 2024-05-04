package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * This interface is implemented by components that need to process entities.
 * The processing method is called by the {@code process()} method.
 */
public interface IEntityProcessingService {

    /**
     * Called to process an entity.
     *
     * <p>Pre-conditions:</p>
     * <ul>
     *     <li>{@code gamedata} isn't {@code null}</li>
     *     <li>{@code world} isn't {@code null}</li>
     * </ul>
     *
     * <p>Post-conditions:</p>
     * <ul>
     *     <li>The entity has been processed in the world</li>
     * </ul>
     *
     * @param gameData Holds non-game related data such as key inputs. Must not be {@code null}
     * @param world    Mainly holds entities in the world to handle. Must not be {@code null}.
     */
    void process(GameData gameData, World world);
}
