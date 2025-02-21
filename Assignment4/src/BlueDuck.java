

import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

import java.io.File;

public class BlueDuck extends Duck {
    public BlueDuck(boolean isFlyingForwardly,double xPos,double yPos) {
        super(isFlyingForwardly,xPos,yPos);
    }

    @Override
    public void color() {
        setFlyingCrossAni1(new Image(new File("assets/duck_blue/1.png").toURI().toString()));
        setFlyingCrossAni2(new Image(new File("assets/duck_blue/2.png").toURI().toString()));
        setFlyingCrossAni3(new Image(new File("assets/duck_blue/3.png").toURI().toString()));
        setFlyingForwardAni1(new Image(new File("assets/duck_blue/4.png").toURI().toString()));
        setFlyingForwardAni2(new Image(new File("assets/duck_blue/5.png").toURI().toString()));
        setFlyingForwardAni3(new Image(new File("assets/duck_blue/6.png").toURI().toString()));
        setShootAni(new Image(new File("assets/duck_blue/7.png").toURI().toString()));
        setFallAni(new Image(new File("assets/duck_blue/8.png").toURI().toString()));
    }
}
