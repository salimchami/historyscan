package io.sch.historyscan.domain.contexts.analysis.network;

import io.sch.historyscan.domain.hexagonalarchitecture.DDDValueObject;

import java.util.List;

@DDDValueObject
public record NetworkNodes(List<NetworkNode> nodes) {
}
