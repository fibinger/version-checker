package pl.fibinger.versionchecker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.fibinger.versionchecker.domain.VersionFace;
import pl.fibinger.versionchecker.domain.VersionFaceFeature;

import java.util.List;

public interface VersionFaceFeatureRepository extends JpaRepository<VersionFaceFeature, Long> {

    List<VersionFaceFeature> findByVersionFace(VersionFace versionFace);

    void deleteByVersionFace(VersionFace versionFace);

}
