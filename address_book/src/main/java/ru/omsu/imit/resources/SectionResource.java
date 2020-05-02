package ru.omsu.imit.resources;

import ru.omsu.imit.service.SectionService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/api/sections")
public class SectionResource {
    private static SectionService sectionService = new SectionService();

    @POST
    @Path("/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addSection(String json) {
        return sectionService.addSection(json);
    }

    @PUT
    @Path("/{id}")
    @Produces("application/json")
    public Response editById(@PathParam(value = "id") int id, String json) {
        return sectionService.editById(id, json);
    }

    @GET
    @Path("/")
    @Produces("application/json")
    public Response getAll() {
        return sectionService.getAll();
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    public Response deleteById(@PathParam(value = "id") int id) {
        return sectionService.deleteById(id);
    }

    @DELETE
    @Path("/")
    @Produces("application/json")
    public Response deleteAll() {
        return sectionService.deleteAll();
    }
}