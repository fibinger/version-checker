package pl.fibinger.versionchecker.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationMapper implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException exception) {
        String message = exception.getMessage();
        return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }
}
