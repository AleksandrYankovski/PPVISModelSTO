package client.model.database;

import client.model.Client;
import client.model.Record;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Александр on 26.09.2017.
 */
public class ClientTable implements Table {


    private Connection connection;
    private final static String SQL_ID_IN_CLIENT =
            "SELECT id FROM client WHERE id=?";
    private final static String SQL_CREATE_CLIENT =
            "INSERT INTO client(name,surname,patronymic,country,city,street,house,flat," +
                    "idDiscontCard,id) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?)";
    private final static String SQL_GET_CLIENT_BY_ID = "SELECT client.*,discontCard.accumulationPercentage" +
            " FROM client natural join discontCard " +
            " WHERE id=?";
  private final static String SQL_UPDATE_CLIENT_BY_ID="UPDATE client inner join discontCard on " +
          "client.idDiscontCard=discontCard.id SET name=?,surname=?,patronymic=?," +
          "country=?,city=?,street=?,house=?,flat=?,rating=?,accumulationPercentage=? " +
          "where client.id=? ";



    public ClientTable(Connection connection) throws SQLException {
        this.connection = connection;
    }


    public Client getClientById(String id) throws SQLException {
        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_GET_CLIENT_BY_ID);

        preparedStatement.setString(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        Client client = new Client();

        resultSet.next();

        client.setName(resultSet.getString(1));
        client.setSurname(resultSet.getString(2));
        client.setPatronymic(resultSet.getString(3));

        Client.Adres adres = new Client.Adres();
        client.setAdres(adres);

        adres.setCountry(resultSet.getString(4));
        adres.setCity(resultSet.getString(5));
        adres.setStreet(resultSet.getString(6));
        adres.setHouse(resultSet.getString(7));
        adres.setFlat(resultSet.getInt(8));

        // client.setPhoto(resultSet.getBigDecimal(9));

        client.activateDiscontCard(resultSet.getString(10));
        client.setPassword(resultSet.getString(11));
        client.setRating(resultSet.getInt(12));
        client.getDiscontCard().setAccumulationPercentage(resultSet.getInt(13));
        return client;
    }



    public Boolean add(Client client) throws SQLException, IOException {


        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_CREATE_CLIENT);

        preparedStatement.setString(1, client.getName());
        preparedStatement.setString(2, client.getSurname());
        preparedStatement.setString(3, client.getPatronymic());
        preparedStatement.setString(4, client.getAdres().getCountry());
        preparedStatement.setString(5, client.getAdres().getCity());
        preparedStatement.setString(6, client.getAdres().getStreet());
        preparedStatement.setString(7, client.getAdres().getHouse());
        preparedStatement.setInt(8, client.getAdres().getFlat());

//        Blob blob = (Blob) ConnectorDB.getConnection().createBlob();
  //      OutputStream outputStream = blob.setBinaryStream(1);
    //    ImageIO.write(client.getPhoto(), "jpg", outputStream);

      //  preparedStatement.setBlob(9, blob);
        preparedStatement.setString(9, client.getDiscontCard().getId());
        preparedStatement.setString(10,client.getPassword());
        if (preparedStatement.executeUpdate() != 0) {
            return true;
        }

        return false;

    }


    public Boolean update(String id,Client client) throws SQLException {
        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_UPDATE_CLIENT_BY_ID);

        preparedStatement.setString(1, client.getName());
        preparedStatement.setString(2, client.getSurname());
        preparedStatement.setString(3, client.getPatronymic());
        preparedStatement.setString(4, client.getAdres().getCountry());
        preparedStatement.setString(5, client.getAdres().getCity());
        preparedStatement.setString(6, client.getAdres().getStreet());
        preparedStatement.setString(7, client.getAdres().getHouse());
        preparedStatement.setInt(8, client.getAdres().getFlat());
        preparedStatement.setInt(9,client.getRating());
        preparedStatement.setInt(10,client.getDiscontCard().getAccumulationPercentage());
        preparedStatement.setString(11,id);


        if (preparedStatement.executeUpdate() != 0) {
            return true;
        }

        return false;


    }


    public Boolean isExist(String id) throws SQLException {

        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_ID_IN_CLIENT);
        preparedStatement.setString(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }


    private PreparedStatement getPrepareStatement(String sgl) {

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sgl);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return preparedStatement;
    }


}
