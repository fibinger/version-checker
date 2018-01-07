package pl.fibinger.versionchecker;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import pl.fibinger.versionchecker.resource.FeaturesResource;
import pl.fibinger.versionchecker.resource.UsersResource;
import pl.fibinger.versionchecker.resource.VersionsResource;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(VersionsResource.class);
        register(FeaturesResource.class);
        register(UsersResource.class);
    }
}
