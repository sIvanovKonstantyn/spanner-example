package org.home.demos;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.spanner.*;
import com.google.spanner.admin.instance.v1.CreateInstanceMetadata;

import java.util.concurrent.ExecutionException;

public class CreateInstanceSample {
    static void createInstance(Spanner spanner, String projectId, String instanceId) {
        InstanceAdminClient instanceAdminClient = spanner.getInstanceAdminClient();

        // Set Instance configuration.
        String configId = "regional-us-central1";
        int nodeCount = 2;
        String displayName = "Custom title";

        // Create an InstanceInfo object that will be used to create the instance.
        InstanceInfo instanceInfo =
                InstanceInfo.newBuilder(InstanceId.of(projectId, instanceId))
                        .setInstanceConfigId(InstanceConfigId.of(projectId, configId))
                        .setNodeCount(nodeCount)
                        .setDisplayName(displayName)
                        .build();

        OperationFuture<Instance, CreateInstanceMetadata> operation =
                instanceAdminClient.createInstance(instanceInfo);
        try {
            // Wait for the createInstance operation to finish.
            Instance instance = operation.get();
            System.out.printf("Instance %s was successfully created%n", instance.getId());
        } catch (ExecutionException e) {
            System.out.printf(
                    "Error: Creating instance %s failed with error message %s%n",
                    instanceInfo.getId(), e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Error: Waiting for createInstance operation to finish was interrupted");
        }
    }
}
