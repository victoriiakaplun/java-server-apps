package ru.omsu.imit.rest.mappers;

import ru.omsu.imit.exception.AddressBookException;
import ru.omsu.imit.exception.ErrorCode;
import ru.omsu.imit.utils.AddressBookRESTUtils;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WrongURLExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        return AddressBookRESTUtils.failureResponse(Response.Status.NOT_FOUND, new AddressBookException(ErrorCode.WRONG_URL));
    }
}
