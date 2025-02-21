import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.util.Duration;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class DuckHunt extends Application {
    public static final double volume = 0.10;
    public static final double scale = 2.0;
    public static final double height = 240 * scale;
    public static final double width = 256 * scale;
    private static Stage primaryStage;
    private int backgroundChoose = 0;

    private int crosshairChoose = 0;

    private static final String[] crosshairPaths = {
            "assets/crosshair/1.png","assets/crosshair/2.png","assets/crosshair/3.png","assets/crosshair/4.png","assets/crosshair/5.png","assets/crosshair/6.png","assets/crosshair/7.png"
    };

    private static final String[] backgroundPaths = {
            "assets/background/1.png","assets/background/2.png","assets/background/3.png","assets/background/4.png","assets/background/5.png","assets/background/6.png"
    };

    private static final String[] foregrounds = {
      "assets/foreground/1.png","assets/foreground/2.png","assets/foreground/3.png","assets/foreground/4.png","assets/foreground/5.png","assets/foreground/6.png"
    };

    private ImageView gameBackground;
    private ImageView crosshairImage;
    private final ArrayList<Level> levels = new ArrayList<>();
    public static int currentLevel = 0;
    public static boolean gameCompleted = false;

    public static void setPrimaryStage(Stage primaryStage) {
        DuckHunt.primaryStage = primaryStage;
    }

    public static void main(String[] args) {
            launch(args);
    }

    /**
     *
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set. The primary stage will be embedded in
     * the browser if the application was launched as an applet.
     * Applications may create other stages, if needed, but they will not be
     * primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage){
        setPrimaryStage(primaryStage);
        primaryStage.setTitle("HUBBM Duck Hunt");
        File fileIcon = new File("assets/favicon/1.png");
        Image icon = new Image(fileIcon.toURI().toString());
        primaryStage.getIcons().add(icon);
        Sounds.setVolume(volume);
        Sounds.title.setCycleCount(MediaPlayer.INDEFINITE); //title song playing infinite
        Sounds.title.play();
        openingScreen(Sounds.title);    //goes to opening screen
    }

    /**
     * opening screen title song plays in loop
     * @param mediaPlayer title music
     */
    public void openingScreen(MediaPlayer mediaPlayer) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root,width,height);
        File fileBackground = new File("assets/welcome/1.png");

        //creating texts
        Text quitInfo = createText("PRESS ESC TO EXIT",true,15);
        Text enterInfo = createText("PRESS ENTER TO START",true,15);

        //placements of texts
        quitInfo.setTranslateX(0);
        quitInfo.setTranslateY(33 * scale);
        enterInfo.setTranslateX(0);
        enterInfo.setTranslateY(50 * scale);

        root.getChildren().addAll(quitInfo,enterInfo);

        Image backgroundImage = new Image(fileBackground.toURI().toString());

        //KEYS
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            //ESC button quits the game
            if (event.getCode() == KeyCode.ESCAPE) {
                primaryStage.close();
            }
            //ENTER button goes to selection screen
            if (event.getCode() == KeyCode.ENTER) {
                selectionScreen(mediaPlayer);
            }
        });
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, false));    //background
        root.setBackground(new Background(background));
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public void selectionScreen(MediaPlayer mediaPlayer) {
        AtomicBoolean selectDone = new AtomicBoolean(false);    //is player done selecting
        StackPane root = new StackPane();
        Scene scene = new Scene(root,width,height);
        crosshairChoose = 0;
        backgroundChoose = 0;

        //creating background and placements
        gameBackground = new ImageView(new Image(new File(backgroundPaths[backgroundChoose]).toURI().toString()));
        gameBackground.setFitHeight(height);
        gameBackground.setFitWidth(width);

        //creating crosshair image and placements
        crosshairImage = new ImageView(new Image(new File(crosshairPaths[crosshairChoose]).toURI().toString()));
        crosshairImage.setFitWidth(width/30);
        crosshairImage.setFitHeight(height/30);
        crosshairImage.setX(width/2);
        crosshairImage.setY(height/2);

        //creating texts and placements
        Text arrowInfo = createText("USE ARROW TO NAVIGATE");
        Text startInfo = createText("PRESS ENTER TO START");
        Text exitInfo = createText("PRESS ESC TO EXIT");

        arrowInfo.setTranslateY(-(height/2) + 18 * scale);
        startInfo.setTranslateY(-(height/2) + 30 * scale);
        exitInfo.setTranslateY(-(height/2) + 42 * scale);

        root.getChildren().addAll(gameBackground,crosshairImage,arrowInfo,startInfo,exitInfo);


        //KEYS
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if(!selectDone.get()) {
                if (event.getCode() == KeyCode.RIGHT) {
                    changeBackground(1);
                }
                if (event.getCode() == KeyCode.LEFT) {
                    changeBackground(-1);
                }
                if (event.getCode() == KeyCode.UP) {
                    changeCrosshair(1);
                }
                if (event.getCode() == KeyCode.DOWN) {
                    changeCrosshair(-1);
                }
                //starts the intro sound and goes to levels
                if (event.getCode() == KeyCode.ENTER) {
                    beforeGame();
                    selectDone.set(true);
                    Sounds.playSound(Sounds.intro);
                    Sounds.intro.setOnEndOfMedia(this::generateLevel);
                    mediaPlayer.stop();
                }
                if (event.getCode() == KeyCode.ESCAPE) {
                    openingScreen(mediaPlayer);
                }
            }
            });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * changes the background with pressing right or left arrow
     * @param change + or - 1 changes the index of background arraylist
     */
    private void changeBackground(int change) {
        backgroundChoose = (backgroundChoose + change + backgroundPaths.length) % backgroundPaths.length;
        gameBackground.setImage(new Image(new File(backgroundPaths[backgroundChoose]).toURI().toString()));
    }

    /**
     * changes the crosshair with pressing up and down arrow
     * @param change + or - 1 changes the index of crosshair arraylist
     */
    private void changeCrosshair(int change) {
        crosshairChoose = (crosshairChoose + change + crosshairPaths.length) % crosshairPaths.length;
        crosshairImage.setImage(new Image(new File(crosshairPaths[crosshairChoose]).toURI().toString()));
    }

    /**
     * Arrange the important details of level class such as background, crosshair, width and height
     */
    private void beforeGame() {
        Level.setBackground(new ImageView(new Image(new File(backgroundPaths[backgroundChoose]).toURI().toString())));
        Level.setForeground(new ImageView(new Image(new File(foregrounds[backgroundChoose]).toURI().toString())));
        Level.getForeground().setFitHeight(height);
        Level.getForeground().setFitWidth(width);
        Level.getBackground().setFitHeight(height);
        Level.getBackground().setFitWidth(width);
        Level.getForeground().setDisable(true);
        Level.setWidth(width);
        Level.setHeight(height);
        Image imageCursor = new Image(new File(crosshairPaths[crosshairChoose]).toURI().toString());
        Level.setCrosshair(imageCursor);
    }

    /**
     * generates the levels.There is 6 levels in this code
     */
    public void generateLevel() {
        //creating level classes and adding to the levels arraylist
        resetGame(levels);
        for (int numberOfLevels = 0; numberOfLevels < 6; numberOfLevels++) {
            Level level = new Level();
            levels.add(level);
        }
        //arraylists of ducks
        ArrayList<Duck> level1 = new ArrayList<>();
        ArrayList<Duck> level2 = new ArrayList<>();
        ArrayList<Duck> level3 = new ArrayList<>();
        ArrayList<Duck> level4 = new ArrayList<>();
        ArrayList<Duck> level5 = new ArrayList<>();
        ArrayList<Duck> level6 = new ArrayList<>();

        //creating ducks
        //l1d2 -> level 1 Duck 2
        Duck l1d1 = new BlackDuck(true,-(width/10), -(height/3));

        Duck l2d1 = new BlueDuck(false, -(width/10) , (0 * scale));

        Duck l3d1 = new RedDuck(true,-(width/10),-(height/3));
        Duck l3d2 = new BlueDuck(true,-(width),-(height/6));

        Duck l4d1 = new BlueDuck(false,-(width),-(height/3) + 10 * scale);
        Duck l4d2 = new BlackDuck(false,-(width/10),-(height/3));

        Duck l5d1 = new RedDuck(false,(width/10),-(height/5));
        Duck l5d2 = new BlueDuck(true,-(width/10),-(height/6));
        Duck l5d3 = new BlackDuck(true, -(width/2),-(height/3));

        Duck l6d1 = new BlackDuck(false,(width/5),-(height/2));
        Duck l6d2 = new RedDuck(false,(width/2),(height/6));
        Duck l6d3 = new BlueDuck(false,-(width/2),-(height/10));

        //adding ducks to the arraylists
        level1.add(l1d1);
        level2.add(l2d1);
        level3.add(l3d2);
        level3.add(l3d1);
        level4.add(l4d1);
        level4.add(l4d2);
        level5.add(l5d1);
        level5.add(l5d2);
        level5.add(l5d3);
        level6.add(l6d1);
        level6.add(l6d2);
        level6.add(l6d3);

        //set arraylists to their level
        levels.get(5).setDucks(level6);
        levels.get(4).setDucks(level5);
        levels.get(3).setDucks(level4);
        levels.get(2).setDucks(level3);
        levels.get(1).setDucks(level2);
        levels.get(0).setDucks(level1);

        //goes to game
        inGame();
    }

    public void inGame() {
        StackPane rootOfCurrentLevel = new StackPane();
        Scene sceneOfCurrentLevel = new Scene(rootOfCurrentLevel,width,height);
        levels.get(currentLevel).showLevel(rootOfCurrentLevel);     //shows the level
        Level.getForeground().toFront();    //gets foreground to the up front

        sceneOfCurrentLevel.addEventFilter(KeyEvent.KEY_PRESSED,event -> {
            if(!gameCompleted) {
                //level passed
                if (levels.get(currentLevel).isLevelFinished()) {
                    if (levels.get(currentLevel).isLevelWon()) {
                        if (event.getCode() == KeyCode.ENTER) {
                            currentLevel++;
                            Sounds.stopSounds();
                            inGame();
                        }
                    }

                    //Game lost
                    else {
                        if (event.getCode() == KeyCode.ENTER) {
                            generateLevel();
                        }
                        if (event.getCode() == KeyCode.ESCAPE) {
                            Sounds.stopSounds();
                            start(primaryStage);
                        }
                    }
                }
            }
            //Game Completed Successfully
            else{
                if(event.getCode() == KeyCode.ENTER) {
                    Sounds.stopSounds();
                    generateLevel();
                }
                if (event.getCode() == KeyCode.ESCAPE) {
                    try {
                        Sounds.stopSounds();
                        start(primaryStage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        primaryStage.setScene(sceneOfCurrentLevel);
        primaryStage.show();
    }

    /**
     * arrange the font and color
     * @param text text you want to add
     * @return text with modified font and color
     */
    public static Text createText(String text) {
        Text output = new Text(text);
        output.setFont(Font.font("verdana",FontWeight.BOLD,FontPosture.REGULAR,10 * scale));
        output.setFill(Color.ORANGE);
        return output;
    }

    /**
     * arrange the size more detailed
     * @param text text you want to add
     * @param blink is your text going to blink
     * @param size size of the text
     * @return text
     */
    public static Text createText(String text, boolean blink,int size) {
        Text output = new Text(text);
        output.setFont(Font.font("verdana",FontWeight.BOLD,FontPosture.REGULAR,size * scale));
        output.setFill(Color.ORANGE);

        if (blink) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0), event -> output.setVisible(true)),
                    new KeyFrame(Duration.seconds(1), event -> output.setVisible(false)),
                    new KeyFrame(Duration.seconds(2), event -> output.setVisible(true))
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
        return output;
    }

    /**
     * resets the game clear all levels set current level to 0 etc.
     * @param levels levels arraylist which contains all the levels
     */
    public void resetGame(ArrayList<Level> levels) {
        levels.clear();
        Level.setLevelCount(0);
        currentLevel = 0;
        gameCompleted = false;
        Sounds.stopSounds();
    }

    }
