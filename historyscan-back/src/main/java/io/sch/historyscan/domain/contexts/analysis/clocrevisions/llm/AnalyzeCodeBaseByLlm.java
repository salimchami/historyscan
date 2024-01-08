package io.sch.historyscan.domain.contexts.analysis.clocrevisions.llm;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileSystemNode;

public interface AnalyzeCodeBaseByLlm {
    String analyzeCodebase(FileSystemNode codebaseNodes);
}
