package ru.omsu.imit.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsu.imit.dao.ContactDao;
import ru.omsu.imit.dao.ContactSectionDao;
import ru.omsu.imit.dao.SectionDao;
import ru.omsu.imit.daoimpl.ContactDaoImpl;
import ru.omsu.imit.daoimpl.ContactSectionDaoImpl;
import ru.omsu.imit.daoimpl.SectionDaoImpl;
import ru.omsu.imit.exception.AddressBookException;
import ru.omsu.imit.model.Contact;
import ru.omsu.imit.model.Section;
import ru.omsu.imit.rest.dto.request.AddContactToSectionRequestDto;
import ru.omsu.imit.rest.dto.request.DtoValidator;
import ru.omsu.imit.rest.dto.request.IdRequestDto;
import ru.omsu.imit.rest.dto.response.SectionResponseDto;
import ru.omsu.imit.utils.AddressBookRESTUtils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ContactSectionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactService.class);
    private static final Gson GSON = new GsonBuilder().create();
    private ContactSectionDao contactSectionDao = new ContactSectionDaoImpl();
    private ContactDao contactDao = new ContactDaoImpl();
    private SectionDao sectionDao = new SectionDaoImpl();

    public Response addContactToSection(int id, String json) {
        LOGGER.debug("SERVICE add contact with id " + id + " to section " + json);
        try {
            AddContactToSectionRequestDto request = AddressBookRESTUtils.getClassInstanceFromJson(GSON,
                    json, AddContactToSectionRequestDto.class);
            DtoValidator.sectionFieldsDtoValidate(request.getTitle());
            Contact contact = contactDao.getById(id);
            Section section = sectionDao.getById(request.getId());
            contactSectionDao.addContactToSection(contact, section);
            List<SectionResponseDto> sectionsResponseList = new ArrayList<>();
            for(Section s: contact.getSections()) {
                sectionsResponseList.add(new SectionResponseDto(s.getId(), s.getTitle()));
            }
            String response = GSON.toJson(sectionsResponseList);
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (AddressBookException e) {
            return AddressBookRESTUtils.failureResponse(e);
        }
    }

    public Response addContactToSections(int id, String json) {
        LOGGER.debug("SERVICE add contact with id " + id + " to sections " + json);
        try {
            Type requestListType = new TypeToken<List<AddContactToSectionRequestDto>>() {}.getType();
            List<AddContactToSectionRequestDto> requestList = GSON.fromJson(json, requestListType);
            Contact contact = contactDao.getById(id);
            List<Section> sectionsToDB = new ArrayList<>();
            for(AddContactToSectionRequestDto requestItem: requestList){
                DtoValidator.sectionFieldsDtoValidate(requestItem.getTitle());
                Section section = sectionDao.getById(requestItem.getId());
                sectionsToDB.add(section);
            }
            contactSectionDao.addContactToSections(contact, sectionsToDB);
            List<SectionResponseDto> sectionsResponseList = new ArrayList<>();
            for(Section s: contact.getSections()) {
                sectionsResponseList.add(new SectionResponseDto(s.getId(), s.getTitle()));
            }
            String response = GSON.toJson(sectionsResponseList);
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (AddressBookException e) {
            return AddressBookRESTUtils.failureResponse(e);
        }
    }

    public Response removeContactFromSection(int id, String json) {
        LOGGER.debug("SERVICE remove contact with id " + id + " from section " + json);
        try {
            IdRequestDto requestId = AddressBookRESTUtils.getClassInstanceFromJson(GSON,
                    json, IdRequestDto.class);
            Contact contact = contactDao.getById(id);
            Section section = sectionDao.getById(requestId.getId());
            contactSectionDao.removeContactFromSection(contact, section);
            List<SectionResponseDto> sectionsResponseList = new ArrayList<>();
            for(Section s: contact.getSections()) {
                sectionsResponseList.add(new SectionResponseDto(s.getId(), s.getTitle()));
            }
            String response = GSON.toJson(sectionsResponseList);
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (AddressBookException e) {
            return AddressBookRESTUtils.failureResponse(e);
        }
    }
}
