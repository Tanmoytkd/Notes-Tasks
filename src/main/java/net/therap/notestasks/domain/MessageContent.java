package net.therap.notestasks.domain;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

/**
 * @author tanmoy.das
 * @since 4/21/20
 */
@Embeddable
public class MessageContent {

    @NotEmpty
    private String text;

    public MessageContent() {
        this.text = "";
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
