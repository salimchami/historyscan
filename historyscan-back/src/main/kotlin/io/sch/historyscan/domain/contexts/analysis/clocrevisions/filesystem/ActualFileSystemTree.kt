package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem

import io.sch.historyscan.domain.hexagonalarchitecture.HexagonalArchitectureSPI

@HexagonalArchitectureSPI
fun interface ActualFileSystemTree {
    fun from(rootFolder: RootFolder): FileSystemTree
}
