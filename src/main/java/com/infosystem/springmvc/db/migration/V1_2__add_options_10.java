package com.infosystem.springmvc.db.migration;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class V1_2__add_options_10 implements JdbcMigration {

    public void migrate(Connection connection) throws Exception {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO tariff_options(name, price,costofadd) VALUES (?,?,?);");
        for (int i = 0; i < 11; i++) {
            statement.setString(1,"Option?"+(i+1));
            statement.setDouble(2,(double)(i+1));
            statement.setDouble(3,(double)(i*2+1));
            statement.addBatch();
        }
        try {
            statement.executeBatch();
        } finally {
            statement.close();
        }
    }

}