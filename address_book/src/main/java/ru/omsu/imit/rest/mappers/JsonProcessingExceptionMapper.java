package ru.omsu.imit.rest.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.omsu.imit.exception.AddressBookException;
import ru.omsu.imit.exception.ErrorCode;
import ru.omsu.imit.utils.AddressBookRESTUtils;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonProcessingExceptionMapper implements
        ExceptionMapper<JsonProcessingException> {
    @Override
    public Response toResponse(JsonProcessingException exception) {
        return AddressBookRESTUtils.failureResponse(Response.Status.BAD_REQUEST,
                new AddressBookException(ErrorCode.JSON_PARSE_EXCEPTION));
    }
}
