package pl.fibinger.versionchecker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fibinger.versionchecker.dao.FeatureRepository;
import pl.fibinger.versionchecker.domain.Feature;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class FeatureService {

    @Autowired
    private FeatureRepository featureRepository;

    public List<Feature> getAllFeatures() {
        return featureRepository.findAll();
    }

    public Feature getFeature(String featureName) {
        return featureRepository.findByName(featureName);
    }

    public void addFeature(String featureName) {
        featureRepository.save(new Feature(featureName));
    }

    public void validateFeatures(String... features) {
        List<String> availableFeatureNames = getAllFeatures().stream().map(Feature::getName).collect(Collectors.toList());
        if (!availableFeatureNames.containsAll(Arrays.asList(features))) {
            throw new RuntimeException("Invalid features"); // TODO implement exception mapper
        }
    }

}
