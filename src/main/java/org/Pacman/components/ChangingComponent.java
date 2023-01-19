package org.Pacman.components;
/**
 * @author Mohamed Sayed (maze7.56r9@gmail.com)
 */

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Rectangle2D;


public class ChangingComponent extends Component {
    private Texture texture;
    private double lastX = 0;
    private double lastY = 0;
    private double TimeToSwitch = 0;
    private int SpriteColor = 0;

    public ChangingComponent(Texture texture) {
        this.texture = texture;
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        TimeToSwitch += tpf;
        if (TimeToSwitch >= 5) {
            SpriteColor = (int) (160 * FXGLMath.random(0, 2) * 0.24);
            TimeToSwitch = 0;
        }
        double dx = entity.getX() - lastX;
        double dy = entity.getY() - lastY;
        lastX = entity.getX();
        lastY = entity.getY();
        if (dx == 0 && dy == 0) {
            return;
        }
        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) {
                texture.setViewport(new Rectangle2D(130 * 3 * 0.24, SpriteColor, 130 * 0.24, 160 * 0.24));
            } else {
                texture.setViewport(new Rectangle2D(130 * 2 * 0.24, SpriteColor, 130 * 0.24, 160 * 0.24));
            }
        } else {
            if (dy > 0) {
                texture.setViewport(new Rectangle2D(0, SpriteColor, 130 * 0.24, 160 * 0.24));
            } else {
                texture.setViewport(new Rectangle2D(130 * 0.24, SpriteColor, 130 * 0.24, 160 * 0.24));
            }
        }
    }
}
