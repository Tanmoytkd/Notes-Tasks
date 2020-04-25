package net.therap.notestasks.domain;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

/**
 * @author tanmoy.das
 * @since 4/22/20
 */
@Embeddable
public class CommentContent {

    @NotEmpty
    private String text;

    public CommentContent() {
        this.text = "";
    }

    public CommentContent(@NotEmpty String text) {
        this.text = text;
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
