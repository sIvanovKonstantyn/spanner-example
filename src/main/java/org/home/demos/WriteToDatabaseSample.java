package org.home.demos;

import com.google.cloud.Timestamp;
import com.google.cloud.spanner.DatabaseClient;
import com.google.cloud.spanner.DatabaseId;
import com.google.cloud.spanner.Mutation;
import com.google.cloud.spanner.Spanner;

import java.util.List;

public class WriteToDatabaseSample {
    static void writeToDatabase(Spanner spanner, DatabaseId databaseId, List<Mutation> data) {

        DatabaseClient dbClient = spanner.getDatabaseClient(databaseId);

        System.out.println("start writing...");
        Timestamp timestamp = dbClient.write(data);
        System.out.println("writing time: " + timestamp);
    }
}
