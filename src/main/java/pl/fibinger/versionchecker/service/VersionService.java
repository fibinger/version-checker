package pl.fibinger.versionchecker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fibinger.versionchecker.dao.UserRepository;
import pl.fibinger.versionchecker.dao.VersionFaceFeatureRepository;
import pl.fibinger.versionchecker.dao.VersionFaceRepository;
import pl.fibinger.versionchecker.dao.VersionRepository;
import pl.fibinger.versionchecker.domain.*;
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
    private VersionFaceRepository versionFaceRepository;

    @Autowired
    private VersionFaceFeatureRepository versionFaceFeatureRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeatureService featureService;

    public List<String> getAllVersionNames() {
        return versionRepository.findAll().stream()
                .map(Version::getName)
                .collect(Collectors.toList());
    }

    public VersionRepresentation getVersionRepresentation(String versionName, Long userId) {
        Version version = versionRepository.findByName(versionName);
        User user = userId != null ? userRepository.getOne(userId) : null;
        VersionFace versionFace = versionFaceRepository.findByVersionAndUser(version, user);
        if (versionFace == null) { // no face for given user, use public face
            versionFace = versionFaceRepository.findByVersionAndUser(version, null);
        }
        List<VersionFaceFeature> versionFeatures = versionFaceFeatureRepository.findByVersionFace(versionFace);
        return new VersionRepresentation()
                .setName(versionName)
                .setValid(version.getValid())
                .setActiveFeatures(versionFeatures.stream().map(vf -> vf.getFeature().getName()).collect(Collectors.toSet()));
    }

    public void addVersion(VersionDTO versionDTO) {
        Version version = new Version(versionDTO.getName(), versionDTO.isValid());
        version = versionRepository.save(version);
        setFeatures(version, null, versionDTO.getActiveFeatures());
    }

    public void setFeatures(String versionName, Long userId, Set<String> featureNames) {
        Version version = versionRepository.findByName(versionName);
        User user = userId != null ? userRepository.getOne(userId) : null;
        setFeatures(version, user, featureNames);
    }

    private void setFeatures(Version version, User user, Set<String> featureNames) {
        featureService.validateFeatures(featureNames.toArray(new String[0]));
        VersionFace versionFace = versionFaceRepository.findByVersionAndUser(version, user);
        if (versionFace == null) {
            versionFace = new VersionFace();
            versionFace.setVersion(version);
            versionFace.setUser(user);
            versionFace = versionFaceRepository.save(versionFace);
        }
        versionFaceFeatureRepository.deleteByVersionFace(versionFace);

        for (String featureName : featureNames) {
            Feature feature = featureService.getFeature(featureName);
            VersionFaceFeature versionFaceFeature = new VersionFaceFeature();
            versionFaceFeature.setVersionFace(versionFace);
            versionFaceFeature.setFeature(feature);
            versionFaceFeatureRepository.save(versionFaceFeature);
        }
    }
}
