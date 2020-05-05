package net.therap.notestasks.converter;

import net.therap.notestasks.domain.TaskAssignment;
import net.therap.notestasks.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author tanmoy.das
 * @since 5/4/20
 */
@Component
public class StringToTaskAssignmentConverter implements Converter<String, TaskAssignment> {

    @Autowired
    private TaskService taskService;

    @Override
    public TaskAssignment convert(String id) {
        long taskAssignmentId = Long.parseLong(id);
        return taskService.findTaskAssignmentById(taskAssignmentId).orElse(null);
    }
}
