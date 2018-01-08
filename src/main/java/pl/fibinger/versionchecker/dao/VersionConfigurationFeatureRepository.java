package pl.fibinger.versionchecker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.fibinger.versionchecker.domain.VersionConfiguration;
import pl.fibinger.versionchecker.domain.VersionConfigurationFeature;

import java.util.List;

public interface VersionConfigurationFeatureRepository extends JpaRepository<VersionConfigurationFeature, Long> {

    List<VersionConfigurationFeature> findByVersionConfiguration(VersionConfiguration versionConfiguration);

    void deleteByVersionConfiguration(VersionConfiguration versionConfiguration);

}
