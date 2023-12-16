package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseToAnalyze;
import io.sch.historyscan.domain.contexts.analysis.common.EnumAnalysisType;
import io.sch.historyscan.infrastructure.features.analysis.dto.CodeBaseToAnalyzeDTO;
import org.springframework.stereotype.Component;

@Component
public class CodebaseToAnalyzeMapper {
    public CodeBaseToAnalyze webToDomain(CodeBaseToAnalyzeDTO codeBaseToAnalyzeDTO) {
        return new CodeBaseToAnalyze(
                codeBaseToAnalyzeDTO.getName(),
                EnumAnalysisType.fromTitle(codeBaseToAnalyzeDTO.getType()).orElseThrow(() -> new RuntimeException("Analysis type not found")),
                codeBaseToAnalyzeDTO.getRootFolder());
    }
}
