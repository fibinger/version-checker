package pl.fibinger.versionchecker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.fibinger.versionchecker.domain.User;
import pl.fibinger.versionchecker.domain.Version;
import pl.fibinger.versionchecker.domain.VersionFace;

public interface VersionFaceRepository extends JpaRepository<VersionFace, Long> {

    VersionFace findByVersionAndUser(Version version, User user);

}
