package io.sch.historyscan.domain.contexts.analysis.common

import java.util.*

enum class EnumAnalysisType(@JvmField val title: String) {
    COMMITS_SCAN("history"),
    CLOC_REVISIONS("cloc-revisions"),
    NETWORK("network");

    companion object {
        fun fromTitle(text: String?): Optional<EnumAnalysisType> {
            return Arrays.stream(entries.toTypedArray())
                    .filter { b: EnumAnalysisType -> b.title.equals(text, ignoreCase = true) }
                    .findFirst()
        }
    }
}
