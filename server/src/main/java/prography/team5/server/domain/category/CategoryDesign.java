package prography.team5.server.domain.category;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CategoryDesign {

    private static final String DEFAULT_BACKGROUND_COLOR = "#000000";
    private static final String DEFAULT_TEXT_COLOR = "#FFFFFF";

    private String thumbnail;
    private String textColor;
    private String backgroundColor;

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTextColor() {
        if(textColor == null) {
            return DEFAULT_TEXT_COLOR;
        }
        return textColor;
    }

    public String getBackgroundColor() {
        if(backgroundColor == null) {
            return DEFAULT_BACKGROUND_COLOR;
        }
        return backgroundColor;
    }
}
