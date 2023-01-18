package org.Pacman;
/**
 * @author Mohamed Sayed (maze7.56r9@gmail.com)
 */

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;
import static org.Pacman.Pacman.*;
import static org.Pacman.App.*;


public class Factory implements EntityFactory {

    @Spawns("1")
    public Entity newBlock(SpawnData data){
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
    public Entity newCoin(SpawnData data){
        var view = texture("coin.png");
        view.setTranslateX(5);
        view.setTranslateY(5);

        return entityBuilder(data)
                .type(COIN)
                .bbox(new HitBox(new Point2D(5,5), BoundingShape.box(30,30)))
                .view(view)
                .zIndex(-1)
                .with(new CollidableComponent(true))
                .with(new CellMoveComponent(BLOCK_SIZE,BLOCK_SIZE,50))
                .scale(0.5,0.5)
                .build();
    }

//    private Supplier<Component> aiComponents = new Supplier<>() {
////        private Map<Integer, Supplier<Component>> components = Map.of(
////                0, ()-> new ChasePlayer().withDelay(),
////                1, Gua
////
//////        )
//        @Override
//        public Component get() {
//            return null;
//        }
//    }
}
