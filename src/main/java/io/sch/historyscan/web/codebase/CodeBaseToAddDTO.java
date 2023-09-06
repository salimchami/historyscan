package io.sch.historyscan.web.codebase;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class CodeBaseToAddDTO extends RepresentationModel<CodeBaseToAddDTO> {

    private final String url;
    private final String publicKey;

    @JsonCreator
    public CodeBaseToAddDTO(@JsonProperty("url") String url,
                            @JsonProperty("public-key") String publicKey) {
        this.url = url;
        this.publicKey = publicKey;
    }

    public String getUrl() {
        return url;
    }

    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CodeBaseToAddDTO that = (CodeBaseToAddDTO) o;
        return Objects.equals(url, that.url) && Objects.equals(publicKey, that.publicKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), url, publicKey);
    }
}
