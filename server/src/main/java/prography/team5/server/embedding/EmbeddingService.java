package prography.team5.server.embedding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import prography.team5.server.card.domain.InformationCard;
import prography.team5.server.card.repository.InformationCardRepository;
import prography.team5.server.category.domain.Category;

@Service
@RequiredArgsConstructor
public class EmbeddingService {

    private final VectorStore vectorStore;
    private final InformationCardRepository informationCardRepository;
    private final EmbeddedInformationRepository embeddedInformationRepository;

    //트랜잭션X
    public synchronized void embed() {
        // embeddedInformationRepository에가 가장 마지막에 embedded된 information id를 가져온다.
        Long lastEmbeddedId = embeddedInformationRepository.findTopByOrderByIdDesc()
                .map(EmbeddedInformation::getInformationId)
                .orElse(0L);

        // 해당 id보다 큰 information card 부터 임베딩한다.
        List<InformationCard> cardsToEmbed = informationCardRepository.findByIdGreaterThan(lastEmbeddedId);
        if (cardsToEmbed.isEmpty()) {
            return;
        }

        List<Document> documents = new ArrayList<>();
        List<EmbeddedInformation> embeddedInformation = new ArrayList<>();
        for (InformationCard informationCard : cardsToEmbed) {
            for (Category category : informationCard.getCategories()) {
                final Document document = new Document(
                        informationCard.getTitle() + ":" + informationCard.getContent(),
                        Map.of("informationId", informationCard.getId(), "categoryId", category.getId(), "title",
                                informationCard.getTitle())
                );
                documents.add(document);
                embeddedInformation.add(new EmbeddedInformation(informationCard.getId()));
            }
        }

        vectorStore.add(documents);
        embeddedInformationRepository.saveAll(embeddedInformation);
    }
}
