package pl.shalpuk.scooterService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.shalpuk.scooterService.util.IdGenerator;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractPersistentObject {

    @Id
    private UUID id = IdGenerator.generateId();

    @Version
    @JsonIgnore
    private Integer version;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractPersistentObject that = (AbstractPersistentObject) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
