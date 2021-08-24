package com.damdamdeo.objectsstorage.infrastructure.s3;

import com.damdamdeo.objectsstorage.domain.*;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class S3StoredObjectRepositoryTest {

    private static final String VERSION_ID_PATTERN = "[0-9]+";

    @Inject
    S3StoredObjectRepository s3StoredObjectRepository;

    public static final class TestMetadata implements Metadata {

        @Override
        public Map<String, String> all() {
            final Map<String, String> metadata = new HashMap<>(50);
            metadata.put("date", "2021-08-19T23:19:05");
            metadata.put("Number of Tables", "4 Huffman tables");
            metadata.put("Compression Type", "Baseline");
            metadata.put("Data Precision", "8 bits");
            metadata.put("Number of Components", "3");
            metadata.put("tiff:ImageLength", "360");
            metadata.put("Component 2", "Cb component: Quantization table 1, Sampling factors 1 horiz/1 vert");
            metadata.put("Thumbnail Height Pixels", "0");
            metadata.put("Exif IFD0:Model", "Integrated Camera: Integrated C");
            metadata.put("dcterms:created", "2021-08-19T23:19:05");
            metadata.put("Component 1", "Y component: Quantization table 0, Sampling factors 2 horiz/1 vert");
            metadata.put("dcterms:modified", "2021-08-19T23:19:05");
            metadata.put("Last-Modified", "2021-08-19T23:19:05");
            metadata.put("X Resolution", "1 dot");
            metadata.put("Last-Save-Date", "2021-08-19T23:19:05");
            metadata.put("File Size", "34212 bytes");
            metadata.put("Exif SubIFD:Exif Version", "2.30");
            metadata.put("Component 3", "Cr component: Quantization table 1, Sampling factors 1 horiz/1 vert");
            metadata.put("meta:save-date", "2021-08-19T23:19:05");
            metadata.put("modified", "2021-08-19T23:19:05");
            metadata.put("File Name", "apache-tika-15134912598380710981.tmp");
            metadata.put("tiff:BitsPerSample", "8");
            metadata.put("Content-Type", "image/jpeg");
            metadata.put("Exif SubIFD:Date/Time Original", "2021:08:19 21:19:05");
            metadata.put("X-Parsed-By", "org.apache.tika.parser.CompositeParser,org.apache.tika.parser.jpeg.JpegParser");
            metadata.put("Resolution Units", "none");
            metadata.put("File Modified Date", "jeu. août 19 21:52:17 +02:00 2021");
            metadata.put("meta:creation-date", "2021-08-19T23:19:05");
            metadata.put("exif:DateTimeOriginal", "2021-08-19T23:19:05");
            metadata.put("Creation-Date", "2021-08-19T23:19:05");
            metadata.put("Image Height", "360 pixels");
            metadata.put("Thumbnail Width Pixels", "0");
            metadata.put("Exif SubIFD:FlashPix Version", "1.00");
            metadata.put("Image Width", "640 pixels");
            metadata.put("tiff:Software", "Cheese 3.28.0");
            metadata.put("tiff:Model", "Integrated Camera: Integrated C");
            metadata.put("XMP Value Count", "5");
            metadata.put("Exif IFD0:Software", "Cheese 3.28.0");
            metadata.put("Exif IFD0:Date/Time", "2021:08:19 21:19:05");
            metadata.put("tiff:ImageWidth", "640");
            metadata.put("Y Resolution", "1 dot");
            return metadata;
        }

        @Override
        public String contentType() {
            return "image/jpeg";
        }

        @Override
        public Long contentLength() {
            return null;
        }
    }

    @Test
    @Order(0)
    public void should_store_image() throws Exception {
        // Given
        final Metadata givenMetadata = new TestMetadata();
        final ObjectLocation givenObjectLocation = new ObjectLocation("/pictures/2021-08-19-211905.jpg");
        final byte[] givenContent = readFileFromResource("given/2021-08-19-211905.jpg");

        // When
        final ObjectCreated objectCreated = s3StoredObjectRepository.store(new CreateObject(
                givenMetadata,
                givenObjectLocation,
                givenContent
        ));

        // Then
        assertThat(objectCreated.getObjectLocation()).isEqualTo(new ObjectLocation("/pictures/2021-08-19-211905.jpg"));
        assertThat(objectCreated.getVersionId()).matches(VERSION_ID_PATTERN);
    }

    @Test
    @Order(1)
    public void should_get_image() throws Exception {
        // Given (previous tests)
        final ObjectLocation givenObjectLocation = new ObjectLocation("/pictures/2021-08-19-211905.jpg");

        // When
        final StoredObject storedObject = s3StoredObjectRepository.get(givenObjectLocation);

        // Then
        assertThat(storedObject.metadata().all()).contains(
                entry("date", "2021-08-19T23:19:05"),
                entry("number-of-tables", "4 Huffman tables"),
                entry("compression-type", "Baseline"),
                entry("data-precision", "8 bits"),
                entry("number-of-components", "3"),
                entry("tiff-image-length", "360"),
                entry("component-2", "Cb component: Quantization table 1, Sampling factors 1 horiz/1 vert"),
                entry("thumbnail-height-pixels", "0"),
                entry("exif-ifd0-model", "Integrated Camera: Integrated C"),
                entry("dcterms-created", "2021-08-19T23:19:05"),
                entry("component-1", "Y component: Quantization table 0, Sampling factors 2 horiz/1 vert"),
                entry("dcterms-modified", "2021-08-19T23:19:05"),
                entry("last-modified", "2021-08-19T23:19:05"),
                entry("x-resolution", "1 dot"),
                entry("last-save-date", "2021-08-19T23:19:05"),
                entry("file-size", "34212 bytes"),
                entry("exif-sub-ifd-exif-version", "2.30"),
                entry("component-3", "Cr component: Quantization table 1, Sampling factors 1 horiz/1 vert"),
                entry("meta-save-date", "2021-08-19T23:19:05"),
                entry("modified", "2021-08-19T23:19:05"),
//                entry("File Name", "apache-tika-15134912598380710981.tmp"),
                entry("tiff-bits-per-sample", "8"),
                entry("content-type", "image/jpeg"),
                entry("exif-sub-ifd-date-time-original", "2021:08:19 21:19:05"),
                entry("x-parsed-by", "org.apache.tika.parser.CompositeParser,org.apache.tika.parser.jpeg.JpegParser"),
                entry("resolution-units", "none"),
//                entry("File Modified Date", "jeu. août 19 21:52:17 +02:00 2021"),
                entry("meta-creation-date", "2021-08-19T23:19:05"),
                entry("exif-date-time-original", "2021-08-19T23:19:05"),
                entry("creation-date", "2021-08-19T23:19:05"),
                entry("image-height", "360 pixels"),
                entry("thumbnail-width-pixels", "0"),
                entry("exif-sub-ifd-flash-pix-version", "1.00"),
                entry("image-width", "640 pixels"),
                entry("tiff-software", "Cheese 3.28.0"),
                entry("tiff-model", "Integrated Camera: Integrated C"),
                entry("xmp-value-count", "5"),
                entry("exif-ifd0-software", "Cheese 3.28.0"),
                entry("exif-ifd0-date-time", "2021:08:19 21:19:05"),
                entry("tiff-image-width", "640"),
                entry("y-resolution", "1 dot"));
        assertThat(storedObject.metadata().all()).containsKeys("date", "last-modified");
        assertThat(storedObject.metadata().contentType()).isEqualTo("image/jpeg");
        assertThat(storedObject.metadata().contentLength()).isEqualTo(34212L);
        assertThat(storedObject.objectKey()).isEqualTo(new ObjectLocation("/pictures/2021-08-19-211905.jpg"));
//        assertThat(storedObject.content()).isEqualTo(new BufferedOutputStream())
        assertThat(storedObject.versionId()).matches(VERSION_ID_PATTERN);
    }

    private byte[] readFileFromResource(final String fileName) throws Exception {
        final ClassLoader classLoader = getClass().getClassLoader();
        final URL resource = classLoader.getResource(fileName);
        final File file = new File(resource.toURI());
        return Files.readAllBytes(file.toPath());
    }

}
