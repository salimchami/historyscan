package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.sch.historyscan.common.JsonReader;

public final class FileSystemNodeSerializerUtils {
    private FileSystemNodeSerializerUtils() {
    }

    public static FileSystemNode serializeExpectedRoot(String expectedRootTestCase) throws JsonProcessingException {
        var expectedRootJson = JsonReader.toExpectedJson("filesystemtree", expectedRootTestCase);
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(FileSystemNode.class, new FileSystemNodeDeserializer());
        mapper.registerModule(module);
        return mapper.readValue(expectedRootJson, FileSystemNode.class);
    }
}
