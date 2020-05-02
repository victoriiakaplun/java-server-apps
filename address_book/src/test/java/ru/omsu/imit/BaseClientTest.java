package ru.omsu.imit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsu.imit.client.AddressBookClient;
import ru.omsu.imit.dao.CommonDao;
import ru.omsu.imit.daoimpl.CommonDaoImpl;
import ru.omsu.imit.exception.AddressBookException;
import ru.omsu.imit.exception.ErrorCode;
import ru.omsu.imit.rest.dto.request.AddContactToSectionRequestDto;
import ru.omsu.imit.rest.dto.request.ContactWithAddressRequestDto;
import ru.omsu.imit.rest.dto.request.IdRequestDto;
import ru.omsu.imit.rest.dto.request.SectionRequestDto;
import ru.omsu.imit.rest.dto.response.*;
import ru.omsu.imit.server.AddressBookServer;
import ru.omsu.imit.server.config.Settings;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BaseClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseClientTest.class);

    protected static AddressBookClient client = new AddressBookClient();
    private static String baseURL;
    protected CommonDao commonDao = new CommonDaoImpl();

    private static void initialize() {
        String hostName = null;
        try {
            hostName = InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
            LOGGER.debug("Can't determine my own host name", e);
        }
        baseURL = "http://" + hostName + ":" + Settings.getRestHTTPPort() + "/api";
    }

    @BeforeClass
    public static void startServer() {
        initialize();
        AddressBookServer.createServer();
    }


    @AfterClass
    public static void stopServer() {
        AddressBookServer.stopServer();
    }

    @Before
    public void clearDataBase() throws AddressBookException {
        commonDao.clear();
    }

    public static String getBaseURL() {
        return baseURL;
    }

    protected void checkFailureResponse(Object response, ErrorCode expectedStatus) {
        assertTrue(response instanceof FailureResponseDto);
        FailureResponseDto failureResponseObject = (FailureResponseDto) response;
        assertEquals(expectedStatus, failureResponseObject.getErrorCode());
    }

    protected SectionResponseDto addSection(String title, ErrorCode expectedStatus) {
        SectionRequestDto request = new SectionRequestDto(title);
        Object response = client.post(baseURL + "/sections/", request, SectionResponseDto.class);
        if( response instanceof SectionResponseDto) {
            assertEquals(ErrorCode.SUCCESS, expectedStatus);
            SectionResponseDto sectionResponse = (SectionResponseDto) response;
            assertEquals(title, sectionResponse.getTitle());
            return sectionResponse;
        } else {
            checkFailureResponse(response, expectedStatus);
            return null;
        }
    }

    protected List<SectionResponseDto> getAllSections(int expectedCount, ErrorCode expectedStatus) {
        Object response = client.get(baseURL + "/sections/", List.class);
        if (response instanceof List<?>) {
            assertEquals(ErrorCode.SUCCESS, expectedStatus);
            @SuppressWarnings("unchecked")
            List<SectionResponseDto> responseList = (List<SectionResponseDto>) response;
            assertEquals(expectedCount, responseList.size());
            return responseList;
        } else {
            checkFailureResponse(response, expectedStatus);
            return null;
        }
    }


    protected SectionResponseDto editSectionById(int id, String title, ErrorCode expectedStatus) {
        SectionRequestDto request = new SectionRequestDto(title);
        Object response = client.put(baseURL + "/sections/" + id , request, SectionResponseDto.class);
        if (response instanceof SectionResponseDto) {
            assertEquals(ErrorCode.SUCCESS, expectedStatus);
            SectionResponseDto addTodoItemResponse = (SectionResponseDto) response;
            assertEquals(title, addTodoItemResponse.getTitle());
            return addTodoItemResponse;
        } else {
            checkFailureResponse(response, expectedStatus);
            return null;
        }
    }

    protected EmptySuccessResponseDto deleteSectionById(int id, ErrorCode expectedStatus) {
        Object response = client.delete(baseURL + "/sections/" + id , EmptySuccessResponseDto.class);
        if (response instanceof EmptySuccessResponseDto) {
            assertEquals(ErrorCode.SUCCESS, expectedStatus);
            return (EmptySuccessResponseDto)response;
        } else {
            checkFailureResponse(response, expectedStatus);
            return null;
        }
    }

    protected ContactWithAddressResponseDto addContactWithAddress(String firstName, String lastName, String patronymic,
                                                                  String birthdate, String phone, String email,
                                                                  String postcode, String country, String city,
                                                                  String street, String house, String block, String flat,
                                                                  ErrorCode expectedStatus) {
        ContactWithAddressRequestDto request = new ContactWithAddressRequestDto(firstName, lastName, patronymic,
                birthdate, phone,email, postcode,country, city, street, house, block, flat);
        Object response = client.post(baseURL + "/contacts/", request, ContactWithAddressResponseDto.class);
        if (response instanceof ContactWithAddressResponseDto) {
            assertEquals(ErrorCode.SUCCESS, expectedStatus);
            ContactWithAddressResponseDto addContactWithAddress = (ContactWithAddressResponseDto) response;
            assertEquals(firstName, addContactWithAddress.getFirstName());
            assertEquals(lastName, addContactWithAddress.getLastName());
            assertEquals(patronymic, addContactWithAddress.getPatronymic());
            assertEquals(birthdate, addContactWithAddress.getBirthdate());
            assertEquals(phone, addContactWithAddress.getPhone());
            assertEquals(email, addContactWithAddress.getEmail());
            assertEquals(postcode, addContactWithAddress.getPostcode());
            assertEquals(country, addContactWithAddress.getCountry());
            assertEquals(city, addContactWithAddress.getCity());
            assertEquals(street, addContactWithAddress.getStreet());
            assertEquals(house, addContactWithAddress.getHouse());
            assertEquals(block, addContactWithAddress.getBlock());
            assertEquals(flat, addContactWithAddress.getFlat());
            return addContactWithAddress;
        } else {
            checkFailureResponse(response, expectedStatus);
            return null;
        }
    }

    protected ContactResponseDto getContactById(int id, String firstName, String lastName, String patronymic,
                                                String birthdate, String phone, String email, String postcode,
                                                String country, String city, String street, String house,
                                                String block, String flat, ErrorCode expectedStatus) {
        Object response = client.get(baseURL + "/contacts/" + id, ContactResponseDto.class);
        if (response instanceof ContactResponseDto) {
            assertEquals(ErrorCode.SUCCESS, expectedStatus);
            ContactResponseDto getContactByIdResponse = (ContactResponseDto) response;
            assertEquals(id, getContactByIdResponse.getId());
            assertEquals(firstName, getContactByIdResponse.getFirstName());
            assertEquals(lastName, getContactByIdResponse.getLastName());
            assertEquals(patronymic, getContactByIdResponse.getPatronymic());
            assertEquals(birthdate, getContactByIdResponse.getBirthdate());
            assertEquals(phone, getContactByIdResponse.getPhone());
            assertEquals(email, getContactByIdResponse.getEmail());
            assertEquals(postcode, getContactByIdResponse.getPostcode());
            assertEquals(country, getContactByIdResponse.getCountry());
            assertEquals(city, getContactByIdResponse.getCity());
            assertEquals(street, getContactByIdResponse.getStreet());
            assertEquals(house, getContactByIdResponse.getHouse());
            assertEquals(block, getContactByIdResponse.getBlock());
            assertEquals(flat, getContactByIdResponse.getFlat());
            assertEquals(0, getContactByIdResponse.getSections().size());
            return getContactByIdResponse;
        } else {
            checkFailureResponse(response, expectedStatus);
            return null;
        }
    }

    protected List<ContactResponseDto> getAllContacts(int expectedCount, ErrorCode expectedStatus) {
        Object response = client.get(baseURL + "/contacts/", List.class);
        if (response instanceof List<?>) {
            assertEquals(ErrorCode.SUCCESS, expectedStatus);
            @SuppressWarnings("unchecked")
            List<ContactResponseDto> responseList = (List<ContactResponseDto>) response;
            assertEquals(expectedCount, responseList.size());
            return responseList;
        } else {
            checkFailureResponse(response, expectedStatus);
            return null;
        }
    }

    protected ContactResponseDto editContactById(int id, String firstName, String lastName, String patronymic,
                                                 String birthdate, String phone, String email, String postcode,
                                                 String country, String city, String street, String house,
                                                 String block, String flat, ErrorCode expectedStatus) {
        ContactWithAddressRequestDto request = new ContactWithAddressRequestDto(firstName, lastName, patronymic,
        birthdate, phone, email, postcode, country, city, street, house, block, flat);
        Object response = client.put(baseURL + "/contacts/" + id , request, ContactResponseDto.class);
        if (response instanceof ContactResponseDto) {
            assertEquals(ErrorCode.SUCCESS, expectedStatus);
            ContactResponseDto editTodoItemResponse = (ContactResponseDto) response;
            assertEquals(firstName, editTodoItemResponse.getFirstName());
            assertEquals(lastName, editTodoItemResponse.getLastName());
            assertEquals(patronymic, editTodoItemResponse.getPatronymic());
            assertEquals(birthdate, editTodoItemResponse.getBirthdate());
            assertEquals(phone, editTodoItemResponse.getPhone());
            assertEquals(email, editTodoItemResponse.getEmail());
            assertEquals(postcode, editTodoItemResponse.getPostcode());
            assertEquals(country, editTodoItemResponse.getCountry());
            assertEquals(city, editTodoItemResponse.getCity());
            assertEquals(street, editTodoItemResponse.getStreet());
            assertEquals(house, editTodoItemResponse.getHouse());
            assertEquals(block, editTodoItemResponse.getBlock());
            assertEquals(flat, editTodoItemResponse.getFlat());
            return editTodoItemResponse;
        } else {
            checkFailureResponse(response, expectedStatus);
            return null;
        }
    }

    protected EmptySuccessResponseDto deleteContactById(int id, ErrorCode expectedStatus) {
        Object response = client.delete(baseURL + "/contacts/" + id , EmptySuccessResponseDto.class);
        if (response instanceof EmptySuccessResponseDto) {
            assertEquals(ErrorCode.SUCCESS, expectedStatus);
            return (EmptySuccessResponseDto)response;
        } else {
            checkFailureResponse(response, expectedStatus);
            return null;
        }
    }

    protected EmptySuccessResponseDto deleteAllContacts(ErrorCode expectedStatus) {
        Object response = client.delete(baseURL + "/contacts/", EmptySuccessResponseDto.class);
        if (response instanceof EmptySuccessResponseDto) {
            assertEquals(ErrorCode.SUCCESS, expectedStatus);
            return (EmptySuccessResponseDto)response;
        } else {
            checkFailureResponse(response, expectedStatus);
            return null;
        }
    }

    protected List<SectionResponseDto> addContactToSection(int expectedCount, int contactId, int sectionId, String title,
                                                           ErrorCode expectedStatus) {
        AddContactToSectionRequestDto request = new AddContactToSectionRequestDto(sectionId,title);
        Object response = client.post(baseURL + "/contacts/" + contactId + "/section", request,
                List.class);
        if (response instanceof List<?>) {
            assertEquals(ErrorCode.SUCCESS, expectedStatus);
            @SuppressWarnings("unchecked")
            List<SectionResponseDto> responseList = (List<SectionResponseDto>) response;
            assertEquals(expectedCount, responseList.size());
            return responseList;
        } else {
            checkFailureResponse(response, expectedStatus);
            return null;
        }
    }

    protected List<SectionResponseDto> addContactToSections(int expectedCount, int contactId, int sectionId, String title,
                                                            ErrorCode expectedStatus) {
        List<AddContactToSectionRequestDto> requests = new ArrayList<>();
        requests.add(new AddContactToSectionRequestDto(sectionId,title));
        Object response = client.post(baseURL + "/contacts/" + contactId + "/sections", requests,
                List.class);
        if (response instanceof List<?>) {
            assertEquals(ErrorCode.SUCCESS, expectedStatus);
            @SuppressWarnings("unchecked")
            List<SectionResponseDto> responseList = (List<SectionResponseDto>) response;
            assertEquals(expectedCount, responseList.size());
            return responseList;
        } else {
            checkFailureResponse(response, expectedStatus);
            return null;
        }
    }

    protected List<SectionResponseDto> removeContactFromSection(int expectedCount, int contactId, int sectionId,
                                                                ErrorCode expectedStatus) {
        IdRequestDto request = new IdRequestDto(sectionId);
        Object response = client.put(baseURL + "/contacts/" + contactId + "/section", request,
                List.class);
        if (response instanceof List<?>) {
            assertEquals(ErrorCode.SUCCESS, expectedStatus);
            @SuppressWarnings("unchecked")
            List<SectionResponseDto> responseList = (List<SectionResponseDto>) response;
            assertEquals(expectedCount, responseList.size());
            return responseList;
        } else {
            checkFailureResponse(response, expectedStatus);
            return null;
        }
    }

}
