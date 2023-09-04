package io.sch.historyscan.common;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class JsonReader {

    private JsonReader() {
        // private constructor due to utils class.
    }

    private static String toJson(String baseFolder, String folder, String testCase) {
        String jsonFile = String.format("/%s/%s/%s.json", baseFolder, folder, testCase);
        URL jsonURL = JsonReader.class.getResource(jsonFile);
        try (Stream<String> lines = Files.lines(Paths.get(jsonURL.toURI()))) {
            return lines.collect(Collectors.joining(""));
        } catch (Exception e) {
            throw new IllegalArgumentException("No JSON file found : " + jsonFile, e);
        }
    }

    public static String toExpectedJson(String folder, String testCase) {
        return toJson("expected-data", folder, testCase);
    }

    public static String toRequestedJson(String folder, String testCase) {
        return toJson("requested-data", folder, testCase);
    }

    public static String toExpectedJson(String prefix, String folder, String testCase) {
        return toJson(prefix.concat("/").concat("expected-data"), folder, testCase);
    }

    public static String toRequestedJson(String prefix, String folder, String testCase) {
        return toJson(prefix.concat("/").concat("requested-data"), folder, testCase);
    }
}
