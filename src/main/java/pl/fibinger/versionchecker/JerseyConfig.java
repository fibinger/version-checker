package pl.fibinger.versionchecker;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import pl.fibinger.versionchecker.resource.VersionResource;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(VersionResource.class);
    }
}
