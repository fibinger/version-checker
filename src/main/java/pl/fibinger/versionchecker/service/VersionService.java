package pl.fibinger.versionchecker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fibinger.versionchecker.dao.VersionFeatureRepository;
import pl.fibinger.versionchecker.dao.VersionRepository;
import pl.fibinger.versionchecker.domain.Feature;
import pl.fibinger.versionchecker.domain.Version;
import pl.fibinger.versionchecker.domain.VersionFeature;
import pl.fibinger.versionchecker.representation.VersionRepresentation;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class VersionService {

    @Autowired
    private VersionRepository versionRepository;

    @Autowired
    private VersionFeatureRepository versionFeatureRepository;

    @Autowired
    private FeatureService featureService;

    public List<String> getAllVersionNames() {
        return versionRepository.findAll().stream()
                .map(Version::getName)
                .collect(Collectors.toList());
    }

    public VersionRepresentation getVersionRepresentation(String versionName) {
        Version version = versionRepository.findByName(versionName);
        List<VersionFeature> versionFeatures = versionFeatureRepository.findByVersion(version);
        return new VersionRepresentation()
                .setName(versionName)
                .setActiveFeatures(versionFeatures.stream().map(vf -> vf.getFeature().getName()).collect(Collectors.toSet()));
    }

    public Version addVersion(String version) {
        return versionRepository.save(new Version(version));
    }

    public void setFeatures(String versionName, List<String> featureNames) {
        featureService.validateFeatures(featureNames.toArray(new String[0]));
        Version version = versionRepository.findByName(versionName);

        for (String featureName : featureNames) {
            Feature feature = featureService.getFeature(featureName);
            VersionFeature versionFeature = new VersionFeature();
            versionFeature.setVersion(version);
            versionFeature.setFeature(feature);
            versionFeatureRepository.save(versionFeature);
        }
    }
}
