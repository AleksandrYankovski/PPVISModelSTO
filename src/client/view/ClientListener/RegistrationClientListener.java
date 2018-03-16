package client.view.ClientListener;

import client.controler.Controler;
import client.view.registrationDialog.RegistrationDialog;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.io.FileNotFoundException;

/**
 * Created by Александр on 14.09.2017.
 */
public class RegistrationClientListener implements EventHandler {


    private RegistrationDialog registrationDialog;

    public RegistrationClientListener(RegistrationDialog registrationDialog) {

        this.registrationDialog = registrationDialog;

    }

    @Override
    public void handle(Event event) {
        registrationDialog.show();
    }


}
