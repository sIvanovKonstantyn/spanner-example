package org.home.demos;

import com.google.cloud.spanner.DatabaseId;
import com.google.cloud.spanner.Mutation;
import com.google.cloud.spanner.Spanner;
import com.google.cloud.spanner.SpannerOptions;

import java.util.List;

public class SpannerExample {
    public static void main(String[] args) {
        SpannerOptions options = SpannerOptions.newBuilder()
                .setProjectId("example-project")
                .setEmulatorHost("localhost:9010")
                .build();

        Spanner spanner = options.getService();
        try {
            String instanceId = "cloudspanner-example";
            String databaseName = "demo";
            String tableName = "Tickets";

            /*
            *************************CreateInstanceSample*************************************
            * */

            CreateInstanceSample.createInstance(spanner, options.getProjectId(), instanceId);
            DatabaseId databaseId = DatabaseId.of(
                    options.getProjectId(),
                    instanceId,
                    databaseName
            );

            /*
             *************************CreateDatabaseSample*************************************
             * */
            CreateDatabaseSample.createDatabase(spanner, databaseId, tableName);

            /*
             *************************ReadFromDatabaseSample*************************************
             * */
            ReadFromDatabaseSample.readFromDatabase(spanner, databaseId, tableName);

            /*
             *************************WriteToDatabaseSample*************************************
             * */
            List<Mutation> data = List.of(
                    Mutation.newInsertBuilder(tableName)
                            .set("TicketId").to(1L)
                            .set("Name").to("ticket1")
                            .set("Price").to(100L)
                            .build()
            );
            WriteToDatabaseSample.writeToDatabase(spanner, databaseId, data);

        } finally {
            spanner.close();
        }
    }
}
