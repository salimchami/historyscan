package io.sch.historyscan.infrastructure.config.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class ApiError extends RepresentationModel<ApiError> implements Serializable {

    private final LocalDateTime timeStamp;
    private final String message;
    private final String url;

    @JsonCreator
    public ApiError(String message, String url) {
        this.timeStamp = LocalDateTime.now();
        this.message = message;
        this.url = url;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ApiError apiError = (ApiError) o;
        return Objects.equals(timeStamp, apiError.timeStamp)
                && Objects.equals(message, apiError.message)
                && Objects.equals(url, apiError.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), timeStamp, message, url);
    }
}
