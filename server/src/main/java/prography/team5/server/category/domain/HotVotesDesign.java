package prography.team5.server.category.domain;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class HotVotesDesign {

    private final String name;
    private final String iconUrl;
    private final String textColor;

    public HotVotesDesign(
            @Value("${design.hot-vote.name}") final String name,
            @Value("${design.hot-vote.icon-url}") final String iconUrl,
            @Value("${design.hot-vote.text-color}") final String textColor) {
        this.name = name;
        this.iconUrl = iconUrl;
        this.textColor = textColor;
    }
}
