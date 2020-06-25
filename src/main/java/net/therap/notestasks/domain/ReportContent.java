package net.therap.notestasks.domain;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author tanmoy.das
 * @since 4/22/20
 */
@Embeddable
public class ReportContent implements Serializable {

    private static final long serialVersionUID = 1;

    @NotEmpty
    @Size(min = 3, max = 1000)
    private String text;

    public ReportContent() {
        this.text = "";
    }

    public ReportContent(@NotEmpty @Size(min = 3, max = 1000) String text) {
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
