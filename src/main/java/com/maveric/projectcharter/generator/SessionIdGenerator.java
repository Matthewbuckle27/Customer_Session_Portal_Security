package com.maveric.projectcharter.generator;

import com.maveric.projectcharter.config.Constants;
import org.hibernate.Session;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.jdbc.Work;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        Session currentSession = (Session) session;

        final String[] generatedId = new String[1];

        currentSession.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                try (PreparedStatement preparedStatement = connection.prepareStatement(Constants.QUERY_SESSION)) {
                    ResultSet rs = preparedStatement.executeQuery();
                    if (rs.next()) {
                        int nextValue = rs.getInt(1) + 1;
                        generatedId[0] = Constants.PREFIX_SESSION + String.format(Constants.FORMAT, nextValue);
                    } else {
                        generatedId[0] = Constants.PREFIX_SESSION + Constants.PREFIX_FORMAT;
                    }
                }
            }
        });

        return generatedId[0];
    }

}