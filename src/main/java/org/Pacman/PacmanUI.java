package org.Pacman;

/**
 * @author Mohamed Sayed (maze7.56r9@gmail.com)
 */

import com.almasb.fxgl.ui.ProgressBar;
import com.almasb.fxgl.ui.UIController;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import  static com.almasb.fxgl.dsl.FXGL.*;


public class PacmanUI implements UIController {

    @FXML
    private Pane root;
    @FXML
    private Label LabelScore;
    @FXML
    private Label LabelTeleporter;

    private ProgressBar TimeBar;

    @Override
    public void init(){
        TimeBar = new ProgressBar(false);
        TimeBar.setHeight(50);
        TimeBar.setTranslateX(-60);
        TimeBar.setTranslateY(100);
        TimeBar.setRotate(-90);
        TimeBar.setFill(Color.GREEN);
        TimeBar.setLabelVisible(false);
        TimeBar.setMaxValue(App.TIME_PER_LEVEL);
        TimeBar.setMinValue(0);
        TimeBar.setCurrentValue(App.TIME_PER_LEVEL);
        TimeBar.currentValueProperty().bind(getip("time"));
        root.getChildren().addAll(TimeBar);
        LabelScore.setFont(getUIFactoryService().newFont(18));
        LabelScore.textProperty().bind(getip("score").asString("Score: \n%d"));

        LabelTeleporter.setFont(getUIFactoryService().newFont(12));
        LabelTeleporter.textProperty().bind(getip("teleport").asString("Teleport:\n[%d"));

    }



}
