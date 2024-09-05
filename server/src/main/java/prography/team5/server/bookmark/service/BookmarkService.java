package prography.team5.server.bookmark.service;

import static prography.team5.server.common.exception.ErrorType.BOOKMARK_EXISTS;
import static prography.team5.server.common.exception.ErrorType.INVALID_INFORMATION_CARD_ID;
import static prography.team5.server.common.exception.ErrorType.INVALID_VOTE_CARD_ID;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.bookmark.domain.InformationCardBookmark;
import prography.team5.server.bookmark.domain.InformationCardBookmarkRepository;
import prography.team5.server.bookmark.domain.VoteCardBookmark;
import prography.team5.server.bookmark.domain.VoteCardBookmarkRepository;
import prography.team5.server.bookmark.service.dto.InformationCardBookmarkCreationRequest;
import prography.team5.server.bookmark.service.dto.VoteCardBookmarkCreationRequest;
import prography.team5.server.bookmark.service.dto.VoteCardBookmarkResponse;
import prography.team5.server.card.domain.InformationCard;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.card.repository.InformationCardRepository;
import prography.team5.server.card.repository.VoteCardRepository;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.service.dto.CategoryResponse;
import prography.team5.server.common.exception.SachosaengException;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final VoteCardBookmarkRepository voteCardBookmarkRepository;
    private final VoteCardRepository voteCardRepository;
    private final InformationCardBookmarkRepository informationCardBookmarkRepository;
    private final InformationCardRepository informationCardRepository;

    @Transactional
    public void createVoteCardBookmark(final Long userId, final VoteCardBookmarkCreationRequest request) {
        final VoteCard voteCard = voteCardRepository.findById(request.voteId())
                .orElseThrow(() -> new SachosaengException(INVALID_VOTE_CARD_ID));
        if(voteCardBookmarkRepository.existsByVoteCardAndUserId(voteCard, userId)) {
            throw new SachosaengException(BOOKMARK_EXISTS);
        }
        final VoteCardBookmark voteCardBookmark = new VoteCardBookmark(voteCard, userId);
        voteCardBookmarkRepository.save(voteCardBookmark);
    }

    @Transactional(readOnly = true)
    public List<VoteCardBookmarkResponse> findVoteCardBookmark(final Long userId) {
        final List<VoteCardBookmark> bookmarks = voteCardBookmarkRepository.findAllByUserId(
                userId,
                Sort.by(Direction.DESC, "id")
        );
        return VoteCardBookmarkResponse.toResponse(bookmarks);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> findVoteCardBookmarkCategories(final Long userId) {
        final List<VoteCardBookmark> bookmarks = voteCardBookmarkRepository.findAllByUserId(userId);
        List<Category> categories = bookmarks.stream()
                .flatMap(each -> each.getVoteCard().getCategories().stream())
                .distinct()
                .sorted(Comparator.comparing(Category::getPriority))
                .toList();
        return CategoryResponse.toResponse(categories);
    }

    @Transactional
    public void createInformationCardBookmark(final Long userId, final InformationCardBookmarkCreationRequest request) {
        final InformationCard informationCard = informationCardRepository.findById(request.informationId())
                .orElseThrow(() -> new SachosaengException(INVALID_INFORMATION_CARD_ID));
        if(informationCardBookmarkRepository.existsByInformationCardAndUserId(informationCard, userId)) {
            throw new SachosaengException(BOOKMARK_EXISTS);
        }
        final InformationCardBookmark informationCardBookmark = new InformationCardBookmark(informationCard, userId);
        informationCardBookmarkRepository.save(informationCardBookmark);
    }

}
