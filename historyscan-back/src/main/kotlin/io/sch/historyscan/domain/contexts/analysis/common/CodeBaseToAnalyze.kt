package io.sch.historyscan.domain.contexts.analysis.common

class CodeBaseToAnalyze private constructor(val name: String, val type: EnumAnalysisType, val rootFolder: String) {
    companion object {
        @JvmStatic
        fun of(name: String, analysisType: String, rootFolder: String?): CodeBaseToAnalyze {
            if (rootFolder.isNullOrEmpty()) {
                throw RootFolderNotFound("Root folder cannot be null or empty")
            }
            val type: EnumAnalysisType = EnumAnalysisType.fromTitle(analysisType)
                .orElseThrow { ScanTypeNotFound("Analysis type isn't found") }
            return CodeBaseToAnalyze(name, type, rootFolder)
        }
    }
}
