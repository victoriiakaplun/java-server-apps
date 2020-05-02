package ru.omsu.imit;

import org.junit.Test;
import ru.omsu.imit.exception.ErrorCode;
import ru.omsu.imit.rest.dto.response.FailureResponseDto;

public class CommonClientTest extends BaseClientTest{
    @Test
    public void testWrongUrl() {
        Object response = client.post(getBaseURL() + "/wrong", null, FailureResponseDto.class);
        checkFailureResponse((FailureResponseDto) response, ErrorCode.WRONG_URL);
    }

    @Test
    public void testWrongJson() {
        Object response = client.postWrongJson(getBaseURL() + "/contacts", "{ wrong: ", FailureResponseDto.class);
        checkFailureResponse((FailureResponseDto) response, ErrorCode.JSON_PARSE_EXCEPTION);
    }

    @Test
    public void testEmptyJson() {
        Object response = client.postWrongJson(getBaseURL() + "/contacts", "", FailureResponseDto.class);
        checkFailureResponse((FailureResponseDto) response, ErrorCode.NULL_REQUEST);
    }
}
