package net.therap.notestasks.helper;

import net.therap.notestasks.domain.Note;
import net.therap.notestasks.domain.Report;
import net.therap.notestasks.domain.Task;
import net.therap.notestasks.domain.User;

/**
 * @author tanmoy.das
 * @since 5/4/20
 */
public class UrlHelper {

    public static String redirectTo(String url) {
        return "redirect:" + url;
    }

    public static String getUrl(Note note) {
        return "/note/" + note.getId();
    }

    public static String getUrl(User user) {
        return "/profile/" + user.getId();
    }

    public static String getUrl(Task task) {
        return "/task/"+task.getId();
    }

    public static String getUrl(Report report) {
        return "/task/"+report.getId();
    }

    public static String getMessageUrl(User user) {
        return "/messages/"+user.getId();
    }


}
