package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

public record RootFolder(String value) {

    public boolean isValid() {
        return !value.isBlank()
                && !value.equals("/")
                && !value.equals("./")
                && !value.equals("\\")
                && !value.equals(".")
                && !value.equals("..")
                && !value.equals("./..")
                && !value.equals("../")
                && !value.equals("./../")
                && !value.equals("../.")
                && !value.equals("./../.")
                && !value.equals("../..");
    }
}
