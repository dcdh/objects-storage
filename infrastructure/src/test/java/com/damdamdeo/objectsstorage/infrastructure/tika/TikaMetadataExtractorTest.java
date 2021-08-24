package com.damdamdeo.objectsstorage.infrastructure.tika;

import com.damdamdeo.objectsstorage.domain.Metadata;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

@QuarkusTest
public class TikaMetadataExtractorTest {

    @Inject
    TikaMetadataExtractor tikaMetadataExtractor;

    @Test
    public void should_parse_jpeg_image() throws Exception {
        // Given
        final byte[] givenContent = readFileFromResource("given/2021-08-19-211905.jpg");

        // When
        final Metadata metadata = tikaMetadataExtractor.extract(givenContent);

        // Then
        assertThat(metadata.all()).contains(
                entry("date", "2021-08-19T23:19:05"),
                entry("Number of Tables", "4 Huffman tables"),
                entry("Compression Type", "Baseline"),
                entry("Data Precision", "8 bits"),
                entry("Number of Components", "3"),
                entry("tiff:ImageLength", "360"),
                entry("Component 2", "Cb component: Quantization table 1, Sampling factors 1 horiz/1 vert"),
                entry("Thumbnail Height Pixels", "0"),
                entry("Exif IFD0:Model", "Integrated Camera: Integrated C"),
                entry("dcterms:created", "2021-08-19T23:19:05"),
                entry("Component 1", "Y component: Quantization table 0, Sampling factors 2 horiz/1 vert"),
                entry("dcterms:modified", "2021-08-19T23:19:05"),
                entry("Last-Modified", "2021-08-19T23:19:05"),
                entry("X Resolution", "1 dot"),
                entry("Last-Save-Date", "2021-08-19T23:19:05"),
                entry("File Size", "34212 bytes"),
                entry("Exif SubIFD:Exif Version", "2.30"),
                entry("Component 3", "Cr component: Quantization table 1, Sampling factors 1 horiz/1 vert"),
                entry("meta:save-date", "2021-08-19T23:19:05"),
                entry("modified", "2021-08-19T23:19:05"),
//                entry("File Name", "apache-tika-15134912598380710981.tmp"),
                entry("tiff:BitsPerSample", "8"),
                entry("Content-Type", "image/jpeg"),
                entry("Exif SubIFD:Date/Time Original", "2021:08:19 21:19:05"),
                entry("X-Parsed-By", "org.apache.tika.parser.CompositeParser,org.apache.tika.parser.jpeg.JpegParser"),
                entry("Resolution Units", "none"),
//                entry("File Modified Date", "jeu. août 19 21:52:17 +02:00 2021"),
                entry("meta:creation-date", "2021-08-19T23:19:05"),
                entry("exif:DateTimeOriginal", "2021-08-19T23:19:05"),
                entry("Creation-Date", "2021-08-19T23:19:05"),
                entry("Image Height", "360 pixels"),
                entry("Thumbnail Width Pixels", "0"),
                entry("Exif SubIFD:FlashPix Version", "1.00"),
                entry("Image Width", "640 pixels"),
                entry("tiff:Software", "Cheese 3.28.0"),
                entry("tiff:Model", "Integrated Camera: Integrated C"),
                entry("XMP Value Count", "5"),
                entry("Exif IFD0:Software", "Cheese 3.28.0"),
                entry("Exif IFD0:Date/Time", "2021:08:19 21:19:05"),
                entry("tiff:ImageWidth", "640"),
                entry("Y Resolution", "1 dot"));
        assertThat(metadata.all()).containsKeys("File Name", "File Modified Date");
        assertThat(metadata.contentType()).isEqualTo("image/jpeg");
        assertThat(metadata.contentLength()).isNull();
    }

    private byte[] readFileFromResource(final String fileName) throws Exception {
        final ClassLoader classLoader = getClass().getClassLoader();
        final URL resource = classLoader.getResource(fileName);
        final File file = new File(resource.toURI());
        return Files.readAllBytes(file.toPath());
    }
}
