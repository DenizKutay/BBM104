

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;



public abstract class Duck{
    static double height = 240 * DuckHunt.scale;
    static double width = 256 * DuckHunt.scale;
    private double duckXSpeed = 20 * DuckHunt.scale;
    private double duckYSpeed = -20 * DuckHunt.scale;
    private boolean isFlyingForwardly;

    private Image flyingCrossAni1;
    private Image flyingCrossAni2;
    private Image flyingCrossAni3;
    private Image flyingForwardAni1;
    private Image flyingForwardAni2;
    private Image flyingForwardAni3;
    private Image shootAni;
    private Image fallAni;

    private boolean getShoot = false;
    private HBox hitbox;
    private ImageView animation;
    private Timeline timeline;

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public void setAnimation(ImageView animation) {
        this.animation = animation;
    }

    public HBox getHitbox() {
        return hitbox;
    }

    public void setHitbox(HBox hitbox) {
        this.hitbox = hitbox;
    }

    public void setFlyingCrossAni1(Image flyingCrossAni1) {
        this.flyingCrossAni1 = flyingCrossAni1;
    }

    public void setFlyingCrossAni2(Image flyingCrossAni2) {
        this.flyingCrossAni2 = flyingCrossAni2;
    }

    public void setFlyingCrossAni3(Image flyingCrossAni3) {
        this.flyingCrossAni3 = flyingCrossAni3;
    }


    public void setFlyingForwardAni1(Image flyingForwardAni1) {
        this.flyingForwardAni1 = flyingForwardAni1;
    }



    public void setFlyingForwardAni2(Image flyingForwardAni2) {
        this.flyingForwardAni2 = flyingForwardAni2;
    }


    public void setFlyingForwardAni3(Image flyingForwardAni3) {
        this.flyingForwardAni3 = flyingForwardAni3;
    }


    public void setShootAni(Image shootAni) {
        this.shootAni = shootAni;
    }

    public void setFallAni(Image fallAni) {
        this.fallAni = fallAni;
    }


    public void setFlyingForwardly(boolean flyingForwardly) {
        isFlyingForwardly = flyingForwardly;
    }

    public boolean isGetShoot() {
        return getShoot;
    }


    public Duck(boolean isFlyingForwardly, double xPos, double yPos) {
        color();
        setFlyingForwardly(isFlyingForwardly);
        flying(xPos,yPos);
    }
    public void shot() {
        if (!isGetShoot()){
            getShoot = true;
            timeline.stop();
            animation.setImage(shootAni);
            animation.setFitWidth(shootAni.getWidth() * DuckHunt.scale);
            animation.setFitHeight(shootAni.getHeight() * DuckHunt.scale);
            falling(animation);
        }
    }
    public void flying(double xPos,double yPos) {
        ImageView animation = new ImageView();
        setAnimation(animation);
        HBox hitbox = new HBox();
        hitbox.getChildren().add(animation);
        setHitbox(hitbox);
        hitbox.setMaxHeight(flyingCrossAni1.getWidth() * DuckHunt.scale);
        hitbox.setMaxWidth(flyingCrossAni1.getHeight() * DuckHunt.scale);
        hitbox.setTranslateX(xPos);
        hitbox.setTranslateY(yPos);
        Timeline timeline = new Timeline();
        setTimeline(timeline);
        double aniDuration = 0.35;
        //Forward flying
        if (isFlyingForwardly) {

            timeline.setCycleCount(Timeline.INDEFINITE);
            KeyFrame ani1 = new KeyFrame(Duration.seconds(aniDuration), event -> {
                xMovement(hitbox,animation);
                hitbox.setTranslateY(hitbox.getTranslateY() + 10);
                animation.setImage(flyingForwardAni1);
                animation.setFitHeight(flyingForwardAni1.getHeight() * DuckHunt.scale);
                animation.setFitWidth(flyingForwardAni1.getWidth() * DuckHunt.scale);


            });
            KeyFrame ani2 = new KeyFrame(Duration.seconds(aniDuration * 2), s -> {
                xMovement(hitbox,animation);
                animation.setImage(flyingForwardAni2);
                animation.setFitHeight(flyingForwardAni2.getHeight() * DuckHunt.scale);
                animation.setFitWidth(flyingForwardAni2.getWidth() * DuckHunt.scale);


            });
            KeyFrame ani3 = new KeyFrame(Duration.seconds(aniDuration * 3), event -> {
                xMovement(hitbox,animation);
                animation.setImage(flyingForwardAni3);
                hitbox.setTranslateY(hitbox.getTranslateY() - 10);

            });
            timeline.getKeyFrames().add(ani2);
            timeline.getKeyFrames().add(ani3);
            timeline.getKeyFrames().add(ani1);
            timeline.play();
        }
        //flying cross
        else {

            timeline.setCycleCount(Timeline.INDEFINITE);

            KeyFrame ani1 = new KeyFrame(Duration.seconds(aniDuration), event -> {
                xMovement(hitbox,animation);
                yMovement(hitbox,animation);
                animation.setImage(flyingCrossAni1);
                animation.setFitHeight(flyingCrossAni1.getHeight() * DuckHunt.scale);
                animation.setFitWidth(flyingCrossAni1.getWidth() * DuckHunt.scale);


            });
            KeyFrame ani2 = new KeyFrame(Duration.seconds(aniDuration * 2), s -> {
                xMovement(hitbox,animation);
                yMovement(hitbox,animation);
                animation.setImage(flyingCrossAni2);
                animation.setFitHeight(flyingCrossAni2.getHeight() * DuckHunt.scale);
                animation.setFitWidth(flyingCrossAni2.getWidth() * DuckHunt.scale);


            });
            KeyFrame ani3 = new KeyFrame(Duration.seconds(aniDuration * 3), event -> {
                xMovement(hitbox,animation);
                yMovement(hitbox,animation);
                animation.setImage(flyingCrossAni3);

            });
            timeline.getKeyFrames().addAll(ani2,ani3,ani1);
            timeline.play();
        }
    }
    public void xMovement(HBox hitbox,ImageView animation) {
        hitbox.setTranslateX(hitbox.getTranslateX() + duckXSpeed);
        if ( hitbox.getTranslateX() >= width/2  - (hitbox.getWidth()/2)|| hitbox.getTranslateX() <= -(width/2) + (hitbox.getWidth()/2)){
            if (hitbox.getTranslateX() >= width/2 -(hitbox.getWidth()/2)) {
                hitbox.setTranslateX(width / 2 - (hitbox.getWidth() / 2));
            } else {
                hitbox.setTranslateX(-(width/2) + (hitbox.getWidth()/2));
            }
            animation.setScaleX(-animation.getScaleX());
            duckXSpeed = -duckXSpeed;
        }
    }
    public void yMovement(HBox hitbox,ImageView animation) {
        hitbox.setTranslateY(hitbox.getTranslateY() + duckYSpeed);
        if ( hitbox.getTranslateY() >= height/2  - (hitbox.getHeight()/2)|| hitbox.getTranslateY() <= -(height/2) + (hitbox.getHeight()/2)){
            if(hitbox.getTranslateY() >= height/2 - (hitbox.getHeight()/2)) {
                hitbox.setTranslateY(height/2 - (hitbox.getHeight()/2));
            } else {
                hitbox.setTranslateY(-(height/2) + (hitbox.getHeight()/2));
            }
            animation.setScaleY(-animation.getScaleY());
            duckYSpeed = -duckYSpeed;
        }
    }
    public void falling(ImageView animation) {
        if (duckYSpeed > 0) {
            animation.setScaleY(-animation.getScaleY());
        }
        Timeline falling = new Timeline();
        KeyFrame fallMoment = new KeyFrame(Duration.seconds(0.5),event -> {
            animation.setImage(fallAni);
            animation.setImage(fallAni);
            animation.setFitWidth(fallAni.getWidth() * DuckHunt.scale);
            animation.setFitHeight(fallAni.getHeight() * DuckHunt.scale);
            animation.setTranslateY(animation.getTranslateY() + (30 * DuckHunt.scale));
        });
        falling.getKeyFrames().add(fallMoment);
        falling.setCycleCount(Timeline.INDEFINITE);
        falling.play();
        Sounds.playSound(Sounds.duckFalls);
    }
    public abstract void  color();
}
