package net.therap.notestasks.domain;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author tanmoy.das
 * @since 4/22/20
 */
@Embeddable
public class NoteContent implements Serializable {

    private static final long serialVersionUID = 1;

    @NotEmpty
    private String text;

    public NoteContent() {
        this.text = "";
    }

    public NoteContent(@NotEmpty String text) {
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
