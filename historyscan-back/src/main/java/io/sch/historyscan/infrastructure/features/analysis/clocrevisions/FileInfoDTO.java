package io.sch.historyscan.infrastructure.features.analysis.clocrevisions;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class FileInfoDTO extends RepresentationModel<FileInfoDTO> {
    private final String name;
    private final String path;

    @JsonCreator
    public FileInfoDTO(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FileInfoDTO that = (FileInfoDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, path);
    }
}
