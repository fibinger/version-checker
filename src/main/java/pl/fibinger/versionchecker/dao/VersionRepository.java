package pl.fibinger.versionchecker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.fibinger.versionchecker.domain.Version;

public interface VersionRepository extends JpaRepository<Version, Long> {

}
