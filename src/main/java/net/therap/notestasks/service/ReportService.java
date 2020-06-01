package net.therap.notestasks.service;

import net.therap.notestasks.dao.ReportCommentDao;
import net.therap.notestasks.dao.ReportDao;
import net.therap.notestasks.dao.UserDao;
import net.therap.notestasks.domain.Report;
import net.therap.notestasks.domain.ReportComment;
import net.therap.notestasks.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author tanmoy.das
 * @since 4/24/20
 */
@Service
@Transactional
public class ReportService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private ReportCommentDao reportCommentDao;

    public void createReport(Report report) {
        reportDao.saveOrUpdate(report);

        User sender = report.getSender();
        sender.getSentReports().add(report);
        userDao.saveOrUpdate(sender);


        User target = report.getTarget();
        target.getTargetedReports().add(report);
        userDao.saveOrUpdate(target);
    }

    public void updateReport(Report report) {
        reportDao.saveOrUpdate(report);
    }

    public void deleteReport(Report report) {
        User sender = report.getSender();
        sender.getSentReports().remove(report);
        userDao.saveOrUpdate(sender);

        User target = report.getTarget();
        target.getTargetedReports().remove(report);
        userDao.saveOrUpdate(target);

        report.getComments().forEach(reportComment -> deleteReportComment(reportComment));

        reportDao.delete(report);
    }

    public void changeReportStatus(Report report, Report.ReportStatus reportStatus) {
        report.setReportStatus(reportStatus);
        reportDao.saveOrUpdate(report);
    }

    public void createReportComment(ReportComment reportComment) {
        reportCommentDao.saveOrUpdate(reportComment);

        Report report = reportComment.getReport();
        report.getComments().add(reportComment);
        reportDao.saveOrUpdate(report);

        User writer = reportComment.getWriter();
        writer.getReportComments().add(reportComment);
        userDao.saveOrUpdate(writer);
    }

    public void updateReportComment(ReportComment reportComment) {
        reportCommentDao.saveOrUpdate(reportComment);
    }

    public void deleteReportComment(ReportComment reportComment) {
        Report report = reportComment.getReport();
        report.getComments().remove(reportComment);
        reportDao.saveOrUpdate(report);

        User writer = reportComment.getWriter();
        writer.getReportComments().remove(reportComment);
        userDao.saveOrUpdate(writer);

        reportCommentDao.delete(reportComment);
    }
}
