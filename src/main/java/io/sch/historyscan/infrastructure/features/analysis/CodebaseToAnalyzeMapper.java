package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseToAnalyze;
import org.springframework.stereotype.Component;

@Component
public class CodebaseToAnalyzeMapper {
    public CodeBaseToAnalyze webToDomain(CodeBaseToAnalyzeDTO codeBaseToAnalyzeDTO) {
        return new CodeBaseToAnalyze(
                codeBaseToAnalyzeDTO.getName(),
                codeBaseToAnalyzeDTO.getType(),
                codeBaseToAnalyzeDTO.getBaseFolder());
    }
}
