package prography.team5.server.bookmark.service;

import static prography.team5.server.common.exception.ErrorType.INVALID_VOTE_CARD_ID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.bookmark.domain.Bookmark;
import prography.team5.server.bookmark.domain.BookmarkRepository;
import prography.team5.server.bookmark.domain.CardType;
import prography.team5.server.bookmark.service.dto.InformationCardBookmarkCreationRequest;
import prography.team5.server.bookmark.service.dto.VoteCardBookmarkCreationRequest;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.card.repository.VoteCardRepository;
import prography.team5.server.common.exception.SachosaengException;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final VoteCardRepository voteCardRepository;

    @Transactional
    public void createVoteCardBookmark(final Long userId, final VoteCardBookmarkCreationRequest request) {
        final VoteCard voteCard = voteCardRepository.findById(request.voteId())
                .orElseThrow(() -> new SachosaengException(INVALID_VOTE_CARD_ID));
        final Bookmark bookmark = new Bookmark(voteCard, userId, CardType.VOTE);
        bookmarkRepository.save(bookmark);
    }

    @Transactional
    public void createInformationCardBookmark(final Long userId, final InformationCardBookmarkCreationRequest request) {
        final VoteCard voteCard = voteCardRepository.findById(request.informationId())
                .orElseThrow(() -> new SachosaengException(INVALID_VOTE_CARD_ID));
        final Bookmark bookmark = new Bookmark(voteCard, userId, CardType.INFORMATION);
        bookmarkRepository.save(bookmark);
    }
}
