package net.therap.notestasks.converter;

import net.therap.notestasks.domain.TaskComment;
import net.therap.notestasks.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author tanmoy.das
 * @since 5/4/20
 */
@Component
public class StringToTaskCommentConverter implements Converter<String, TaskComment> {

    @Autowired
    private TaskService taskService;

    @Override
    public TaskComment convert(String id) {
        long taskCommentId = Long.parseLong(id);
        return taskService.findTaskCommentById(taskCommentId).orElse(null);
    }
}
