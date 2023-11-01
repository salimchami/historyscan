package io.sch.historyscan.infrastructure.features.analysis;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.Objects;

public class CodeBaseHistoryCommitInfoDTO extends RepresentationModel<CodeBaseHistoryCommitInfoDTO> {
    private final String hash;
    private final String author;
    private final LocalDateTime date;
    private final String shortMessage;

    @JsonCreator
    public CodeBaseHistoryCommitInfoDTO(String hash, String author, LocalDateTime date, String shortMessage) {
        this.hash = hash;
        this.author = author;
        this.date = date;
        this.shortMessage = shortMessage;
    }

    public String getHash() {
        return hash;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getShortMessage() {
        return shortMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CodeBaseHistoryCommitInfoDTO that = (CodeBaseHistoryCommitInfoDTO) o;
        return Objects.equals(hash, that.hash) && Objects.equals(author, that.author) && Objects.equals(date, that.date) && Objects.equals(shortMessage, that.shortMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), hash, author, date, shortMessage);
    }
}
