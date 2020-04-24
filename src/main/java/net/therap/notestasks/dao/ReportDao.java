package net.therap.notestasks.dao;

import net.therap.notestasks.domain.Report;
import org.springframework.stereotype.Repository;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@Repository
public class ReportDao extends GenericDao<Report> {

    protected ReportDao() {
        super(Report.class);
    }
}
