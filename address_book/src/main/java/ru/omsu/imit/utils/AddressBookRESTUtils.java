package ru.omsu.imit.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang.StringUtils;
import ru.omsu.imit.rest.dto.response.FailureResponseDto;
import ru.omsu.imit.exception.AddressBookException;
import ru.omsu.imit.exception.ErrorCode;

import javax.ws.rs.core.Response;
import java.time.LocalDate;

public class AddressBookRESTUtils {

    private static final Gson GSON = new GsonBuilder().create();

    public static <T> T getClassInstanceFromJson(Gson gson, String json, Class<T> clazz) throws AddressBookException {
        if (StringUtils.isEmpty(json)) {
            throw new AddressBookException(ErrorCode.NULL_REQUEST);
        }
        try {
            return gson.fromJson(json, clazz);
        } catch (JsonSyntaxException ex) {
            throw new AddressBookException(ErrorCode.JSON_PARSE_EXCEPTION, json);
        }
    }

    public static Response failureResponse(Response.Status status, AddressBookException e) {
        return Response
                .status(status)
                .entity(GSON.toJson(new FailureResponseDto(e.getErrorCode(), e.getMessage()))).build();
    }

    public static Response failureResponse(AddressBookException e) {
        return failureResponse(Response.Status.BAD_REQUEST, e);
    }
}
