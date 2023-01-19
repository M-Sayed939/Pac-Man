package org.Pacman;
/**
 * @author Mohamed Sayed (maze7.56r9@gmail.com)
 */

import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.components.RandomAStarMoveComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import com.almasb.fxgl.physics.*;
import com.almasb.fxgl.texture.AnimatedTexture;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.Pacman.components.AI.*;
import org.Pacman.components.ChangingComponent;
import org.Pacman.components.PlayerComponent;

import java.util.Map;
import java.util.function.Supplier;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.geto;
import static com.almasb.fxgl.dsl.FXGL.texture;

import static org.Pacman.Pacman.*;
import static org.Pacman.App.*;


public class Factory implements EntityFactory {

    @Spawns("1")
    public Entity newBlock(SpawnData data) {
        var rect = new Rectangle(38, 38, Color.BLACK);
        rect.setArcWidth(25);
        rect.setHeight(25);
        rect.setStrokeWidth(1);
        rect.setStroke(Color.BLUE);

        return entityBuilder(data)
                .type(BLOCK)
                .viewWithBBox(rect)
                .zIndex(-1)
                .build();
    }

    @Spawns("0")
    public Entity newCoin(SpawnData data) {
        var view = texture("coin.png");
        view.setTranslateX(5);
        view.setTranslateY(5);

        return entityBuilder(data)
                .type(COIN)
                .bbox(new HitBox(new Point2D(5, 5), BoundingShape.box(30, 30)))
                .view(view)
                .zIndex(-1)
                .with(new CollidableComponent(true))
                .with(new CellMoveComponent(BLOCK_SIZE, BLOCK_SIZE, 50))
                .scale(0.5, 0.5)
                .build();
    }

    @Spawns("P")
    public Entity newPlayer(SpawnData data) {
        AnimatedTexture view = texture("player.png").toAnimatedTexture(2, Duration.seconds(0.33));

        return entityBuilder(data)
                .type(PLAYER)
                .bbox(new HitBox(new Point2D(4, 4), BoundingShape.box(32, 32)))
                .anchorFromCenter()
                .view(view.loop())
                .with(new CollidableComponent(true))
                .with(new CellMoveComponent(BLOCK_SIZE, BLOCK_SIZE, 200).allowRotation(true))
                .with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid"))))
                .with(new PlayerComponent())
                .rotationOrigin(35 / 2.0, 40 / 2.0)
                .build();
    }

    private Supplier<Component> aiComponents = new Supplier<>() {
        private Map<Integer, Supplier<Component>> components = Map.of(
                0, () -> new ChasePlayer().withDelay(),
                1, CoinComponent::new,
                2, RandomAStarMoveComponent::new,
                3, ChasePlayer::new
        );
        private int index = 0;

        @Override
        public Component get() {
            if (index == 4) {
                index = 0;
            }
            return components.get(index++).get();
        }
    };

    @Spawns("E")
    public Entity newEnemy(SpawnData data) {
        return entityBuilder(data)
                .type(ENEMY)
                .bbox(new HitBox(new Point2D(2, 2), BoundingShape.box(36, 36)))
                .anchorFromCenter()
                .with(new CollidableComponent(true))
                .with(new ChangingComponent(texture("spritesheet.png", 695 * 0.24, 1048 * 0.24)))
                .with(new CellMoveComponent(BLOCK_SIZE, BLOCK_SIZE, 125))
                .with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid"))))
                .with(aiComponents.get())
                .build();
    }
}
