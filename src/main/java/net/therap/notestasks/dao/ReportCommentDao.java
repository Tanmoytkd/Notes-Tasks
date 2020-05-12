package net.therap.notestasks.dao;

import net.therap.notestasks.domain.ReportComment;
import org.springframework.stereotype.Repository;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@Repository
public class ReportCommentDao extends GenericDao<ReportComment> {

    public ReportCommentDao() {
        super(ReportComment.class);
    }
}
