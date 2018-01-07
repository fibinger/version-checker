package pl.fibinger.versionchecker.domain;

import javax.persistence.*;

@Entity
@Table(name = "version_face_feature")
public class VersionFaceFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "version_face_id", nullable = false)
    private VersionFace versionFace;

    @ManyToOne
    @JoinColumn(name = "feature_id", nullable = false)
    private Feature feature;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VersionFace getVersionFace() {
        return versionFace;
    }

    public void setVersionFace(VersionFace versionFace) {
        this.versionFace = versionFace;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }
}
