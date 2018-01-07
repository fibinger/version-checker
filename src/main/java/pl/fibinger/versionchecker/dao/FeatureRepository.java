package pl.fibinger.versionchecker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.fibinger.versionchecker.domain.Feature;

public interface FeatureRepository extends JpaRepository<Feature, Long> {

    Feature findByName(String name);

}
