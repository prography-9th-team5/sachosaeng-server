package prography.team5.server.category.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CategoryDesign {

    private static final String DEFAULT_BACKGROUND_COLOR = "#D8D8D8";
    private static final String DEFAULT_TEXT_COLOR = "#000000";

    private String iconUrl;
    private String textColor;
    private String backgroundColor;

    public String getIconUrl() {
        return iconUrl;
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
