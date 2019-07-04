package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.TimerTask;

import static sample.LayoutCreator.tileSize;


public class Timer extends StackPane {
    private Rectangle rectangle = new Rectangle();
    private Text text = new Text("00:00");
    private java.util.Timer timer = new java.util.Timer();
    private Integer second;
    private String secondStr;
    private Integer minute;
    private String minuteStr;
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            second++;
            if (second == 60) {
                second = 0;
                minute++;
            }
            secondStr = (second < 10 ? "0" + second.toString() : second.toString());
            minuteStr = (minute < 10 ? "0" + minute.toString() : minute.toString());
            text.setText(minuteStr + ":" + secondStr);
        }
    };

    public Timer(double x, double y) {
        second = 0;
        minute = 0;
        rectangle.setWidth(2 * tileSize);
        rectangle.setHeight(tileSize);
        rectangle.setArcHeight(30);
        rectangle.setArcWidth(30);
        rectangle.setFill(Paint.valueOf("#343C50"));
        text.setFill(Paint.valueOf("#FFFFFF"));
        text.setFont(Font.font(30));
        setWidth(2 * tileSize);
        setHeight(tileSize);
        setAlignment(Pos.CENTER);
        getChildren().addAll(rectangle, text);
        relocate(x * tileSize, y * tileSize);
        start();
    }


    public void start() {
        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
    }

    public void stop() {
        timer.cancel();
    }

}
