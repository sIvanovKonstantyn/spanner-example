package org.home.demos;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.spanner.*;
import com.google.spanner.admin.database.v1.CreateDatabaseMetadata;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class CreateDatabaseSample {
    static void createDatabase(Spanner spanner, DatabaseId id, String tableName) {
        DatabaseAdminClient dbAdminClient = spanner.getDatabaseAdminClient();

        OperationFuture<Database, CreateDatabaseMetadata> op =
                dbAdminClient.createDatabase(
                        id.getInstanceId().getInstance(),
                        id.getDatabase(),
                        Arrays.asList(
                                "CREATE TABLE " + tableName + " ("
                                        + "  TicketId   INT64 NOT NULL,"
                                        + "  Name  STRING(1024),"
                                        + "  Price   INT64,"
                                        + ") PRIMARY KEY (TicketId)"));
        try {
            // Initiate the request which returns an OperationFuture.
            Database db = op.get();
            System.out.println("Created database [" + db.getId() + "]");
        } catch (ExecutionException e) {
            // If the operation failed during execution, expose the cause.
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            // Throw when a thread is waiting, sleeping, or otherwise occupied,
            // and the thread is interrupted, either before or during the activity.
            throw SpannerExceptionFactory.propagateInterrupt(e);
        }
    }
}
