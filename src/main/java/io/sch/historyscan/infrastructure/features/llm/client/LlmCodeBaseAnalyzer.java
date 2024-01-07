package io.sch.historyscan.infrastructure.features.llm.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileSystemNode;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.llm.AnalyzeCodeBaseByLlm;
import org.springframework.stereotype.Component;

@Component
public class LlmCodeBaseAnalyzer implements AnalyzeCodeBaseByLlm {

    private final LlmClient llmClient;

    public LlmCodeBaseAnalyzer(LlmClient llmClient) {
        this.llmClient = llmClient;
    }

    @Override
    public String analyzeCodebase(FileSystemNode codebaseNodes) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            final String nodes = objectMapper.writeValueAsString(codebaseNodes);
            return llmClient.analyzeCodebase(nodes);
        } catch (JsonProcessingException e) {
            throw new UnableToSerializeCodebaseNode("", e);
        }
    }
}
