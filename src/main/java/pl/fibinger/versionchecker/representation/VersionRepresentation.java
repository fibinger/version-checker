package pl.fibinger.versionchecker.representation;

import com.google.common.base.MoreObjects;

import java.util.Objects;
import java.util.Set;

public class VersionRepresentation {

    private String name;

    private boolean valid;

    private Set<String> activeFeatures;

    public String getName() {
        return name;
    }

    public VersionRepresentation setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isValid() {
        return valid;
    }

    public VersionRepresentation setValid(boolean valid) {
        this.valid = valid;
        return this;
    }

    public Set<String> getActiveFeatures() {
        return activeFeatures;
    }

    public VersionRepresentation setActiveFeatures(Set<String> activeFeatures) {
        this.activeFeatures = activeFeatures;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("valid", valid)
                .add("activeFeatures", activeFeatures)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VersionRepresentation that = (VersionRepresentation) o;
        return valid == that.valid &&
                Objects.equals(name, that.name) &&
                Objects.equals(activeFeatures, that.activeFeatures);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, valid, activeFeatures);
    }
}
