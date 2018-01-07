package pl.fibinger.versionchecker.dto;

import java.util.Objects;

public class VersionDTO {

    private String name;

    public String getName() {
        return name;
    }

    public VersionDTO setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return com.google.common.base.MoreObjects.toStringHelper(this)
                .add("name", name)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VersionDTO that = (VersionDTO) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
