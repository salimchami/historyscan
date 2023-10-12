package io.sch.historyscan.domain.contexts.analysis;

public record Weight(int value) {

    public static Weight ZERO = new Weight(0);
}
