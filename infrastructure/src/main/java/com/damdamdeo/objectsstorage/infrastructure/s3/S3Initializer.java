package com.damdamdeo.objectsstorage.infrastructure.s3;

import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.util.Objects;

@ApplicationScoped
public class S3Initializer {

    private final S3Client s3client;
    private final String bucketName;

    public S3Initializer(final S3Client s3client,
                         @ConfigProperty(name = "bucket.name") final String bucketName) {
        this.s3client = Objects.requireNonNull(s3client);
        this.bucketName = Objects.requireNonNull(bucketName);
    }

    public void onStartup(@Observes StartupEvent startupEvent) {
        if (s3client.listBuckets().buckets().stream().map(Bucket::name).noneMatch(name -> bucketName.equals(name))) {
            s3client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());
        }
        if (!BucketVersioningStatus.ENABLED
                .equals(s3client.getBucketVersioning(GetBucketVersioningRequest
                        .builder()
                        .bucket(bucketName).build()).status())) {
            s3client.putBucketVersioning(PutBucketVersioningRequest.builder()
                    .bucket(bucketName)
                    .versioningConfiguration(
                            VersioningConfiguration.builder()
                                    .status(BucketVersioningStatus.ENABLED)
                                    .build())
                    .build());
        }

    }

}
