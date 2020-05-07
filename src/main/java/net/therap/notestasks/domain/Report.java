package net.therap.notestasks.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tanmoy.das
 * @since 4/12/20
 */
@NamedQueries({
        @NamedQuery(name = "Report.findAll",
                query = "FROM Report report WHERE report.deleted = false")
})

@Entity
@Table(name = "reports")
public class Report extends BasicEntity {

    public enum ReportStatus {
        OPEN,
        RESOLVED,
        REJECTED
    }

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "target_id")
    private User target;

    @Embedded
    private ReportContent content;

    @NotNull
    @Column(name = "report_status")
    @Enumerated(EnumType.STRING)
    private ReportStatus reportStatus;

    @OneToMany(mappedBy = "report", cascade = {CascadeType.ALL})
    private List<ReportComment> comments;

    public Report() {
        this.comments = new ArrayList<>();
        this.reportStatus = ReportStatus.OPEN;
    }

    public boolean isOpen() {
        return reportStatus.equals(ReportStatus.OPEN);
    }

    public boolean isResolved() {
        return reportStatus.equals(ReportStatus.RESOLVED);
    }

    public boolean isRejected() {
        return reportStatus.equals(ReportStatus.REJECTED);
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
    }

    public ReportContent getContent() {
        return content;
    }

    public void setContent(ReportContent content) {
        this.content = content;
    }

    public List<ReportComment> getComments() {
        return comments;
    }

    public void setComments(List<ReportComment> comments) {
        this.comments = comments;
    }
}
