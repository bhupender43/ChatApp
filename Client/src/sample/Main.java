package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader root_l = new FXMLLoader(getClass().getResource("clientwin.fxml"));
        Parent root=root_l.load();
        ClientWin gui_obj=root_l.getController();
        Clients cli=new Clients(gui_obj);
        Thread th=new Thread(cli);
        th.start();
        primaryStage.setTitle("Client");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
