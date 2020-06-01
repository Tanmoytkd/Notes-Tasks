package net.therap.notestasks.converter;

import net.therap.notestasks.domain.Message;
import net.therap.notestasks.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author tanmoy.das
 * @since 5/4/20
 */
@Component
public class StringToMessageConverter implements Converter<String, Message> {

    @Autowired
    private MessageService messageService;

    @Override
    public Message convert(String id) {

        long messageId = Long.parseLong(id);
        return messageService.findMessageById(messageId).orElse(null);
    }
}
