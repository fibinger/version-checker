package pl.fibinger.versionchecker.dto;

import com.google.common.base.MoreObjects;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class VersionDTO {

    private String name;

    private boolean valid;

    private Set<String> activeFeatures;

    public VersionDTO(String name, boolean valid, String... activeFeatures) {
        this.name = name;
        this.valid = valid;
        this.activeFeatures = new HashSet<>(Arrays.asList(activeFeatures));
    }

    public VersionDTO() {
    }

    public String getName() {
        return name;
    }

    public VersionDTO setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isValid() {
        return valid;
    }

    public VersionDTO setValid(boolean valid) {
        this.valid = valid;
        return this;
    }

    public Set<String> getActiveFeatures() {
        return activeFeatures;
    }

    public VersionDTO setActiveFeatures(Set<String> activeFeatures) {
        this.activeFeatures = activeFeatures;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("activeFeatures", activeFeatures)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VersionDTO that = (VersionDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(activeFeatures, that.activeFeatures);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, activeFeatures);
    }
}
