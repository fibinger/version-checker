package pl.fibinger.versionchecker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fibinger.versionchecker.dao.UserRepository;
import pl.fibinger.versionchecker.dao.VersionConfigurationFeatureRepository;
import pl.fibinger.versionchecker.dao.VersionConfigurationRepository;
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
    private VersionConfigurationRepository versionConfigurationRepository;

    @Autowired
    private VersionConfigurationFeatureRepository versionConfigurationFeatureRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeatureService featureService;

    /**
     * @return list of all version names
     */
    public List<String> getAllVersionNames() {
        return versionRepository.findAll().stream()
                .map(Version::getName)
                .collect(Collectors.toList());
    }

    /**
     * Returns version representation.
     *
     * @param versionName name of the version
     * @param userId      ID of user for which we want the configuration to be returned or null for public configuration.
     * @return configuration for requested user, or public one if either userId was null, or no configuration was
     * registered for given user.
     */
    public VersionRepresentation getVersionRepresentation(String versionName, Long userId) {
        Version version = versionRepository.findByName(versionName);
        User user = userId != null ? userRepository.getOne(userId) : null;
        VersionConfiguration versionConfiguration = versionConfigurationRepository.findByVersionAndUser(version, user);
        if (versionConfiguration == null) { // no configuration for given user, use public configuration
            versionConfiguration = versionConfigurationRepository.findByVersionAndUser(version, null);
        }
        List<VersionConfigurationFeature> versionFeatures = versionConfigurationFeatureRepository.findByVersionConfiguration(versionConfiguration);
        return new VersionRepresentation()
                .setName(versionName)
                .setValid(version.getValid())
                .setActiveFeatures(versionFeatures.stream().map(vf -> vf.getFeature().getName()).collect(Collectors.toSet()));
    }

    /**
     * Creates new version and stores it in database.
     */
    public void addVersion(VersionDTO versionDTO) {
        Version version = new Version(versionDTO.getName(), versionDTO.isValid());
        version = versionRepository.save(version);
        setFeatures(version, null, versionDTO.getActiveFeatures());
    }

    /**
     * Sets enabled features for the version of which name is provided.
     *
     * @param versionName  name of the version
     * @param userId       ID of user for which we want to restrict the change, or null if we want to set features globally
     * @param featureNames set of features we want to keep enabled
     */
    public void setFeatures(String versionName, Long userId, Set<String> featureNames) {
        Version version = versionRepository.findByName(versionName);
        User user = userId != null ? userRepository.getOne(userId) : null;
        setFeatures(version, user, featureNames);
    }

    private void setFeatures(Version version, User user, Set<String> featureNames) {
        featureService.validateFeatures(featureNames.toArray(new String[0]));
        VersionConfiguration versionConfiguration = versionConfigurationRepository.findByVersionAndUser(version, user);
        if (versionConfiguration == null) { // if no configuration object yet, create one
            versionConfiguration = new VersionConfiguration();
            versionConfiguration.setVersion(version);
            versionConfiguration.setUser(user);
            versionConfiguration = versionConfigurationRepository.save(versionConfiguration);
        }

        // delete old features, so that we can save new ones
        versionConfigurationFeatureRepository.deleteByVersionConfiguration(versionConfiguration);

        for (String featureName : featureNames) {
            Feature feature = featureService.getFeature(featureName);
            VersionConfigurationFeature versionConfigurationFeature = new VersionConfigurationFeature();
            versionConfigurationFeature.setVersionConfiguration(versionConfiguration);
            versionConfigurationFeature.setFeature(feature);
            versionConfigurationFeatureRepository.save(versionConfigurationFeature);
        }
    }
}
