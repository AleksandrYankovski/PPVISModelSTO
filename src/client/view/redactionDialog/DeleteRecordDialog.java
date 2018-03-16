package client.view.redactionDialog;

import client.controler.Controler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.sql.SQLException;
import java.util.Optional;

/**
 * Created by Александр on 29.09.2017.
 */
public class DeleteRecordDialog {


    private Controler clientControler;


    public DeleteRecordDialog(Controler clientControler) {

        this.clientControler = clientControler;

    }


    public void inputId() {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please enter id record:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                clientControler.deleteRecord(Integer.valueOf(result.get()));
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("This id doesn't exist.Try again,please");
                alert.showAndWait();
            }
            catch (NumberFormatException e)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("This id doesn't exist.Try again,please");
                alert.showAndWait();
            }
        }

    }

}
