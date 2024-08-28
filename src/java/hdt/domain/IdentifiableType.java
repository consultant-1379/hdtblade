package hdt.domain;

public abstract class IdentifiableType {

    private Integer id;

    public IdentifiableType() {
    }

    // Used for object copying.
    public IdentifiableType(IdentifiableType template) {
        this.id = template.id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public IdentifiableType(Integer id) {
        super();
        this.id = id;
    }

    @Override
    public int hashCode() {
        return ((id == null) ? 0 : id.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        IdentifiableType other = (IdentifiableType) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
