package org.Pacman.components.AI;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import org.Pacman.Pacman;


/**
 * @author Mohamed Sayed (maze7.56r9@gmail.com)
 */

@Required(AStarMoveComponent.class)
public class ChasePlayer extends Component {
    private  AStarMoveComponent astar;
    private boolean isDelayed = false;

    private void move() {
        var player = FXGL.getGameWorld().getSingleton(Pacman.PLAYER);
        int x = player.call("getCellX");
        int y = player.call("getCellY");
        astar.moveToCell(x, y);
    }

        @Override
    public void onUpdate(double tpf){
        if(!isDelayed){
            move();
        } else {
            if (astar.isAtDestination()) {
                move();

            }
        }
    }

    public ChasePlayer withDelay(){
        isDelayed = true;
        return this;
    }

}
