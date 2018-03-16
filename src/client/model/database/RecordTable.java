package client.model.database;

import client.model.Record;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Александр on 26.09.2017.
 */
public class RecordTable implements Table {


    private Connection connection;
    private final static String SQL_DELETE_RECORDS =
            "DELETE FROM record WHERE id=?";
    private final static String SQL_INSERT_RECORD =
            "INSERT INTO record(date,time,idClient,status,idCar,idFailure)" +
                    " VALUES(?,?,?,?,?,?)";
    private final static String SQL_GET_ALL_BY_CLIENT = "select record.date,record.time,record.status," +
            "record.id,failure.*,car.* from record inner join car" +
            " on record.idCar=car.numberStateRegistration inner join failure on " +
            "record.idFailure=failure.typeOfFailure where idClient=?  ORDER BY date,time DESC";
    private final static String SQL_GET_OVERDUE_RECORDS = "SELECT count(*) FROM record WHERE (CURDATE()>date " +
            "OR (CURDATE()=date AND CURTIME()>time) and idClient=? )"  ;
    private final static String SQL_DELETE_OVERDUE_RECORDS = "DELETE FROM record WHERE (CURDATE()>date " +
            "OR (CURDATE()=date AND CURTIME()>time) and idClient=? )";


    public RecordTable(Connection connection) throws SQLException {
       this.connection=connection;
    }

    public Integer getKolOverdueRecordsForClient(String idClient) throws SQLException {
        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_GET_OVERDUE_RECORDS);
        preparedStatement.setString(1, idClient);


        ResultSet resultSet=preparedStatement.executeQuery();

        resultSet.next();
        Integer kol=resultSet.getInt(1);

        return kol;

    }

    public Boolean deleteOverOverdueRecordsForClient(String idClient) throws SQLException {
        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_DELETE_OVERDUE_RECORDS);
        preparedStatement.setString(1, idClient);

        if (preparedStatement.executeUpdate() != 0)
            return true;

        return false;

    }

    public Boolean delete(Integer id) throws SQLException {
        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_DELETE_RECORDS);
        preparedStatement.setInt(1, id);

        if (preparedStatement.executeUpdate() != 0)
            return true;

        return false;
    }


    public Boolean add(Record record) throws SQLException {

        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_INSERT_RECORD);

        preparedStatement.setDate(1, Date.valueOf(record.getDate()));
        preparedStatement.setTime(2, Time.valueOf(record.getTime()));
        preparedStatement.setString(3, record.getClient().getPassword());
        preparedStatement.setString(4, record.getStatus());
        preparedStatement.setString(5, record.getCarModel().getNumberStateRegistration());
        preparedStatement.setString(6, record.getFailure().getTypeOfFailure());

        if (preparedStatement.executeUpdate() != 0)
            return true;

        return false;
    }

    public ArrayList<Record> getAllByIdClient(String idClient) throws SQLException {

        ArrayList<Record> arrayList = new ArrayList<>();

        PreparedStatement preparedStatement = this.getPrepareStatement(SQL_GET_ALL_BY_CLIENT);

        preparedStatement.setString(1, idClient);


        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            Record record = new Record();

            record.setDate(resultSet.getDate(1).toLocalDate());
            record.setTime(resultSet.getTime(2).toLocalTime());
            record.setStatus(resultSet.getString(3));
            record.setId(resultSet.getInt(4));
            record.getFailure().setTypeOfFailure(resultSet.getString(5));
            record.getFailure().setPrice(resultSet.getInt(6));
            record.getCarModel().setMark(resultSet.getString(7));
            record.getCarModel().setModel(resultSet.getString(8));
            record.getCarModel().setYear(resultSet.getInt(9));
            record.getCarModel().setBulk(resultSet.getDouble(10));
            record.getCarModel().setNumberStateRegistration(resultSet.getString(11));
            record.getCarModel().setColor(resultSet.getString(12));

            arrayList.add(record);

        }
        return arrayList;
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
