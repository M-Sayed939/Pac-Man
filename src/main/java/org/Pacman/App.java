package org.Pacman;
/*
 * @author Mohamed Sayed (maze7.56r9@gmail.com)
 */

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.text.TextLevelLoader;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.ui.UI;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.Pacman.components.PlayerComponent;


import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static org.Pacman.Pacman.*;


public class App extends GameApplication {
    public static final int BLOCK_SIZE = 40;
    public static final int MAP_SIZE = 21;
    public static final int UI_SIZE = 80;
    public static final int TIME_PER_LEVEL = 100;
    private boolean requestNewGame = false;

    public Entity getPlayer() {
        return getGameWorld().getSingleton(PLAYER);
    }

    public PlayerComponent getPlayerComponent() {
        return getPlayer().getComponent(PlayerComponent.class);
    }


    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(MAP_SIZE * BLOCK_SIZE + UI_SIZE);
        gameSettings.setHeight(MAP_SIZE * BLOCK_SIZE);
        gameSettings.setTitle("PAC-MAN");
        gameSettings.setVersion("1.0");
        gameSettings.setManualResizeEnabled(true);
        gameSettings.setPreserveResizeRatio(true);
    }

    @Override
    protected void initInput() {
        onKey(KeyCode.W, () -> getPlayerComponent().up());
        onKey(KeyCode.S, () -> getPlayerComponent().down());
        onKey(KeyCode.A, () -> getPlayerComponent().left());
        onKey(KeyCode.D, () -> getPlayerComponent().right());

        onKeyDown(KeyCode.F, () -> {
            if (geti("teleport") > 0) {
                inc("teleport", -1);
                getPlayerComponent().teleport();
            }
        });
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
        vars.put("coins", 0);
        vars.put("teleport", 1);
        vars.put("time", TIME_PER_LEVEL);
    }

    public void onPlayerKilled() {
        requestNewGame = true;
    }

    @Override
    protected void initGame() {
        getGameScene().setBackgroundColor(Color.DARKSLATEGRAY);
        getGameWorld().addEntityFactory(new Factory());
        Level level = getAssetLoader().loadLevel("pacman_level0.txt", new TextLevelLoader(40, 40, ' '));
        getGameWorld().setLevel(level);
        AStarGrid grid = AStarGrid.fromWorld(getGameWorld(), MAP_SIZE, MAP_SIZE, BLOCK_SIZE, BLOCK_SIZE, (type) -> {
            if (type == BLOCK)
                return CellState.NOT_WALKABLE;
            return CellState.WALKABLE;
        });
        set("grid", grid);
        set("coins", getGameWorld().getEntitiesByType(COIN).size());
        run(() -> inc("time", -1), Duration.seconds(1));
        getWorldProperties().<Integer>addListener("time", (old, now) -> {
            if (now == 0) {
                onPlayerKilled();
            }
        });
    }

    private void gameOver() {
        getDialogService().showMessageBox("Game Over. Press OK to Exit", getGameController()::exit);
    }

    public void onCoinPickup() {
        inc("coins", -1);
        inc("score", +20);
        if (geti("score") % 2000 == 0) {
            inc("teleport", +1);
        }
        if (geti("coins") == 0) {
            gameOver();

        }
    }

    @Override
    protected void initPhysics() {
        onCollisionBegin(PLAYER, ENEMY, (p, e) -> onPlayerKilled());
        onCollisionCollectible(PLAYER, COIN, c -> onCoinPickup());
    }

    @Override
    protected void initUI() {
        UI ui = getAssetLoader().loadUI("pacman_ui.fxml", new PacmanUI());
        ui.getRoot().setTranslateX(MAP_SIZE * BLOCK_SIZE);
        getGameScene().addUI(ui);

    }

    @Override
    protected void onUpdate(double tpf) {
        if (requestNewGame) {
            requestNewGame = false;
            getGameController().startNewGame();

        }
    }

}
