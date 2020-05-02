package ru.omsu.imit.service;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsu.imit.dao.ContactDao;
import ru.omsu.imit.daoimpl.ContactDaoImpl;
import ru.omsu.imit.model.Section;
import ru.omsu.imit.rest.dto.request.ContactWithAddressRequestDto;
import ru.omsu.imit.rest.dto.request.DtoValidator;
import ru.omsu.imit.rest.dto.response.ContactResponseDto;
import ru.omsu.imit.rest.dto.response.ContactWithAddressResponseDto;
import ru.omsu.imit.rest.dto.response.EmptySuccessResponseDto;
import ru.omsu.imit.exception.AddressBookException;
import ru.omsu.imit.model.Address;
import ru.omsu.imit.model.Contact;
import ru.omsu.imit.rest.dto.response.SectionResponseDto;
import ru.omsu.imit.utils.AddressBookRESTUtils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ContactService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactService.class);
    private static final Gson GSON = new GsonBuilder().create();
    private ContactDao contactDao = new ContactDaoImpl();

    public Response addContactWithAddress(String json) {
        LOGGER.debug("SERVICE Insert contact with address " + json);
        try {
            ContactWithAddressRequestDto request = AddressBookRESTUtils.getClassInstanceFromJson(GSON,
                    json,
                    ContactWithAddressRequestDto.class);
            DtoValidator.contactWithAddressDtoValidate(request);
            Contact contact = new Contact(request.getFirstName(), request.getLastName(), request.getPatronymic(),
                    LocalDate.parse(request.getBirthdate()), request.getPhone(), request.getEmail());
            Address address = new Address(request.getPostcode(), request.getCountry(), request.getCity(),
                    request.getStreet(), request.getHouse(), request.getBlock(), request.getFlat());
            Contact addedContact = contactDao.insert(contact, address);
            String response = GSON.toJson(new ContactWithAddressResponseDto(addedContact.getId(), addedContact.getFirstName(),
                    addedContact.getLastName(), addedContact.getPatronymic(), addedContact.getBirthdate().toString(),
                    addedContact.getPhone(), addedContact.getEmail(), addedContact.getAddress().getPostcode(),
                    addedContact.getAddress().getCountry(), addedContact.getAddress().getCity(),
                    addedContact.getAddress().getStreet(), addedContact.getAddress().getHouse(),
                    addedContact.getAddress().getBlock(), addedContact.getAddress().getFlat()));
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (AddressBookException e) {
            return AddressBookRESTUtils.failureResponse(e);
        }
    }

    public Response getById(int id) {
        LOGGER.debug("SERVICE Get contact by id " + id);
        try {
            Contact contact = contactDao.getById(id);
            List<SectionResponseDto> sectionResponses = new ArrayList<>();
            List<Section> contactSections = contact.getSections();
            for(Section s: contactSections) {
                sectionResponses.add(new SectionResponseDto(s.getId(), s.getTitle()));
            }
            String response = GSON.toJson(new ContactResponseDto(contact.getId(), contact.getFirstName(),
                    contact.getLastName(), contact.getPatronymic(), contact.getBirthdate().toString(),
                    contact.getPhone(), contact.getEmail(), contact.getAddress().getPostcode(),
                    contact.getAddress().getCountry(), contact.getAddress().getCity(),
                    contact.getAddress().getStreet(), contact.getAddress().getHouse(),
                    contact.getAddress().getBlock(), contact.getAddress().getFlat(), sectionResponses));
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (AddressBookException e){
           return AddressBookRESTUtils.failureResponse(e);
       }
    }

    public Response getAll() {
        LOGGER.debug("SERVICE Get all contacts");
        try{
        List<Contact> contacts = contactDao.getAll();
        List<ContactResponseDto> contactResponses= new ArrayList<>();
        for(Contact contact: contacts) {
            List<SectionResponseDto> sectionResponses = new ArrayList<>();
            List<Section> contactSections = contact.getSections();
            for(Section s: contactSections) {
                sectionResponses.add(new SectionResponseDto(s.getId(), s.getTitle()));
            }
            ContactResponseDto contactResponse = new ContactResponseDto(contact.getId(), contact.getFirstName(),
                    contact.getLastName(), contact.getPatronymic(), contact.getBirthdate().toString(),
                    contact.getPhone(), contact.getEmail(), contact.getAddress().getPostcode(),
                    contact.getAddress().getCountry(), contact.getAddress().getCity(),
                    contact.getAddress().getStreet(), contact.getAddress().getHouse(),
                    contact.getAddress().getBlock(), contact.getAddress().getFlat(), sectionResponses);
            contactResponses.add(contactResponse);
        }
        String response = GSON.toJson(contactResponses);
        return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (AddressBookException e) {
            return AddressBookRESTUtils.failureResponse(e);
        }
    }


    public Response editById(int id, String json) {
        LOGGER.debug("SERVICE Edit contact by id " + id + "with params " + json);
        try{
            ContactWithAddressRequestDto request = AddressBookRESTUtils.getClassInstanceFromJson(GSON,
                    json,
                    ContactWithAddressRequestDto.class);
            DtoValidator.contactWithAddressDtoValidate(request);
            Contact contact = contactDao.getById(id);
            contact.setFirstName(request.getFirstName());
            contact.setLastName(request.getLastName());
            contact.setPatronymic(request.getPatronymic());
            contact.setBirthdate(LocalDate.parse(request.getBirthdate()));
            contact.setEmail(request.getEmail());
            contact.setPhone(request.getPhone());
            contact.getAddress().setContact(contact);
            contact.getAddress().setPostcode(request.getPostcode());
            contact.getAddress().setCountry(request.getCountry());
            contact.getAddress().setCity(request.getCity());
            contact.getAddress().setStreet(request.getStreet());
            contact.getAddress().setHouse(request.getHouse());
            contact.getAddress().setBlock(request.getBlock());
            contact.getAddress().setFlat(request.getFlat());
            contactDao.update(contact);
            String response = GSON.toJson(new ContactWithAddressResponseDto(contact.getId(), contact.getFirstName(),
                    contact.getLastName(), contact.getPatronymic(), contact.getBirthdate().toString(),
                    contact.getPhone(), contact.getEmail(), contact.getAddress().getPostcode(),
                    contact.getAddress().getCountry(), contact.getAddress().getCity(),
                    contact.getAddress().getStreet(), contact.getAddress().getHouse(),
                    contact.getAddress().getBlock(), contact.getAddress().getFlat()));
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (AddressBookException e) {
            return AddressBookRESTUtils.failureResponse(e);
        }
    }

    public Response deleteById(int id) {
        LOGGER.debug("SERVICE Delete by id " + id);
        try {
            contactDao.delete(id);
            String response = GSON.toJson(new EmptySuccessResponseDto());
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
            } catch (AddressBookException e){
             return AddressBookRESTUtils.failureResponse(e);
        }
    }

    public Response deleteAll() {
        LOGGER.debug("SERVICE Delete all ");
        try {
            contactDao.deleteAll();
            String response = GSON.toJson(new EmptySuccessResponseDto());
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (AddressBookException e){
            return AddressBookRESTUtils.failureResponse(e);
         }
    }
}
