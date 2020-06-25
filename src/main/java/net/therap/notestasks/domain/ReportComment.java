package net.therap.notestasks.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author tanmoy.das
 * @since 4/22/20
 */
@NamedQueries({
        @NamedQuery(name = "ReportComment.findAll",
                query = "FROM ReportComment reportComment WHERE reportComment.deleted = false")
})
@Entity
@Table(name = "report_comment")
public class ReportComment extends Comment implements Serializable {

    private static final long serialVersionUID = 1;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "report_id")
    private Report report;

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
