package pl.fibinger.versionchecker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fibinger.versionchecker.dao.VersionFeatureRepository;
import pl.fibinger.versionchecker.dao.VersionRepository;
import pl.fibinger.versionchecker.domain.Feature;
import pl.fibinger.versionchecker.domain.Version;
import pl.fibinger.versionchecker.domain.VersionFeature;
import pl.fibinger.versionchecker.dto.VersionDTO;
import pl.fibinger.versionchecker.representation.VersionRepresentation;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
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
                .setValid(version.getValid())
                .setActiveFeatures(versionFeatures.stream().map(vf -> vf.getFeature().getName()).collect(Collectors.toSet()));
    }

    public void addVersion(VersionDTO versionDTO) {
        Version version = new Version(versionDTO.getName(), versionDTO.isValid());
        version = versionRepository.save(version);
        setFeatures(version, versionDTO.getActiveFeatures());
    }

    public void setFeatures(String versionName, Set<String> featureNames) {
        Version version = versionRepository.findByName(versionName);
        setFeatures(version, featureNames);
    }

    private void setFeatures(Version version, Set<String> featureNames) {
        featureService.validateFeatures(featureNames.toArray(new String[0]));
        versionFeatureRepository.deleteByVersion(version);

        for (String featureName : featureNames) {
            Feature feature = featureService.getFeature(featureName);
            VersionFeature versionFeature = new VersionFeature();
            versionFeature.setVersion(version);
            versionFeature.setFeature(feature);
            versionFeatureRepository.save(versionFeature);
        }
    }
}
