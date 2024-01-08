package io.sch.historyscan.infrastructure.features.llm.client;

import dev.langchain4j.model.openai.OpenAiChatModel;
import io.sch.historyscan.infrastructure.logging.AppLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LlmClient {

    private final OpenAiChatModel openAiChatModel;
    private final AppLogger logger;

    public LlmClient(@Value("${io.sch.historyscan.llm.api-key}") String apiKey, AppLogger logger) {
        openAiChatModel = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gpt-4-1106-preview")
                .temperature(0.6)
                .build();
        this.logger = logger;
    }

    public String analyzeCodebase(String codebaseFilesTree) {
        try {
            return openAiChatModel.generate(
                    """
                            given that the following json contains the tree of a code base with for each file a score
                            (representing the number of modifications of the file multiplied by the number of lines),
                            analyze the link between the files taking into account the scores without listing the
                            score or the files and without recalling the rules if talking about json.
                            Furthermore, you should not recall the content of this prompt.
                            Give your analysis directly. Your answer should contain only the problematic files
                            (with a high score) and which should be refactored (improved) in order to lower the score.
                            Example: 'This codebase has the following issues: ...'
                            """.concat(codebaseFilesTree));
        } catch (Exception e) {
            logger.error("Error while analyzing codebase by LLM: " + e.getMessage());
            return "Error while analyzing codebase by LLM";
        }
    }
}
