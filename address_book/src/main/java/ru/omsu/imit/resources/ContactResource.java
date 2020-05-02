package ru.omsu.imit.resources;

import ru.omsu.imit.service.ContactSectionService;
import ru.omsu.imit.service.ContactService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/api/contacts")
public class ContactResource {
    private static ContactService contactService = new ContactService();
    private static ContactSectionService contactSectionService = new ContactSectionService();

    @POST
    @Path("/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addContact(String json) {
        return contactService.addContactWithAddress(json);
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getById(@PathParam(value = "id") int id) {
        return contactService.getById(id);
    }

    @GET
    @Path("/")
    @Produces("application/json")
    public Response getAll() {
        return contactService.getAll();
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response editById(@PathParam(value = "id") int id, String json) {
        return contactService.editById(id, json);
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    public Response deleteById(@PathParam(value = "id") int id) {
        return contactService.deleteById(id);
    }

    @DELETE
    @Path("/")
    @Produces("application/json")
    public Response deleteAll() {
        return contactService.deleteAll();
    }

    @POST
    @Path("{id}/section")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addContactToSection(@PathParam(value = "id") int id, String json) {
        return contactSectionService.addContactToSection(id, json);
    }

    @POST
    @Path("{id}/sections")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addContactToSections(@PathParam(value = "id") int id, String json) {
        return contactSectionService.addContactToSections(id, json);
    }

    @PUT
    @Path("{id}/section")
    @Consumes("application/json")
    @Produces("application/json")
    public Response removeContactFromSection(@PathParam(value = "id") int id, String json) {
        return contactSectionService.removeContactFromSection(id, json);
    }
}
