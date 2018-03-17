package nl.gertontenham.rest.resource.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException e) {
        Map<String, String> map = new HashMap<>();
        for (ConstraintViolation<?> cv : ((ConstraintViolationException) e).getConstraintViolations()) {
            map.put(cv.getPropertyPath().toString(), cv.getMessage());
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonResult = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(map);

            return Response
                    .status(Response.Status.BAD_REQUEST.getStatusCode())
                    .type(MediaType.APPLICATION_JSON)
                    .entity(jsonResult)
                    .build();
        } catch (JsonProcessingException err) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode())
                    .type(MediaType.TEXT_PLAIN).entity(err.getMessage()).build();
        }
    }
}
