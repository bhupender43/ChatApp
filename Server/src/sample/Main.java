package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader page= new FXMLLoader(getClass().getResource("serverwin.fxml"));
        Parent root=page.load();
        ServerWin obj=page.getController();
        Server serve=new Server(obj);
        Thread th=new Thread(serve);
        th.start();
        primaryStage.setTitle("Multi-Threaded Server");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
