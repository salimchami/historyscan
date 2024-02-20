package io.sch.historyscan.common

import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

object JsonReader {
    private fun toJson(baseFolder: String, folder: String, testCase: String): String {
        val jsonFile = String.format("/%s/%s/%s.json", baseFolder, folder, testCase)
        val jsonURL = JsonReader::class.java.getResource(jsonFile)
            ?: throw IllegalArgumentException("No JSON file found : $jsonFile")
        try {
            Files.lines(Paths.get(jsonURL.toURI())).use { lines ->
                return lines.collect(Collectors.joining(""))
            }
        } catch (e: Exception) {
            throw IllegalArgumentException("No JSON file found : $jsonFile", e)
        }
    }

    fun toExpectedJson(folder: String, testCase: String): String {
        return toJson("expected-data", folder, testCase)
    }

    fun toRequestedJson(folder: String, testCase: String): String {
        return toJson("requested-data", folder, testCase)
    }

    fun toExpectedJson(prefix: String, folder: String, testCase: String): String {
        return toJson("$prefix/expected-data", folder, testCase)
    }

    fun toRequestedJson(prefix: String, folder: String, testCase: String): String {
        return toJson("$prefix/requested-data", folder, testCase)
    }
}
