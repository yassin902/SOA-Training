package webservices;

import entities.Module;
import entities.UniteEnseignement;
import metiers.ModuleBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/modules")
public class ModuleRessource {

    private ModuleBusiness helper = new ModuleBusiness();

    // B1) POST /modules  (JSON)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addModule(Module module) {
        if (module == null || module.getUniteEnseignement() == null
                || helper.getModuleByMatricule(module.getMatricule()) != null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (helper.addModule(module)) {
            return Response.status(Response.Status.CREATED).entity(module).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build(); // UE inexistante
    }

    // B2) GET /modules
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllModules() {
        return Response.status(Response.Status.OK).entity(helper.getAllModules()).build();
    }

    // B6) GET /modules/UE?codeUE=1
    @GET
    @Path("/UE")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModulesByUE(@QueryParam("codeUE") int codeUE) {
        UniteEnseignement ue = new UniteEnseignement();
        ue.setCode(codeUE);
        List<Module> liste = helper.getModulesByUE(ue);
        if (liste.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(liste).build();
    }

    // B3) GET /modules/M101
    @GET
    @Path("/{matricule}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModule(@PathParam("matricule") String matricule) {
        Module m = helper.getModuleByMatricule(matricule);
        if (m == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(m).build();
    }

    // B4) DELETE /modules/M101
    @DELETE
    @Path("/{matricule}")
    public Response deleteModule(@PathParam("matricule") String matricule) {
        if (helper.deleteModule(matricule)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // B5) PUT /modules/M102  (JSON)
    @PUT
    @Path("/{matricule}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateModule(@PathParam("matricule") String matricule, Module module) {
        if (helper.updateModule(matricule, module)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}