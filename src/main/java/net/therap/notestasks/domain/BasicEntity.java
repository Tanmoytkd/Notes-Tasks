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
    protected Long id;

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
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedOn = new Date();
    }

    @PreRemove
    private void onDelete() {
        this.deletedOn = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
    }

    public boolean isNew() {
        return id == 0;
    }
}
