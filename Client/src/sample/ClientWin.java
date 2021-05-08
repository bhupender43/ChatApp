package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ClientWin {
    public TextField msgwin;
    public TextArea chatwin;
    String sing_msg="-1";
    public void send(ActionEvent actionEvent) {
        sing_msg=msgwin.getText();
        msgwin.clear();
    }
}
