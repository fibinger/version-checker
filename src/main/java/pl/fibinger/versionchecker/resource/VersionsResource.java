package pl.fibinger.versionchecker.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.fibinger.versionchecker.representation.VersionRepresentation;
import pl.fibinger.versionchecker.service.VersionService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/versions")
@Component
public class VersionsResource {

    @Autowired
    private VersionService versionService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public List<String> getVersions() {
        return versionService.getAllVersionNames();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{versionName}/validity")
    public VersionRepresentation getVersion(@PathParam("versionName") String versionName) {
        return versionService.getVersionRepresentation(versionName);
    }

    @POST
    @Path("/")
    public void postVersion(String versionName) {
        versionService.addVersion(versionName);
    }

    @POST
    @Path("/{versionName}/features")
    public void putVersionFeatures(@PathParam("versionName") String versionName, List<String> features) {
        versionService.setFeatures(versionName, features);
    }
}
