package webservices;

import entities.UniteEnseignement;
import metiers.UniteEnseignementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/UE")
public class UniteEnseignementRessource {

    private UniteEnseignementBusiness helper = new UniteEnseignementBusiness();

    // A1) POST /UE  (XML)
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response addUE(UniteEnseignement ue) {
        if (ue == null || helper.getUEByCode(ue.getCode()) != null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        helper.addUniteEnseignement(ue);
        return Response.status(Response.Status.CREATED).build();
    }

    // A2) GET /UE        -> toutes les UE
    // A3) GET /UE?semestre=2
    // A6) GET /UE?code=123
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUEs(@QueryParam("semestre") Integer semestre,
                           @QueryParam("code") Integer code) {
        if (code != null) {
            UniteEnseignement ue = helper.getUEByCode(code);
            if (ue == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.status(Response.Status.OK).entity(ue).build();
        }
        if (semestre != null) {
            return Response.status(Response.Status.OK).entity(helper.getUEBySemestre(semestre)).build();
        }
        return Response.status(Response.Status.OK).entity(helper.getListeUE()).build();
    }

    // A4) DELETE /UE/102
    @DELETE
    @Path("/{code}")
    public Response deleteUE(@PathParam("code") int code) {
        if (helper.deleteUniteEnseignement(code)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // A5) PUT /UE/101  (XML)
    @PUT
    @Path("/{code}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response updateUE(@PathParam("code") int code, UniteEnseignement ue) {
        if (helper.updateUniteEnseignement(code, ue)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}