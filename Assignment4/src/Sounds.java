
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Sounds {
    public static MediaPlayer duckFalls = new MediaPlayer(new Media(new File("assets/effects/DuckFalls.mp3").toURI().toString()));
    public static MediaPlayer gameCompleted = new MediaPlayer(new Media(new File("assets/effects/GameCompleted.mp3").toURI().toString()));
    public static MediaPlayer gameOver = new MediaPlayer(new Media(new File("assets/effects/GameOver.mp3").toURI().toString()));
    public static MediaPlayer gunshot = new MediaPlayer(new Media(new File("assets/effects/Gunshot.mp3").toURI().toString()));
    public static MediaPlayer intro = new MediaPlayer(new Media(new File("assets/effects/Intro.mp3").toURI().toString()));
    public static MediaPlayer levelCompleted = new MediaPlayer(new Media(new File("assets/effects/LevelCompleted.mp3").toURI().toString()));
    public static MediaPlayer title = new MediaPlayer(new Media(new File("assets/effects/Title.mp3").toURI().toString()));

    private static final MediaPlayer[] sounds = {
            duckFalls,gameCompleted,gameOver,gunshot,intro,levelCompleted,title
    };


    public static void playSound(MediaPlayer sound) {
        if (sound.getStatus() == MediaPlayer.Status.PLAYING) {
            sound.stop();
        }
        sound.play();
    }

    public static void setVolume(double volume) {
        for (MediaPlayer mediaPlayer : sounds) {
            mediaPlayer.setVolume(volume);
        }
    }
    public static void stopSounds() {
        for(MediaPlayer mediaPlayer : sounds) {
            mediaPlayer.stop();
        }
    }
}
