package prography.team5.server.bookmark.service;

import static prography.team5.server.common.exception.ErrorType.INVALID_VOTE_CARD_ID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.bookmark.domain.InformationCardBookmark;
import prography.team5.server.bookmark.domain.InformationCardBookmarkRepository;
import prography.team5.server.bookmark.domain.VoteCardBookmark;
import prography.team5.server.bookmark.domain.VoteCardBookmarkRepository;
import prography.team5.server.bookmark.service.dto.InformationCardBookmarkCreationRequest;
import prography.team5.server.bookmark.service.dto.VoteCardBookmarkCreationRequest;
import prography.team5.server.card.domain.InformationCard;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.card.repository.InformationCardRepository;
import prography.team5.server.card.repository.VoteCardRepository;
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
        //todo: 이미 북마크되어있다면 X
        final VoteCardBookmark voteCardBookmark = new VoteCardBookmark(voteCard, userId);
        voteCardBookmarkRepository.save(voteCardBookmark);
    }

    @Transactional
    public void createInformationCardBookmark(final Long userId, final InformationCardBookmarkCreationRequest request) {
        final InformationCard informationCard = informationCardRepository.findById(request.informationId())
                .orElseThrow(() -> new SachosaengException(INVALID_VOTE_CARD_ID));
        final InformationCardBookmark informationCardBookmark = new InformationCardBookmark(informationCard, userId);
        informationCardBookmarkRepository.save(informationCardBookmark);
    }
}
