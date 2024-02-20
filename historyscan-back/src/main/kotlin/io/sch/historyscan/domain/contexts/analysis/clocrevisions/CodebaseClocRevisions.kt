package io.sch.historyscan.domain.contexts.analysis.clocrevisions

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileInfo
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileSystemTree
import io.sch.historyscan.domain.hexagonalarchitecture.DDDAggregate

@DDDAggregate
class CodebaseClocRevisions(
    val actualFsTree: FileSystemTree, val ignoredRevisions: List<FileInfo>, val extensions: List<String>
)
