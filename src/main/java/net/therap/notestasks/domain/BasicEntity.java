package net.therap.notestasks.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * @author tanmoy.das
 * @since 4/12/20
 */
@MappedSuperclass
public class BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdOn;

    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date updatedOn;

    @Column(name = "deleted_on")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date deletedOn;

    @Column(name = "is_deleted")
    protected boolean isDeleted;

    @PrePersist
    private void onCreate() {
        this.createdOn = new Date();
        onUpdate();
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedOn = new Date();
    }

    private void onDelete() {
        this.deletedOn = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
        onDelete();
    }

    public boolean isNew() {
        return id == 0;
    }
}
