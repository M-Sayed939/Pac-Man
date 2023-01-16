package org.Pacman.components;
/**
 * @author Mohamed Sayed (maze7.56r9@gmail.com)
 */

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;

import static org.Pacman.components.MoveDirections.*;


@Required(AStarMoveComponent.class)
public class PlayerComponent extends Component {

    private CellMoveComponent moveComponent;
    private AStarMoveComponent astar;

    private MoveDirections crrMxDir = RIGHT;
    private MoveDirections nxtMxDir = RIGHT;

    public void up() {
        nxtMxDir = UP;
    }

    public void down() {
        nxtMxDir = DOWN;
    }

    public void left() {
        nxtMxDir = LEFT;
    }

    public void right() {
        nxtMxDir = RIGHT;
    }

    @Override
    public void onUpdate(double tpf) {
        var x = moveComponent.getCellX();
        var y = moveComponent.getCellY();

        if (x == 0 && crrMxDir == LEFT) {
            astar.stopMovementAt(astar.getGrid().getWidth() - 1, moveComponent.getCellY());
            return;

        } else if (x == astar.getGrid().getWidth() - 1 && crrMxDir == RIGHT) {
            astar.stopMovementAt(0, moveComponent.getCellY());
            return;
        }

        if (astar.isMoving())
            return;

        switch (nxtMxDir) {
            case UP -> {
                if (astar.getGrid().getUp(x, y).filter(c -> c.getState().isWalkable()).isPresent())
                    crrMxDir = nxtMxDir;
            }
            case RIGHT -> {
                if (astar.getGrid().getRight(x, y).filter(c -> c.getState().isWalkable()).isPresent())
                    crrMxDir = nxtMxDir;
            }
            case DOWN -> {
                if (astar.getGrid().getDown(x, y).filter(c -> c.getState().isWalkable()).isPresent())
                    crrMxDir = nxtMxDir;
            }
            case LEFT -> {
                if (astar.getGrid().getLeft(x, y).filter(c -> c.getState().isWalkable()).isPresent())
                    crrMxDir = nxtMxDir;
            }
        }

        switch (crrMxDir) {
            case UP -> astar.moveToUpCell();
            case RIGHT -> astar.moveToRightCell();
            case DOWN -> astar.moveToDownCell();
            case LEFT -> astar.moveToLeftCell();
        }
    }

    public void teleport() {
        astar.getGrid()
                .getRandomCell(AStarCell::isWalkable)
                .ifPresent(c -> astar.stopMovementAt(c.getX(), c.getY()));
    }


}
