package client.controler;

import client.model.*;
import client.model.Client.Adres;
import client.model.database.*;
import client.view.MainClientDialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by Александр on 13.09.2017.
 */
public class Controler {


    private Database database;
    private MainClientDialog mainView;


    public Controler() throws SQLException, ClassNotFoundException, FileNotFoundException, ParseException {
        database=new Database();
        mainView = new MainClientDialog(this);
    }

    public void entry(String id) throws SQLException, ParseException {

        ClientTable clientTable= (ClientTable) database.getTableByName(ClientTable.class.getName());
        RecordTable recordTable= (RecordTable) database.getTableByName(RecordTable.class.getName());

        Boolean bool = clientTable.isExist(id);


        if(bool.equals(false))
          mainView.getRedactionClientDialog().confirm("Client doesn't exist");
        else {
            Client client=clientTable.getClientById(id);

            Integer kolOverdueRecords=recordTable.getKolOverdueRecordsForClient(id);
            client.setRating(client.getRating()-kolOverdueRecords);
            if(!kolOverdueRecords.equals(0)) {
                client.getDiscontCard().setMinustAccumulationPercentage();

            }

            clientTable.update(id,client);

            recordTable.deleteOverOverdueRecordsForClient(id);

            if (client.getRating() < 3)
                mainView.getRedactionClientDialog().confirm("you are add to blacklist");
            else
                mainView.getRedactionClientDialog().openRedactionDialog();

            mainView.getRedactionClientDialog().getDiscontPercentageScreen().setPercentage(
                    client.getDiscontCard().getAccumulationPercentage() );

        }
    }

    public void addClient(Collection<TextField> fioCollection, Collection<TextField> adresCollection,
                          PasswordField passwordField, String photoPass)
            throws SQLException, IOException {




        ArrayList<TextField> arrayListFio = new ArrayList<>();
        arrayListFio.addAll(fioCollection);

        ArrayList<TextField> arrayListAdres = new ArrayList<>();
        arrayListAdres.addAll(adresCollection);

        Client client = new Client();


        client.setName(arrayListFio.get(0).getText());
        client.setSurname(arrayListFio.get(1).getText());
        client.setPatronymic(arrayListFio.get(2).getText());
        client.setPassword(passwordField.getText());
        client.setPhoto(ImageIO.read(new File(photoPass)));

        Adres adres = new Adres();

        adres.setCountry(arrayListAdres.get(0).getText());
        adres.setCity(arrayListAdres.get(1).getText());
        adres.setStreet(arrayListAdres.get(2).getText());
        adres.setHouse(arrayListAdres.get(3).getText());
        adres.setFlat(Integer.valueOf(arrayListAdres.get(4).getText()));
        client.setAdres(adres);


        client.activateDiscontCard(passwordField.getText());

        DiscontCardTable discontCardTable= (DiscontCardTable) database.getTableByName
                (DiscontCardTable.class.getName());
        discontCardTable.add(client.getDiscontCard());


        ClientTable clientTable= (ClientTable) database.getTableByName(ClientTable.class.getName());
        clientTable.add(client);

        mainView.getRegistrationDialog().answerToRegistration();

    }


    public void addRecord(Collection<TextField> collectionCar, Failure failure, LocalDate localDate,
                          LocalTime localTime, Boolean paid, String idClient) throws SQLException, ParseException {


        Record record = new Record();

        ArrayList<TextField> textFieldCar = new ArrayList<>();
        textFieldCar.addAll(collectionCar);

        record.getCarModel().setMark(textFieldCar.get(0).getText());
        record.getCarModel().setModel(textFieldCar.get(1).getText());
        record.getCarModel().setYear(Integer.valueOf(textFieldCar.get(2).getText()));
        record.getCarModel().setBulk(Double.valueOf(textFieldCar.get(3).getText()));
        record.getCarModel().setNumberStateRegistration(textFieldCar.get(4).getText());
        record.getCarModel().setColor(textFieldCar.get(5).getText());
        record.setFailure(failure);
        record.setDate(localDate);
        record.setTime(localTime);
        record.setStatus(paid);


        record.setClient(((ClientTable) database.getTableByName(ClientTable.class.getName()))
               .getClientById(idClient));

        record.getClient().getDiscontCard().icreaseAccumulationPercentage();

        DiscontCardTable discontCardTable =(DiscontCardTable) database.getTableByName(DiscontCardTable.
                class.getName());

        discontCardTable.update(record.getClient().getDiscontCard().getAccumulationPercentage(),idClient);

        CarTable carTable= (CarTable) database.getTableByName(CarTable.class.getName());
        carTable.add(record.getCarModel());


        RecordTable recordTable=(RecordTable) database.getTableByName(RecordTable.class.getName());
        recordTable.add(record);


        mainView.getRedactionClientDialog().getRecordsTable().refresh();
        mainView.getRedactionClientDialog().getDiscontPercentageScreen().setPercentage(
                record.getClient().getDiscontCard().getAccumulationPercentage());

    }


    public void deleteRecord(Integer key) throws SQLException {

        RecordTable recordTable= (RecordTable) database.getTableByName(RecordTable.class.getName());

        recordTable.delete(key);

        mainView.getRedactionClientDialog().getRecordsTable().refresh();
    }

    public Database getDatabase(){return  database;}

}