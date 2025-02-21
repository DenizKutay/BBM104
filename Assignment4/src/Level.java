
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import java.util.ArrayList;

public class Level {
    private static ImageView background;
    private static ImageView foreground;
    private static Image crosshair;
    private static int levelCount = 0;
    private static double height;
    private static double width;
    private ArrayList<Duck> ducks;  //ducks in the level
    private boolean levelFinished = false;
    private boolean levelWon = false;
    private int ammo;

    public static void setLevelCount(int levelCount) {
        Level.levelCount = levelCount;
    }

    public boolean isLevelWon() {
        return levelWon;
    }

    public static void setCrosshair(Image crosshair) {

        Level.crosshair = crosshair;
    }
    public boolean isLevelFinished() {
        return levelFinished;
    }

    public void setDucks(ArrayList<Duck> ducks) {
        this.ducks = ducks;
    }

    public static ImageView getBackground() {
        return background;
    }

    public static void setBackground(ImageView background) {
        Level.background = background;
    }

    public static ImageView getForeground() {
        return foreground;
    }

    public static void setForeground(ImageView foreground) {
        Level.foreground = foreground;
    }

    public static void setHeight(double height) {
        Level.height = height;
    }

    public static void setWidth(double width) {
        Level.width = width;
    }

    public Level() {
        levelCount++;
    }

    /**
     * shows the level
     * @param root of the game
     */
    public void showLevel(StackPane root) {
        ammo = ducks.size() * 3;    //calculate ammo
        Cursor cursor = new ImageCursor(crosshair);
        root.setCursor(cursor);
        root.getChildren().add(background);
        root.getChildren().add(foreground);
        //adding the ducks
        for (Duck duck:ducks) {
            root.getChildren().add(duck.getHitbox());
        }
        Text levelCounter = DuckHunt.createText(String.format("Level %d/%d",DuckHunt.currentLevel + 1,levelCount),false,7); //shows the level at the top of the game
        Text ammoCounter = DuckHunt.createText(String.format("Ammo Left: %d",ammo),false,7);    //shows the ammo at the right top corner

        //scaling and adding
        ammoCounter.setTranslateY(-(height/2) + 6 * DuckHunt.scale);
        root.getChildren().add(ammoCounter);
        ammoCounter.setTranslateX(width/2 - 33 * DuckHunt.scale);
        root.getChildren().add(levelCounter);
        levelCounter.setTranslateY(-(height/2) + 6 * DuckHunt.scale);

        //click with mouse
        root.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (!levelFinished) {
                    //SHOOTING
                    double mouseX = event.getX();
                    double mouseY = event.getY();
                    ammo--;
                    ammoCounter.setText(String.format("Ammo Left: %d", ammo));
                    Sounds.playSound(Sounds.gunshot);

                    //shooting duck
                    for (Duck duck : ducks) {
                        if (duck.getHitbox().getTranslateX() - duck.getHitbox().getWidth()/2 < mouseX - width/2 && mouseX - width/2 < duck.getHitbox().getTranslateX() + duck.getHitbox().getWidth()/2 && duck.getHitbox().getTranslateY() - duck.getHitbox().getHeight()/2 < mouseY - height/2 && mouseY - height/2 < duck.getHitbox().getTranslateY() + duck.getHitbox().getHeight()/2 ) {
                            duck.shot();
                        }
                    }
                    ducks.removeIf(Duck::isGetShoot);   //remove the duck from level
                    //LEVEL COMPLETED SUCCESFULLY
                    if (ducks.size() == 0) {
                        levelFinished = true;   //level finished
                        levelWon = true;        //level won
                        //GAME COMPLETED
                        if (levelCount == DuckHunt.currentLevel + 1) {
                            DuckHunt.gameCompleted = true;
                            Text completed = DuckHunt.createText("You have completed the game!");
                            Text enter = DuckHunt.createText("Press ENTER to play again", true, 10);
                            Text escape = DuckHunt.createText("Press ESC to exit", true, 10);
                            Sounds.playSound(Sounds.gameCompleted);
                            completed.setTranslateY(-15 * DuckHunt.scale);
                            escape.setTranslateY(15 * DuckHunt.scale);
                            root.getChildren().addAll(escape,enter,completed);
                        }
                        //LEVEL WON
                        else {
                            Text youWin = DuckHunt.createText("YOU WIN!", true, 10);
                            Text nextLevel = DuckHunt.createText("Press ENTER to play next level", false, 10);
                            youWin.setTranslateY(-10 * DuckHunt.scale);
                            nextLevel.setTranslateY(10 * DuckHunt.scale);
                            Sounds.playSound(Sounds.levelCompleted);
                            root.getChildren().addAll(nextLevel,youWin);
                        }

                    }
                    //LEVEL LOST
                    else if (ammo == 0) {
                        levelFinished = true;   //level finished
                        Sounds.playSound(Sounds.gameOver);
                        Text gameOver = DuckHunt.createText("GAME OVER!");
                        Text enter = DuckHunt.createText("Press ENTER to play again",true,10);
                        Text escape = DuckHunt.createText("Press ESC to exit",true,10);
                        gameOver.setTranslateY(-15 * DuckHunt.scale);
                        escape.setTranslateY(15 * DuckHunt.scale);
                        Sounds.playSound(Sounds.gameOver);
                        root.getChildren().addAll(enter,escape,gameOver);
                    }
                }
            }
        });
    }
}
