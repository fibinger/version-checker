package pl.fibinger.versionchecker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.fibinger.versionchecker.domain.User;
import pl.fibinger.versionchecker.domain.Version;
import pl.fibinger.versionchecker.domain.VersionConfiguration;

public interface VersionConfigurationRepository extends JpaRepository<VersionConfiguration, Long> {

    VersionConfiguration findByVersionAndUser(Version version, User user);

}
