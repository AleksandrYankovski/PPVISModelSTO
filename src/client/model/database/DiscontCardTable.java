package client.model.database;

import client.model.DiscontCard;

import java.sql.*;

public class DiscontCardTable implements Table {


    private Connection connection;
    private final static String SQL_INSERT_DISCONT =
            "INSERT INTO discontCard(accumulationPercentage,id)" +
                    " VALUES(?,?)";
    private final static String SQL_GET_DISCONT_CARD = "SELECT * FROM discontCard WHERE id=? ";
    private final static String SQL_UPDATE_DISCONT_CARD = "UPDATE discontCard set accumulationPercentage=? WHERE id=?";


    public DiscontCardTable(Connection connection) throws SQLException {
        this.connection = connection;
    }


    public Boolean add(DiscontCard discontCard) throws SQLException {


        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_INSERT_DISCONT);

        preparedStatement.setInt(1, discontCard.getAccumulationPercentage());
        preparedStatement.setString(2,discontCard.getId());

        if (preparedStatement.executeUpdate() != 0)
            return true;

        return false;
    }

    public Boolean update(Integer accumulationPercentage, String id) throws SQLException {
        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_UPDATE_DISCONT_CARD);

        preparedStatement.setInt(1, accumulationPercentage);
        preparedStatement.setString(2, id);


        if (preparedStatement.executeUpdate() != 0)
            return true;

        return false;

    }


    public DiscontCard getDiscontCard(String id) {

        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_GET_DISCONT_CARD);

        DiscontCard discontCard = new DiscontCard();
        try {
            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();


            discontCard.setAccumulationPercentage(resultSet.getInt(1));

            discontCard.setId(resultSet.getString(2));

        } catch (SQLException e) {
            discontCard.setAccumulationPercentage(0);
        }

        return discontCard;
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
