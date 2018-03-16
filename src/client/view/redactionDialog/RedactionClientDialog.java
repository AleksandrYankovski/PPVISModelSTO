package client.view.redactionDialog;

import client.controler.Controler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Optional;

/**
 * Created by Александр on 14.09.2017.
 */
public class RedactionClientDialog extends Stage {



    private Controler clientControler;
    private String foreignKey;
    private BorderPane root;
    private Scene scene;
    private ManagerRecords managerRecords;
    private RecordsTable recordsTable;
    private DiscontPercentageScreen discontPercentageScreen;



    public RedactionClientDialog(Controler clientControler) throws SQLException, ParseException {

        this.clientControler = clientControler;

        root = new BorderPane();
        root.setStyle("-fx-background-color: orange");

        scene = new Scene(root, 1320, 580);

        managerRecords = new ManagerRecords(clientControler);
        root.setLeft(managerRecords);

        recordsTable = new RecordsTable(clientControler);
        root.setCenter(recordsTable);

        discontPercentageScreen=new DiscontPercentageScreen();
        root.setBottom(discontPercentageScreen);




        this.setScene(scene);
    }

    public void inputIdCLient() throws SQLException, ParseException {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please enter your id:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            foreignKey = result.get();
            clientControler.entry(foreignKey);
        }
    }

    public void confirm(String answer) throws SQLException {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText(answer);
            alert.showAndWait();
    }

    public void openRedactionDialog()
    {
        managerRecords.setForeignKey(foreignKey);
        recordsTable.setForeignKey(foreignKey);
        recordsTable.refresh();
        this.show();
    }



    public ManagerRecords getManagerRecords() {
        return managerRecords;
    }

    public DiscontPercentageScreen getDiscontPercentageScreen() {
        return discontPercentageScreen;
    }

    public RecordsTable getRecordsTable() {
        return recordsTable;
    }
}
