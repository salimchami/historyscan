package io.sch.historyscan.domain.contexts.analysis.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.sch.historyscan.common.JsonReader;

public final class NetworkNodesSerializerUtils {
    private NetworkNodesSerializerUtils() {
    }

    public static NetworkNodes serializeExpected(String expectedRootTestCase) throws JsonProcessingException {
        var expectedNetworkJson = JsonReader.toExpectedJson("analysis", expectedRootTestCase);
        var mapper = new ObjectMapper();
        var module = new SimpleModule();
        module.addDeserializer(NetworkNodes.class, new NetworkNodesDeserializer());
        mapper.registerModule(module);
        return mapper.readValue(expectedNetworkJson, NetworkNodes.class);
    }
}
