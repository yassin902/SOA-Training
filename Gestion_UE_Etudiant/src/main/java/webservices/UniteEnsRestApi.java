package webservices;

import entities.UniteEnseignement;
import metiers.UniteEnseignementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ue")
public class UniteEnsRestApi {
    static UniteEnseignementBusiness helper = new UniteEnseignementBusiness();

    // getListUE
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListUe() {
        return Response
                .status(200)
                .entity(helper.getListeUE())
                .build();
    }

    // addUE
    @Path("/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addUe(UniteEnseignement ue) {
        if (helper.addUniteEnseignement(ue)) {
            return Response.status(201).entity("Created").build();
        } else {
            return Response.status(400).entity("Erreur").build();
        }
    }
    // 1) Récupérer une UE par son code : GET /ue/get/{code}
    @Path("/get/{code}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUeByCode(@PathParam("code") int code) {
        UniteEnseignement ue = helper.getUEByCode(code);
        if (ue != null) {
            return Response.status(200).entity(ue).build();
        }
        return Response.status(404).entity("UE introuvable").build();
    }

    // 2) Filtrer par domaine : GET /ue/domaine?domaine=Informatique
    @Path("/domaine")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUeByDomaine(@QueryParam("domaine") String domaine) {
        return Response
                .status(200)
                .entity(helper.getUEByDomaine(domaine))
                .build();
    }

    // 3) Filtrer par semestre : GET /ue/semestre/{semestre}
    @Path("/semestre/{semestre}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUeBySemestre(@PathParam("semestre") int semestre) {
        return Response
                .status(200)
                .entity(helper.getUEBySemestre(semestre))
                .build();
    }

    // 4) Supprimer une UE : DELETE /ue/delete/{code}
    @Path("/delete/{code}")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUe(@PathParam("code") int code) {
        if (helper.deleteUniteEnseignement(code)) {
            return Response.status(200).entity("Supprimée").build();
        }
        return Response.status(404).entity("UE introuvable").build();
    }

    // 5) Mettre à jour une UE : PUT /ue/update/{code}
    @Path("/update/{code}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response   updateUe(@PathParam("code") int code, UniteEnseignement ue) {
        if (helper.updateUniteEnseignement(code, ue)) {
            return Response.status(200).entity("Mise à jour effectuée").build();
        }
        return Response.status(404).entity("UE introuvable").build();
    }
}