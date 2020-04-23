package net.therap.notestasks.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author tanmoy.das
 * @since 4/22/20
 */
@Entity
public class ReportComment extends Comment {

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
