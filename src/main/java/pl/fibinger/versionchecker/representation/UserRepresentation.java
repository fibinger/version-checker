package pl.fibinger.versionchecker.representation;

public class UserRepresentation {

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public UserRepresentation setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserRepresentation setName(String name) {
        this.name = name;
        return this;
    }
}
