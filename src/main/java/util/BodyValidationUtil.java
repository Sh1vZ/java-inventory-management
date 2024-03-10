package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BodyValidationUtil {
    public static <T> Response validateAndBuildResponse(T entity) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(entity);

        if (!violations.isEmpty()) {
            Map<String, String> errors = new HashMap<>();
            for (ConstraintViolation<T> violation : violations) {
                String fieldName = violation.getPropertyPath().toString();
                String errorMessage = violation.getMessage();
                errors.put(fieldName, errorMessage);
            }

            ObjectMapper mapper = new ObjectMapper();
            try {
                String jsonErrors = mapper.writeValueAsString(errors);
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(jsonErrors)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            } catch (JsonProcessingException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error processing validation errors")
                        .type(MediaType.TEXT_PLAIN)
                        .build();
            }
        }

        return null;
    }
}
