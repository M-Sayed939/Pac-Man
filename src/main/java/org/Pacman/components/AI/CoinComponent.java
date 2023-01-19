package org.Pacman.components.AI;
/**
 * @author Mohamed Sayed (maze7.56r9@gmail.com)
 */

import com.almasb.fxgl.core.collection.grid.Cell;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.paint.Color;
import org.Pacman.Pacman;


@Required(AStarMoveComponent.class)
public class CoinComponent extends Component {
    private AStarMoveComponent astar;
    private Entity targetCoin;

    private void highlight(Entity coin) {
        var texture = (Texture) coin.getViewComponent().getChildren().get(0);
        var newTexture = texture.multiplyColor(Color.RED).brighter();
        newTexture.setTranslateX(5);
        newTexture.setTranslateY(5);
        coin.getViewComponent().removeChild(texture);
        coin.getViewComponent().addChild(newTexture);
    }

    @Override
    public void onUpdate(double tpf) {
        if (targetCoin == null || !targetCoin.isActive()) {
            entity.getWorld()
                    .getRandom(Pacman.COIN)
                    .ifPresent(coin -> {
                        highlight(coin);
                        targetCoin = coin;
                    });
        } else {
            if (astar.isAtDestination()) {
                int x = targetCoin.call("getCellX");
                int y = targetCoin.call("getCellY");
                Cell cell = astar.getGrid().get(x, y);

                astar.getGrid()
                        .getRandomCell(c -> c.getState().isWalkable() && c.distance(cell) < 5)
                        .ifPresent(astar::moveToCell);
            }
        }
    }
}
