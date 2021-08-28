package com.damdamdeo.objectsstorage.infrastructure.s3;

import com.damdamdeo.objectsstorage.domain.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.opentracing.Traced;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import javax.enterprise.context.ApplicationScoped;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class S3StoredObjectRepository implements StoredObjectRepository {

    private final S3Client s3Client;
    private final String bucketName;

    public S3StoredObjectRepository(final S3Client s3Client,
                                    @ConfigProperty(name = "bucket.name") final String bucketName) {
        this.s3Client = Objects.requireNonNull(s3Client);
        this.bucketName = Objects.requireNonNull(bucketName);
    }

    @Override
    @Traced
    public ObjectCreated store(final CreateObject createObject) throws StoredObjectRepositoryException {
        try {
            final PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(createObject.objectKey().fullPath())
                    .contentLength(createObject.metadata().contentLength())
                    .contentType(createObject.metadata().contentType())
                    .metadata(fixKeyNaming(createObject.metadata().all()))
                    .build();
            final PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest,
                    RequestBody.fromBytes(createObject.content()));
            return new ObjectCreated(
                    createObject.objectKey(),
                    putObjectResponse.versionId()
            );
        } catch (final SdkException exception) {
            exception.printStackTrace();
            throw new StoredObjectRepositoryException(createObject.objectKey());
        }
    }

    @Override
    @Traced
    public StoredObject get(final ObjectLocation objectLocation) throws UnknownStoredObjectException, StoredObjectRepositoryException {
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName)
                    .key(objectLocation.fullPath())
                    .build();
            final GetObjectResponse getObjectResponse = s3Client.getObject(getObjectRequest, ResponseTransformer.toOutputStream(baos));
            final Metadata metadata = new S3Metadata(getObjectResponse);

            return new StoredObject(
                    metadata,
                    objectLocation,
                    baos,
                    getObjectResponse.versionId());
        } catch (final NoSuchKeyException noSuchKeyException) {
            throw new UnknownStoredObjectException(objectLocation);
        } catch (final SdkException sdkException) {
            sdkException.printStackTrace();
            throw new StoredObjectRepositoryException(objectLocation);
        }
    }

    // https://docs.aws.amazon.com/AmazonS3/latest/userguide/UsingMetadata.html
    private Map<String, String> fixKeyNaming(final Map<String, String> metadata) {
        return metadata
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> keepOnlyCompatibleMetadataNameCharacters(
                                toKebabCase(e.getKey())),
                        e -> e.getValue()
                ));
    }

    private String toKebabCase(final String stringToConvert) {
        return stringToConvert
                .replaceAll("([A-Z]+)([A-Z][a-z])", "$1-$2")
                .replaceAll("([a-z])([A-Z])", "$1-$2");
    }

    private String keepOnlyCompatibleMetadataNameCharacters(final String str) {
        return str.replaceAll("[^a-zA-Z0-9\\-\\_]","-");
    }
}
