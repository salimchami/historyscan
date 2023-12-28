package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import io.sch.historyscan.domain.hexagonalarchitecture.HexagonalArchitectureSPI;

@HexagonalArchitectureSPI
@FunctionalInterface
public interface ActualFileSystemTree {

    FileSystemTree from(RootFolder rootFolder);
}
