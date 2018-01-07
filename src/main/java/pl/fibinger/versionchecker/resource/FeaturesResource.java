package pl.fibinger.versionchecker.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.fibinger.versionchecker.service.FeatureService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/features")
@Component
public class FeaturesResource {

    @Autowired
    private FeatureService featureService;

    @POST
    @Path("/")
    public void postFeature(String featureName) {
        featureService.addFeature(featureName);
    }

}
