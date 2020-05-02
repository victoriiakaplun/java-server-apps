package ru.omsu.imit.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.omsu.imit.dao.SectionDao;
import ru.omsu.imit.daoimpl.SectionDaoImpl;
import ru.omsu.imit.exception.AddressBookException;
import ru.omsu.imit.model.Section;
import ru.omsu.imit.rest.dto.request.DtoValidator;
import ru.omsu.imit.rest.dto.request.SectionRequestDto;
import ru.omsu.imit.rest.dto.response.EmptySuccessResponseDto;
import ru.omsu.imit.rest.dto.response.SectionResponseDto;
import ru.omsu.imit.utils.AddressBookRESTUtils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class SectionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactService.class);
    private static final Gson GSON = new GsonBuilder().create();
    private SectionDao sectionDao = new SectionDaoImpl();

    public Response addSection(String json) {
        LOGGER.debug("SERVICE Insert section " + json);
        try {
            SectionRequestDto request = AddressBookRESTUtils.getClassInstanceFromJson(GSON,
                    json,
                    SectionRequestDto.class);
            DtoValidator.sectionFieldsDtoValidate(request.getTitle());
            Section section = new Section(request.getTitle());
            Section addedSection = sectionDao.insert(section);
            String response = GSON.toJson(new SectionResponseDto(addedSection.getId(), addedSection.getTitle()));
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (AddressBookException e) {
            return AddressBookRESTUtils.failureResponse(e);
        }
    }

    public Response editById(int id, String json) {
        LOGGER.debug("SERVICE Edit section " + json + " by id " + id);
        try {
            SectionRequestDto request = AddressBookRESTUtils.getClassInstanceFromJson(GSON,
                    json,
                    SectionRequestDto.class);
            DtoValidator.sectionDtoValidate(request);
            Section section = sectionDao.getById(id);
            section.setTitle(request.getTitle());
            sectionDao.update(section);
            String response = GSON.toJson(new SectionResponseDto(section.getId(), section.getTitle()));
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (AddressBookException e) {
            return AddressBookRESTUtils.failureResponse(e);
        }
    }

    public Response deleteById(int id) {
        LOGGER.debug("SERVICE Delete by id " + id);
        try {
            sectionDao.delete(id);
            String response = GSON.toJson(new EmptySuccessResponseDto());
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (AddressBookException e){
            return AddressBookRESTUtils.failureResponse(e);
        }
    }

    public Response deleteAll() {
        LOGGER.debug("SERVICE Delete all sections");
        try {
            sectionDao.deleteAll();
            String response = GSON.toJson(new EmptySuccessResponseDto());
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (AddressBookException e){
            return AddressBookRESTUtils.failureResponse(e);
        }
    }

    public Response getAll() {
        LOGGER.debug("SERVICE Get all sections");
        try{
            List<Section> sections = sectionDao.getAll();
            List<SectionResponseDto> sectionResponses= new ArrayList<>();
            for(Section section: sections) {
                sectionResponses.add(new SectionResponseDto(section.getId(), section.getTitle()));
            }
            String response = GSON.toJson(sectionResponses);
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (AddressBookException e) {
            return AddressBookRESTUtils.failureResponse(e);
        }
    }
}
