package storage.immutability.sample;

import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.management.storage.v2019_04_01.ImmutabilityPolicy;
import com.microsoft.azure.management.storage.v2019_04_01.implementation.StorageManager;

import java.io.IOException;

public class App
{
    public static void main( String[] args ) throws IOException
    {
        //=============================================================
        // Authenticate
        //
        final String clientId = "<client-id>";
        final String tenantId = "<tenant-id>";
        final String secret = "<secret>";
        final String subscriptionId = "<subscription-id>";

        final ApplicationTokenCredentials cred = new ApplicationTokenCredentials(clientId, tenantId, secret, AzureEnvironment.AZURE);
        cred.withDefaultSubscriptionId(subscriptionId);
        //
        StorageManager storageManager = StorageManager.authenticate(cred, cred.defaultSubscriptionId());
        //
        //
        final String policyName = "<immutability-poly-name>";
        final String rgName = "<storage-account-resource-group-name>";
        final String stgName = "<storage-account-name>";
        final String containerName = "<storage-account-container-name>";

       ImmutabilityPolicy policy = storageManager.blobContainers().defineImmutabilityPolicy(policyName)
                .withExistingContainer(rgName, stgName, containerName)
                .withIfMatch(null)
                .withImmutabilityPeriodSinceCreationInDays(10)
                .create();
       //
       System.out.println(policy.id());
       System.out.println(policy.immutabilityPeriodSinceCreationInDays());
    }
}
