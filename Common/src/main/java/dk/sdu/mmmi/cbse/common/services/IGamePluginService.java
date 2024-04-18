package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * This interface is implemented by game components that need a creation and deletion method.
 * The creation method is indicated by the {@code start()} method,
 * while the deletion method is indicated by the {@code stop()} method.
 */
public interface IGamePluginService {

    /**
     * Called when a new object of the class is initialized.
     * Should be used for constructing the component.
     *
     * <p>Pre-conditions:</p>
     * <ul>
     *     <li>Initialisation of the class has been called</li>
     *     <li>{@code gameData} isn't {@code null}</li>
     *     <li>{@code world} isn't {@code null}</li>
     * </ul>
     *
     * <p>Post-conditions:</p>
     * <ul>
     *     <li>The component has been initialized and added to the {@code world} object</li>
     * </ul>
     *
     * @param gameData Holds non-game data (e.g. key inputs). Must not be {@code null}.
     * @param world Holds game related data such as Entities in the world. Must not be {@code null}.
     */
    void start(GameData gameData, World world);

    /**
     * Called when an existing object is marked for deletion.
     *
     * <p>Pre-conditions:</p>
     * <ul>
     *     <li>Deletion of the object has been called</li>
     *     <li>{@code gameData} isn't {@code null}</li>
     *     <li>{@code world} isn't {@code null}</li>
     * </ul>
     *
     * <p>Post-conditions:</p>
     * <ul>
     *     <li>The component has been removed from the {@code world} object</li>
     * </ul>
     *
     * @param gameData Holds non-game data (e.g. key inputs). Must not be {@code null}.
     * @param world Holds game related data such as Entities in the world. Must not be {@code null}.
     */
    void stop(GameData gameData, World world);
}
