package client.model.database;

import client.model.Car;

import java.sql.*;

public class CarTable implements Table {


    private Connection connection;
    private final static String SQL_INSERT_CAR =
            "INSERT INTO car(mark,model,year,bulk,numberStateRegistration,color)" +
                    " VALUES(?,?,?,?,?,?)";



    public CarTable(Connection connection) throws SQLException {
        this.connection = connection;
    }


    public Boolean add(Car car) throws SQLException {

        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_INSERT_CAR);

        preparedStatement.setString(1, car.getMark());
        preparedStatement.setString(2, car.getModel());
        preparedStatement.setInt(3, car.getYear());
        preparedStatement.setDouble(4, car.getBulk());
        preparedStatement.setString(5, car.getNumberStateRegistration());
        preparedStatement.setString(6, car.getColor());

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
