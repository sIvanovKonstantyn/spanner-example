package org.home.demos;

import com.google.cloud.spanner.*;

public class ReadFromDatabaseSample {
    static void readFromDatabase(Spanner spanner, DatabaseId databaseId, String tableName) {

        DatabaseClient dbClient = spanner.getDatabaseClient(databaseId);
        try (ResultSet resultSet =
                     dbClient
                             .singleUse() // Execute a single read or query against Cloud Spanner.
                             .executeQuery(Statement.of("SELECT * FROM " + tableName))) {
            while (resultSet.next()) {
                System.out.printf(
                        "%s %s %s\n",
                        resultSet.getLong("TicketId"),
                        resultSet.getString("Name"),
                        resultSet.getLong("Price")
                );
            }
        }
    }
}
