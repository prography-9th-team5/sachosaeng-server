package prography.team5.server.card.service.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.service.dto.CategoryResponse;

public record CategoryHotVotePreviewsResponse(CategoryResponse category, String description, List<SimpleVoteResponse> votes) {


    public static CategoryHotVotePreviewsResponse toHotVoteResponse(
            final Category category,
            final LocalDate startDate,
            final LocalDate endDate,
            final List<VoteCard> votes
    ) {
        return new CategoryHotVotePreviewsResponse(
                CategoryResponse.toResponseWith32px(category),
                formatDateRange(startDate, endDate),
                SimpleVoteResponse.toHotVoteResponse(votes)
        );
    }

    private static String formatDateRange(LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 d일");
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("d일");

        String formattedStartDate = startDate.format(formatter);
        String formattedEndDate;

        if (startDate.getMonth() == endDate.getMonth()) {
            formattedEndDate = endDate.format(dayFormatter);
        } else {
            formattedEndDate = endDate.format(formatter);
        }

        return formattedStartDate + " ~ " + formattedEndDate + " 인기 투표";
    }
}
