package ru.omsu.imit.rest.dto.request;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.DateValidator;
import org.apache.commons.validator.routines.EmailValidator;
import ru.omsu.imit.exception.AddressBookException;
import ru.omsu.imit.exception.ErrorCode;

import java.time.LocalDate;

public class DtoValidator  {
    public static void contactWithAddressDtoValidate(ContactWithAddressRequestDto request) throws AddressBookException {
        contactFieldsValidate(request.getFirstName(),request.getLastName(), request.getPatronymic(),
                request.getBirthdate(), request.getPhone(), request.getEmail());
        addressFieldsValidate(request.getPostcode(), request.getCountry(), request.getCity(), request.getStreet(),
                request.getHouse(), request.getBlock(), request.getFlat());
    }

    public static void sectionDtoValidate(SectionRequestDto dto) throws AddressBookException {
        sectionFieldsDtoValidate(dto.getTitle());
    }

    public static void sectionFieldsDtoValidate(String title) throws AddressBookException {
        if(StringUtils.isEmpty(title)) {
            throw new AddressBookException(ErrorCode.INVALID_PARAMS);
        }
    }

    public static void contactFieldsValidate(String firstName, String lastName, String patronymic,
                                       String date, String phone, String email) throws AddressBookException {
        if(StringUtils.isAnyEmpty(
                firstName, lastName, patronymic)){
            throw new AddressBookException(ErrorCode.INVALID_PARAMS);
        }
        if(date == null) {
            throw new AddressBookException(ErrorCode.INVALID_PARAMS);
        }
        if(phone.length() < 6) {
            throw  new AddressBookException((ErrorCode.INVALID_PARAMS));
        }
        if(!EmailValidator.getInstance().isValid(email)) {
            throw new AddressBookException(ErrorCode.INVALID_PARAMS);
        }
        if(!DateValidator.getInstance().isValid(date, "yyyy-mm-dd")) {
            throw new AddressBookException(ErrorCode.INVALID_PARAMS);
        }
    }

    public static void addressFieldsValidate(String postcode, String country, String city, String street,
                                             String house, String block, String flat) throws AddressBookException {
        if(StringUtils.isAnyEmpty( postcode, country, city, street, house, block, flat)){
            throw new AddressBookException(ErrorCode.INVALID_PARAMS);
        }
    }
}
