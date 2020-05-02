package ru.omsu.imit.rest.mappers;

import ru.omsu.imit.exception.AddressBookException;
import ru.omsu.imit.exception.ErrorCode;
import ru.omsu.imit.utils.AddressBookRESTUtils;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MethodNotAllowedExceptionMapper implements ExceptionMapper<NotAllowedException> {

    @Override
    public Response toResponse(NotAllowedException exception) {
        return AddressBookRESTUtils.failureResponse(Response.Status.METHOD_NOT_ALLOWED,
                new AddressBookException(ErrorCode.METHOD_NOT_ALLOWED));
    }
}
