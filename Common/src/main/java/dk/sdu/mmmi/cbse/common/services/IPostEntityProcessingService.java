package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * This interface is implemented by component entities that needs to process after all components using the IEntityProcessingService interface have been processed.
 * The processing method is called by the {@code process()} method.
 */
public interface IPostEntityProcessingService {

    /**
     * Called to process an entity after entities using the IEntityProcessingService have been processed.
     *
     * <p>Pre-conditions:</p>
     * <ul>
     *     <li>All components using the IEntityProcessingService interface has been processed</li>
     *     <li>{@code gamedata} isn't {@code null}</li>
     *     <li>{@code world} isn't {@code null}</li>
     * </ul>
     *
     * <p>Post-conditions:</p>
     * <ul>
     *     <li>The entity has been processed</li>
     * </ul>
     *
     * @param gameData Holds non-game related data such as key inputs. Must not be {@code null}
     * @param world    Mainly holds entities in the world to handle. Must not be {@code null}.
     */
    void process(GameData gameData, World world);
}
