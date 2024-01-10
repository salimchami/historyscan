package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

public class RootFolder {

    private final String value;
    private final String codebaseName;

    private RootFolder(String value, String codebaseName) {
        this.value = value;
        this.codebaseName = codebaseName;
    }

    public static RootFolder of(String rootFolder, String codeBaseName) {
        var actualValue = formattedValue(rootFolder, codeBaseName);
        return new RootFolder(actualValue.replace("\\", "/"),
                codeBaseName.replace("\\", "/"));
    }

    private static String formattedValue(String value, String codebaseName) {
        if (value.isEmpty()
            || value.equals("/")
            || value.equals("\\")
            || value.equals(codebaseName)
            || value.equals("\\".concat(codebaseName))
            || value.equals("/".concat(codebaseName))
        ) {
            return codebaseName;
        }
        return value
                .replace("/", "\\")
                .replace("\\\\", "\\");
    }

    public String getValue() {
        return value;
    }

    public String getCodebaseName() {
        return codebaseName;
    }
}
