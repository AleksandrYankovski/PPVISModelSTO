package client.view.redactionDialog;

import client.model.Failure;
import client.model.Record;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class ComboxFailure extends ComboBox<Failure> {


    private final ObservableList observableList;


    public ComboxFailure() {

        observableList = FXCollections.observableArrayList();

        fillObservableList();

        this.setItems(observableList);
    }

    private void fillObservableList() {
        observableList.addAll(new Failure("Development of steering traction", 100),
                new Failure("Inspection inspection of the suspension of cars", 120),
                new Failure("Ultrasonic cleaning of injectors of injection system of petrol engines " +
                        "(for 1 pc.)", 70),
                new Failure("Diagnosis of shock absorbers (shock - tester)", 90),
                new Failure("Check of brakes on the stand", 120),
                new Failure("Inspection of electrical equipment", 110),
                new Failure("Computer Diagnostics of Electronic Vehicle Control Systems", 80),
                new Failure("Oil change on passenger cars", 90),
                new Failure("Removing and installing engine protection from plastic", 100),
                new Failure("Removing and installing engine protection from plastic", 120)
        );
    }


}
