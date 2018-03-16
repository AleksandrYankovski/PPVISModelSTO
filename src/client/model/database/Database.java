package client.model.database;


import client.model.database.ClientTable;
import client.model.database.DiscontCardTable;
import client.model.database.RecordTable;
import client.model.database.Table;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;

public class Database {


    HashSet<Table> tables;
    Connection connection;


    public Database() throws SQLException {
        tables=new HashSet<>();
        connection=ConnectorDB.getConnection();
        createStartTables();
    }


    public void removeTable(Table table)
    {
        tables.remove(table);
    }

    public void addTable(Table table)
    {
        tables.add(table);
    }

    public Table getTableByName(String name)
    {
        for(Table table:tables)
            if(table.getClass().getName().equals(name))
                return table;
        return null;
    }

    public void createStartTables() throws SQLException {
        this.addTable(new ClientTable(connection));
        this.addTable(new RecordTable(connection));
        this.addTable(new DiscontCardTable(connection));
        this.addTable(new CarTable(connection));
        this.addTable(new FailureTable(connection));

    }


}
