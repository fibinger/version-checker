package pl.fibinger.versionchecker.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.fibinger.versionchecker.dao.VersionRepository;
import pl.fibinger.versionchecker.domain.Version;
import pl.fibinger.versionchecker.dto.VersionDTO;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/versions")
@Component
public class VersionResource {

    @Autowired
    private VersionRepository versionRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Set<VersionDTO> getVersions() {
        return versionRepository.findAll().stream()
                .map(version -> new VersionDTO().setName(version.getName()))
                .collect(Collectors.toSet());
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public void postVersion(String version) {
        versionRepository.save(new Version(version));
    }
}
