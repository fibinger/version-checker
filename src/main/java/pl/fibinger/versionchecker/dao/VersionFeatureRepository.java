package pl.fibinger.versionchecker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.fibinger.versionchecker.domain.Version;
import pl.fibinger.versionchecker.domain.VersionFeature;

import java.util.List;

public interface VersionFeatureRepository extends JpaRepository<VersionFeature, Long> {

    List<VersionFeature> findByVersion(Version version);

}
