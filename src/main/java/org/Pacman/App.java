package org.Pacman;
/**
 * @author Mohamed Sayed (maze7.56r9@gmail.com)
 */
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import org.Pacman.components.PlayerComponent;


import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static org.Pacman.Pacman.*;


public class App extends GameApplication {
    public static final int BLOCK_SIZE = 40;
    public static final int MAP_SIZE = 21;
    public static final int UI_SIZE = 80;
    public static final int TIME_PER_LEVEL = 100;

    public Entity getPlayer(){return getGameWorld().getSingleton(PLAYER);}
    public PlayerComponent getPlayerComponent(){return getPlayer().getComponent(PlayerComponent.class);}


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
    protected void initInput(){
        onKey(KeyCode.W,()-> getPlayerComponent().up());
        onKey(KeyCode.S,()-> getPlayerComponent().down());
        onKey(KeyCode.A,()-> getPlayerComponent().left());
        onKey(KeyCode.D,()-> getPlayerComponent().right());

        onKeyDown(KeyCode.F,()->{
            if (geti("teleport") > 0){
                inc("teleport",-1);
                getPlayerComponent().teleport();
            }
        });
    }

    @Override
    protected void initGameVars(Map<String,Object> vars){
        vars.put("score",0);
        vars.put("coins",0);
        vars.put("teleport",1);
        vars.put("time",TIME_PER_LEVEL);
    }

//    @Override

}
