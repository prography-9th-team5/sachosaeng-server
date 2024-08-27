package prography.team5.server.embedding.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import prography.team5.server.card.domain.VoteCard;
import prography.team5.server.card.repository.VoteCardRepository;
import prography.team5.server.embedding.service.dto.SimilarInformationResponse;

@Service
@RequiredArgsConstructor
public class SimilarInformationQueryService {

    private final VectorStore vectorStore;
    private final VoteCardRepository voteCardRepository;

    //트랜잭션X
    @Cacheable(value = "similarInformationCache", key = "#voteId + '-' + #categoryId + '-' + #topSize")
    public List<SimilarInformationResponse> querySimilarInformation(final long voteId, final long categoryId,
                                                                    final int topSize) {
        final VoteCard voteCard = voteCardRepository.findById(voteId).orElseThrow();
        String query = voteCard.getTitle();

        System.out.println("캐시 안됨!");

        List<Document> results = vectorStore.similaritySearch(
                SearchRequest.defaults()
                        .withQuery(query)
                        .withTopK(topSize)
                        .withFilterExpression("categoryId == " + categoryId));

        results = new ArrayList<>(results);

        results.sort((d1, d2) -> {
            Float distance1 = ((Number) d1.getMetadata().get("distance")).floatValue();
            Float distance2 = ((Number) d2.getMetadata().get("distance")).floatValue();
            return distance2.compareTo(distance1); // 내림차순 정렬
        });

        List<SimilarInformationResponse> responses = new ArrayList<>();
        for (Document docs : results) {
            final Map<String, Object> metadata = docs.getMetadata();
            final SimilarInformationResponse info = new SimilarInformationResponse(
                    ((Number) metadata.get("informationId")).longValue(), (String) metadata.get("title")
            );
            responses.add(info);
        }
        return responses;
    }
}
