package prography.team5.server.category.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class IconData {

    private final String allIconUrl;
    private final String allIconBackgroundColor;

    public IconData(
            @Value("${design.all-icon.icon-url}") final String allIconUrl,
            @Value("${design.all-icon.background-color}") final String allIconBackgroundColor) {
        this.allIconUrl = allIconUrl;
        this.allIconBackgroundColor = allIconBackgroundColor;
    }
}
