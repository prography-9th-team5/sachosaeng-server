package prography.team5.server.bookmark.service;

import static prography.team5.server.common.exception.ErrorType.BOOKMARK_EXISTS;
import static prography.team5.server.common.exception.ErrorType.BOOKMARK_USER_NOT_SAME;
import static prography.team5.server.common.exception.ErrorType.EMPTY_VOTE_CARD_ID;
import static prography.team5.server.common.exception.ErrorType.INVALID_CATEGORY;
import static prography.team5.server.common.exception.ErrorType.INVALID_INFORMATION_CARD_ID;
import static prography.team5.server.common.exception.ErrorType.INVALID_VOTE_CARD_ID;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.bookmark.domain.InformationCardBookmark;
import prography.team5.server.bookmark.domain.InformationCardBookmarkRepository;
import prography.team5.server.bookmark.domain.VoteCardBookmark;
import prography.team5.server.bookmark.domain.VoteCardBookmarkRepository;
import prography.team5.server.bookmark.service.dto.InformationCardBookmarkCreationRequest;
import prography.team5.server.bookmark.service.dto.InformationCardBookmarkDeletionRequest;
import prography.team5.server.bookmark.service.dto.InformationCardBookmarkResponse;
import prography.team5.server.bookmark.service.dto.InformationCardBookmarkWithCursorResponse;
import prography.team5.server.bookmark.service.dto.VoteCardBookmarkCreationRequest;
import prography.team5.server.bookmark.service.dto.VoteCardBookmarkDeletionRequest;
import prography.team5.server.bookmark.service.dto.VoteCardBookmarkResponse;
import prography.team5.server.bookmark.service.dto.VoteCardBookmarkWithCursorResponse;
import prography.team5.server.card.domain.InformationCard;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.card.repository.InformationCardRepository;
import prography.team5.server.card.repository.VoteCardRepository;
import prography.team5.server.category.domain.Category;
import prography.team5.server.category.domain.CategoryRepository;
import prography.team5.server.category.service.dto.CategoryResponse;
import prography.team5.server.common.exception.SachosaengException;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookmarkService {

    private final VoteCardBookmarkRepository voteCardBookmarkRepository;
    private final VoteCardRepository voteCardRepository;
    private final InformationCardBookmarkRepository informationCardBookmarkRepository;
    private final InformationCardRepository informationCardRepository;
    private final CategoryRepository categoryRepository;
    private final VoteCardBookmarkDescription voteCardBookmarkDescription;

    @Transactional
    public void createVoteCardBookmark(final Long userId, final VoteCardBookmarkCreationRequest request) {
        log.info("voteId: {}", request.voteId());
        if(Objects.isNull(request.voteId())) {
            throw new SachosaengException(EMPTY_VOTE_CARD_ID);
        }
        final VoteCard voteCard = voteCardRepository.findById(request.voteId())
                .orElseThrow(() -> new SachosaengException(INVALID_VOTE_CARD_ID));
        if (voteCardBookmarkRepository.existsByVoteCardAndUserId(voteCard, userId)) {
            throw new SachosaengException(BOOKMARK_EXISTS);
        }
        final VoteCardBookmark voteCardBookmark = new VoteCardBookmark(voteCard, userId);
        voteCardBookmarkRepository.save(voteCardBookmark);
    }

    @Transactional
    public void deleteVoteCardBookmark(final Long userId, final Long voteId) {
        voteCardBookmarkRepository.deleteByUserIdAndVoteCardId(userId, voteId);
    }

    @Transactional
    public void deleteVoteCardBookmarks(final Long userId, final VoteCardBookmarkDeletionRequest request) {
        List<VoteCardBookmark> bookmarks = voteCardBookmarkRepository.findAllByIdIn(request.voteBookmarkIds());

        for (VoteCardBookmark bookmark : bookmarks) {
            if (!bookmark.getUserId().equals(userId)) {
                throw new SachosaengException(BOOKMARK_USER_NOT_SAME);
            }
        }
        voteCardBookmarkRepository.deleteAllByIdInBatch(request.voteBookmarkIds());
    }

    @Transactional(readOnly = true)
    public VoteCardBookmarkWithCursorResponse findVoteCardBookmark(final Long userId, Long lastCursor,
                                                                   final Integer size) {
        final Slice<VoteCardBookmark> bookmarks = findVoteCardBookmarksWithCursor(userId, lastCursor, size);
        final Map<Long, String> descriptions = voteCardBookmarkDescription.createDescriptions(
                bookmarks.stream().map(VoteCardBookmark::getVoteCard).toList());
        final List<VoteCardBookmark> content = bookmarks.getContent();
        final List<VoteCardBookmarkResponse> votes = VoteCardBookmarkResponse.toResponse(content, descriptions);
        return new VoteCardBookmarkWithCursorResponse(
                votes,
                bookmarks.hasNext(),
                content.isEmpty() ? null : content.getLast().getId()
        );
    }

    private Slice<VoteCardBookmark> findVoteCardBookmarksWithCursor(final Long userId, Long lastCursor,
                                                                    final Integer size) {
        if (Objects.isNull(lastCursor)) {
            return voteCardBookmarkRepository.findLatestBookmarks(userId, PageRequest.ofSize(size));
        }
        return voteCardBookmarkRepository.findBookmarksBeforeCursor(
                userId,
                lastCursor,
                PageRequest.ofSize(size)
        );
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

    @Transactional(readOnly = true)
    public VoteCardBookmarkWithCursorResponse findVoteCardBookmarkByCategory(
            final Long userId,
            final Long categoryId,
            final Long lastCursor,
            final Integer size
    ) {
        final Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new SachosaengException(INVALID_CATEGORY));

        Slice<VoteCardBookmark> slice = findVoteCardBookmarksWithCategoryAndCursor(userId, category, lastCursor, size);
        final List<VoteCardBookmark> bookmarks = slice.getContent();
        final Map<Long, String> descriptions = voteCardBookmarkDescription.createDescriptions(
                bookmarks.stream().map(VoteCardBookmark::getVoteCard).toList());
        final List<VoteCardBookmarkResponse> votes = VoteCardBookmarkResponse.toResponse(bookmarks, descriptions);
        return new VoteCardBookmarkWithCursorResponse(
                votes,
                slice.hasNext(),
                bookmarks.isEmpty() ? null : bookmarks.getLast().getId()
        );
    }

    private Slice<VoteCardBookmark> findVoteCardBookmarksWithCategoryAndCursor(final Long userId,
                                                                               final Category category,
                                                                               final Long lastCursor,
                                                                               final Integer size) {
        if (Objects.isNull(lastCursor)) {
            return voteCardBookmarkRepository.findLatestBookmarksByUserIdAndCategory(userId, category,
                    PageRequest.ofSize(size));
        }
        return voteCardBookmarkRepository.findLatestBookmarksByUserIdAndCategoryBeforeCursor(userId, category,
                lastCursor, PageRequest.ofSize(size));
    }

    @Transactional
    public void createInformationCardBookmark(final Long userId, final InformationCardBookmarkCreationRequest request) {
        final InformationCard informationCard = informationCardRepository.findById(request.informationId())
                .orElseThrow(() -> new SachosaengException(INVALID_INFORMATION_CARD_ID));
        if (informationCardBookmarkRepository.existsByInformationCardAndUserId(informationCard, userId)) {
            throw new SachosaengException(BOOKMARK_EXISTS);
        }
        final InformationCardBookmark informationCardBookmark = new InformationCardBookmark(informationCard, userId);
        informationCardBookmarkRepository.save(informationCardBookmark);
    }

    @Transactional
    public void deleteInformationCardBookmark(final Long userId, final Long informationId) {
        informationCardBookmarkRepository.deleteByUserIdAndInformationCardId(userId, informationId);
    }

    @Transactional
    public void deleteInformationCardBookmarks(final Long userId,
                                               final InformationCardBookmarkDeletionRequest request) {
        List<InformationCardBookmark> bookmarks = informationCardBookmarkRepository.findAllByIdIn(
                request.informationBookmarkIds());

        for (InformationCardBookmark bookmark : bookmarks) {
            if (!bookmark.getUserId().equals(userId)) {
                throw new SachosaengException(BOOKMARK_USER_NOT_SAME);
            }
        }
        informationCardBookmarkRepository.deleteAllByIdInBatch(request.informationBookmarkIds());
    }

    @Transactional(readOnly = true)
    public InformationCardBookmarkWithCursorResponse findInformationCardBookmark(
            final Long userId,
            final Long cursor,
            final Integer size
    ) {
        Slice<InformationCardBookmark> slice = findInformationCardBookmarkWithCursor(userId, cursor, size);
        final List<InformationCardBookmark> bookmarks = slice.getContent();
        final List<InformationCardBookmarkResponse> information = InformationCardBookmarkResponse.toResponse(bookmarks);
        return new InformationCardBookmarkWithCursorResponse(
                information,
                slice.hasNext(),
                bookmarks.isEmpty() ? null : bookmarks.getLast().getId()
        );
    }

    private Slice<InformationCardBookmark> findInformationCardBookmarkWithCursor(
            final Long userId,
            final Long cursor,
            final Integer size
    ) {
        if (Objects.isNull(cursor)) {
            return informationCardBookmarkRepository.findLatestBookmarks(userId, PageRequest.ofSize(size));
        }
        return informationCardBookmarkRepository.findBookmarksBeforeCursor(userId, cursor, PageRequest.ofSize(size));
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> findInformationCardBookmarkCategories(final Long userId) {
        final List<InformationCardBookmark> bookmarks = informationCardBookmarkRepository.findAllByUserId(userId);
        List<Category> categories = bookmarks.stream()
                .flatMap(each -> each.getInformationCard().getCategories().stream())
                .distinct()
                .sorted(Comparator.comparing(Category::getPriority))
                .toList();
        return CategoryResponse.toResponse(categories);
    }

    @Transactional(readOnly = true)
    public InformationCardBookmarkWithCursorResponse findInformationCardBookmarkByCategory(
            final Long userId,
            final Long categoryId,
            final Long lastCursor,
            final Integer size
    ) {
        final Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new SachosaengException(INVALID_CATEGORY));
        final Slice<InformationCardBookmark> slice = findInformationCardBookmarkWithCategoriesAndCursor(
                userId, category, lastCursor, size);

        final List<InformationCardBookmark> bookmarks = slice.getContent();
        final List<InformationCardBookmarkResponse> information = InformationCardBookmarkResponse.toResponse(bookmarks);
        return new InformationCardBookmarkWithCursorResponse(
                information,
                slice.hasNext(),
                bookmarks.isEmpty() ? null : bookmarks.getLast().getId()
        );
    }

    private Slice<InformationCardBookmark> findInformationCardBookmarkWithCategoriesAndCursor(
            final Long userId,
            final Category category,
            final Long cursor,
            final Integer size
    ) {
        if (Objects.isNull(cursor)) {
            return informationCardBookmarkRepository.findLatestBookmarksByUserIdAndCategory(userId, category, PageRequest.ofSize(size));
        }
        return informationCardBookmarkRepository.findLatestBookmarksByUserIdAndCategoryBeforeCursor(userId, cursor, category, PageRequest.ofSize(size));
    }
}
