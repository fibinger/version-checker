package pl.fibinger.versionchecker.domain;

import javax.persistence.*;

@Entity
@Table(name = "version_configuration_feature")
public class VersionConfigurationFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "version_configuration_id", nullable = false)
    private VersionConfiguration versionConfiguration;

    @ManyToOne
    @JoinColumn(name = "feature_id", nullable = false)
    private Feature feature;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VersionConfiguration getVersionConfiguration() {
        return versionConfiguration;
    }

    public void setVersionConfiguration(VersionConfiguration versionConfiguration) {
        this.versionConfiguration = versionConfiguration;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }
}
