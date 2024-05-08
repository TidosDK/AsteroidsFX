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
     * Called when a component is initialized.
     * Can be used for constructing the component.
     *
     * <p>Pre-conditions:</p>
     * <ul>
     *     <li>The component has not been initialized.</li>
     *     <li>The {@code start()} method must not have been called before, unless the {@code stop()} method has been called.</li>
     *     <li>{@code gamedata} isn't {@code null}</li>
     *     <li>{@code world} isn't {@code null}</li>
     * </ul>
     *
     * <p>Post-conditions:</p>
     * <ul>
     *     <li>The component has been initialized in the {@code world}.</li>
     * </ul>
     *
     * @param gameData Holds non-game related data such as key inputs. Must not be {@code null}
     * @param world    Mainly holds entities in the world to handle. Must not be {@code null}.
     */
    void start(GameData gameData, World world);

    /**
     * Called when an existing object is marked for deletion.
     *
     * <p>Pre-conditions:</p>
     * <ul>
     *     <li>The component has been called to delete</li>
     *     <li>The {@code stop()} method must not have been called before, unless the {@code start()} method has been called.</li>
     *     <li>{@code gameData} isn't {@code null}</li>
     *     <li>{@code world} isn't {@code null}</li>
     * </ul>
     *
     * <p>Post-conditions:</p>
     * <ul>
     *     <li>The component has been removed from the {@code world}.</li>
     * </ul>
     *
     * @param gameData Holds non-game related data such as key inputs. Must not be {@code null}
     * @param world    Mainly holds entities in the world to handle. Must not be {@code null}.
     */
    void stop(GameData gameData, World world);
}
