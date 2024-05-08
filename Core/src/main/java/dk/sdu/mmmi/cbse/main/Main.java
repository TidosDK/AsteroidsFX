package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
    private Pane gameWindow;
    private Text fpsCountText;

//    private Text tpsCountText;
//    private final int TPSLIMIT = 2;


    public static void main(String[] args) {
        launch(Main.class);
    }

    @Override
    public void start(Stage window) throws Exception {
        Text text = new Text(10, 20, "Destroyed asteroids: ");
        fpsCountText = new Text(10, 40, "FPS: ");
//        tpsCountText = new Text(10, 60, "TPS: ");
        gameWindow = new Pane();
        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gameWindow.getChildren().add(text);
        gameWindow.getChildren().add(fpsCountText);
//        gameWindow.getChildren().add(tpsCountText);

        Scene scene = new Scene(gameWindow);
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                gameData.getKeys().setKey(GameKeys.LEFT, true);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, true);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                gameData.getKeys().setKey(GameKeys.UP, true);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, true);
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                gameData.getKeys().setKey(GameKeys.LEFT, false);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, false);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                gameData.getKeys().setKey(GameKeys.UP, false);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, false);
            }
        });

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getPluginServices()) {
            iGamePlugin.start(gameData, world);
        }
        for (Entity entity : world.getEntities()) {
            Polygon polygon = new Polygon(entity.getPolygonCoordinates());
            polygons.put(entity, polygon);
            gameWindow.getChildren().add(polygon);
        }

        render();

        window.setScene(scene);
        window.setTitle("ASTEROIDS");
        window.show();

    }

    private void render() {
        new AnimationTimer() {
            private long deltaThen = System.nanoTime();
            private long deltaNow;
            private int deltaNowSeconds;
            private int fpsCount;
//            private int tpsCount;

            @Override
            public void handle(long now) {
                deltaNow = (System.nanoTime() - deltaThen);
                deltaThen = System.nanoTime();

                gameData.setDelta(deltaNow);
                deltaNowSeconds += deltaNow;

                fpsCount++;

                update(deltaNow);
                draw();
                gameData.getKeys().update();

                if (deltaNowSeconds * 0.000000001 > 1f) {
                    fpsCountText.setText("FPS: " + fpsCount);
                    fpsCount = 0;
//                    tpsCountText.setText("TPS: " + tpsCount);
//                    tpsCount = 0;
                    deltaNowSeconds -= 1000000000;
                }

                // Below is code for handling TPS (Ticks Per Second).
                // With the code, it is possible to limit the amount of allowed ticks in a second.
//                if (gameData.getDeltaStacking() * 0.000000001 < (1f / TPSLIMIT)) {
//                    return;
//                }
//
//                tpsCount++;
//                gameData.setDeltaStacking(gameData.getDeltaStacking() - (long) (1f / TPSLIMIT * 1000000000));
            }

        }.start();
    }

    private void update(long deltaNow) {
        for (Entity entity : world.getEntities()) {
            if (entity.isDestroyed()) {
                gameWindow.getChildren().remove(polygons.get(entity));
                polygons.remove(entity);
                world.removeEntity(entity);
            }
        }

        // Update
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }

        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }

        for (Entity entity : world.getEntities()) {
            if (polygons.get(entity) == null) {
                Polygon polygon = new Polygon(entity.getPolygonCoordinates());
                polygons.put(entity, polygon);
                gameWindow.getChildren().add(polygon);
            }
        }
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {
            Polygon polygon = polygons.get(entity);
            polygon.setTranslateX(entity.getX());
            polygon.setTranslateY(entity.getY());
            polygon.setRotate(entity.getRotation());
        }
    }

    private Collection<? extends IGamePluginService> getPluginServices() {
        return ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return ServiceLoader.load(IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return ServiceLoader.load(IPostEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
