package client.model.database;

import client.model.Car;
import client.model.Failure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FailureTable implements Table {



    private Connection connection;
    private final static  String SQL_INSERT_FAILURE =
            "INSERT INTO failure(typeOfFailure,price)" +
            " VALUES(?,?)";

    public FailureTable(Connection connection) throws SQLException {
        this.connection=connection;
    }


    public Boolean add(Failure failure) throws SQLException {

        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_INSERT_FAILURE);

        preparedStatement.setString(1, failure.getTypeOfFailure());
        preparedStatement.setInt(2, failure.getPrice());

        if (preparedStatement.executeUpdate() != 0)
            return true;

        return false;


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
