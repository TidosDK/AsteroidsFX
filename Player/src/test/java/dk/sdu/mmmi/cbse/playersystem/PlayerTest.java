package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerTest {

    private Player underlyingPlayer;
    private Player mockPlayer;
    private PlayerControlSystem playerControlSystem;
    private GameData gameData;
    private GameKeys gameKeys;
    private World world;

    @BeforeEach
    void setup() {
        this.mockPlayer = mock(Player.class);
        this.gameData = mock(GameData.class);
        this.gameKeys = mock(GameKeys.class);
        this.world = mock(World.class);

        when(gameData.getDisplayWidth()).thenReturn(500);
        when(gameData.getDisplayHeight()).thenReturn(500);

        this.underlyingPlayer = new Player();
        this.underlyingPlayer.setX((double) gameData.getDisplayHeight() / 2);
        this.underlyingPlayer.setY((double) gameData.getDisplayWidth() / 2);

        List<Entity> players = new ArrayList<>();
        players.add(underlyingPlayer);
        when(world.getEntities(Player.class)).thenReturn(players);

        when(gameData.getDeltaSeconds()).thenReturn(0.016);

        when(mockPlayer.getX()).thenReturn(underlyingPlayer.getX());
        when(mockPlayer.getY()).thenReturn(underlyingPlayer.getY());
        when(mockPlayer.getRotation()).thenReturn(underlyingPlayer.getRotation());

        // Sets the game data and the game keys.
        when(gameData.getKeys()).thenReturn(gameKeys);
        when(gameData.getKeys().isDown(GameKeys.UP)).thenReturn(false);
        when(gameData.getKeys().isDown(GameKeys.RIGHT)).thenReturn(false);
        when(gameData.getKeys().isDown(GameKeys.LEFT)).thenReturn(false);

        // Defines a Player Control System.
        this.playerControlSystem = new PlayerControlSystem();
    }

    @Test()
    void testPlayerMovesForward() {
        when(gameData.getKeys().isDown(GameKeys.UP)).thenReturn(true);

        double oldX = mockPlayer.getX();
        this.playerControlSystem.process(gameData, world);
        when(mockPlayer.getX()).thenReturn(underlyingPlayer.getX());

        assertTrue(mockPlayer.getX() > oldX);
    }

    @Test()
    void testPlayerRotatesRight() {
        when(gameData.getKeys().isDown(GameKeys.RIGHT)).thenReturn(true);

        double oldRotation = mockPlayer.getRotation();
        this.playerControlSystem.process(gameData, world);
        when(mockPlayer.getRotation()).thenReturn(underlyingPlayer.getRotation());

        assertTrue(mockPlayer.getRotation() > oldRotation);
    }

    @Test()
    void testPlayerRotatesLeft() {
        when(gameData.getKeys().isDown(GameKeys.LEFT)).thenReturn(true);

        double oldRotation = mockPlayer.getRotation();
        this.playerControlSystem.process(gameData, world);
        when(mockPlayer.getRotation()).thenReturn(underlyingPlayer.getRotation());

        assertTrue(mockPlayer.getRotation() < oldRotation);
    }

    @Test()
    void testPlayerTakeDamage() {
        underlyingPlayer.setHealthPoints(5);
        int oldHealthPoints = underlyingPlayer.getHealthPoints();

        underlyingPlayer.takeDamage(1);

        assertEquals(underlyingPlayer.getHealthPoints(), oldHealthPoints - 1);
    }

    @Test()
    void testPlayerIsDestroyed() {
        underlyingPlayer.setHealthPoints(10);
        underlyingPlayer.takeDamage(underlyingPlayer.getHealthPoints());

        assertTrue(underlyingPlayer.isDestroyed());
    }
}
