package com.lowagie.text.html;

import com.lowagie.text.Chunk;
import com.lowagie.text.Element;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class EmbeddedImageTest
{

    @Test
    void processHtmlWithEmbeddedImage() throws Exception
    {
        String html = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("base64-image.html").getPath())).stream()
                .collect(Collectors.joining());
        final StringReader reader = new StringReader(html);
        final Map<String, Object> interfaceProps = new HashMap<>();
        final List<Element> elements = HTMLWorker.parseToList(reader, new StyleSheet(), interfaceProps);

        assertThat(elements).hasSize(13);
        assertThat(elements.get(0).type()).isEqualTo(Element.PARAGRAPH);
        assertThat(elements.get(0).getChunks()).hasSize(1);
        assertThat(elements.get(0).getChunks().get(0).type()).isEqualTo(Element.CHUNK);
        Chunk chunk = (Chunk) elements.get(0).getChunks().get(0);
        assertThat(chunk.getImage().isJpeg()).isTrue();
    }

}
