package net.therap.notestasks.converter;

import net.therap.notestasks.domain.Task;
import net.therap.notestasks.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author tanmoy.das
 * @since 5/4/20
 */
@Component
public class StringToTaskConverter implements Converter<String, Task> {

    @Autowired
    private TaskService taskService;

    @Override
    public Task convert(String id) {
        long taskId = Long.parseLong(id);
        return taskService.findTaskById(taskId).orElse(null);
    }
}
