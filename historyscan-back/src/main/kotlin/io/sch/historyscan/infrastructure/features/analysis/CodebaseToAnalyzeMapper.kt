package io.sch.historyscan.infrastructure.features.analysis

import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseToAnalyze
import io.sch.historyscan.infrastructure.features.analysis.dto.CodeBaseToAnalyzeDTO
import org.springframework.stereotype.Component

@Component
class CodebaseToAnalyzeMapper {
    fun webToDomain(codeBaseToAnalyzeDTO: CodeBaseToAnalyzeDTO): CodeBaseToAnalyze {
        return CodeBaseToAnalyze.of(
                codeBaseToAnalyzeDTO.name,
                codeBaseToAnalyzeDTO.type,
                codeBaseToAnalyzeDTO.rootFolder)
    }
}
