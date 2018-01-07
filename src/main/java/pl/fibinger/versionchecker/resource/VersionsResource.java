package pl.fibinger.versionchecker.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.fibinger.versionchecker.dto.VersionDTO;
import pl.fibinger.versionchecker.representation.VersionRepresentation;
import pl.fibinger.versionchecker.service.VersionService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;

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
    @Path("/{versionName}")
    public VersionRepresentation getVersion(@PathParam("versionName") String versionName, @QueryParam("userId") Long userId) {
        return versionService.getVersionRepresentation(versionName, userId);
    }

    @POST
    @Path("/")
    public void postVersion(VersionDTO versionDTO) {
        versionService.addVersion(versionDTO);
    }

    @PUT
    @Path("/{versionName}/features")
    public void putVersionFeatures(@PathParam("versionName") String versionName, @QueryParam("userId") Long userId, Set<String> features) {
        versionService.setFeatures(versionName, userId, features);
    }
}
