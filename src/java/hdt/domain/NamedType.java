package hdt.domain;

public abstract class NamedType extends IdentifiableType {

    private String name;

    public NamedType() {
    }

    public NamedType(String name) {
        super();
        this.name = name;
    }
    
    public NamedType(Integer id, String name) {
        super(id);
        this.name = name;
    }

    // Used for object copying.
    public NamedType(NamedType template) {
        super(template);
        this.name = template.getName();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {        
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        NamedType other = (NamedType) obj;
        if (name == null) {
            if (other.getName() != null) {
                return false;
            }
        } else if (!name.equals(other.getName())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
}
