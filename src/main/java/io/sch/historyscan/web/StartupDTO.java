package io.sch.historyscan.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sch.historyscan.domain.error.HistoryScanTechnicalException;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpMethod;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class StartupDTO extends RepresentationModel<StartupDTO> {

    private final String message;

    @JsonCreator
    public StartupDTO() throws HistoryScanTechnicalException {
        this.message = "Application is up! Please use the links below to navigate to the API endpoints.";
        try {
            addSelfLink();
        } catch (NoSuchMethodException e) {
            throw new HistoryScanTechnicalException("No method found in the controller", e);
        }

    }

    private void addSelfLink() throws NoSuchMethodException {
        add(
                linkTo(StartupController.class,
                        StartupController.class.getMethod("startup"))
                        .withSelfRel()
                        .withName("startup")
                        .withTitle(HttpMethod.GET.name())
        );
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StartupDTO that = (StartupDTO) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), message);
    }
}
