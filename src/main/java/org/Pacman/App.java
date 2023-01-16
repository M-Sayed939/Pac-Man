package org.Pacman;
/**
 * @author Mohamed Sayed (maze7.56r9@gmail.com)
 */
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;


import static com.almasb.fxgl.dsl.FXGL.*;
import static org.Pacman.Pacman.*;


public class App extends GameApplication {
    public static final int BLOCK_SIZE = 40;
    public static final int MAP_SIZE = 21;
    public static final int UI_SIZE = 80;
    public static final int TIME_PER_LEVEL = 100;

    public Entity getPlayer(){return getGameWorld().getSingleton(PLAYER);}


    @Override
    protected void initSettings(GameSettings gameSettings) {

    }
}
