package lk.ubiquitouslibrary.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lk.ubiquitouslibrary.entity.BookBuy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@Slf4j
public class GoogleBooksRepository {

    RestTemplate restTemplate;

    public GoogleBooksRepository() {
        restTemplate = new RestTemplate();
    }

    public List<BookBuy> searchBook(String searchString){
        JsonNode jsonNode = restTemplate.getForObject("https://www.googleapis.com/books/v1/volumes?q={q}", JsonNode.class, Map.of("q",searchString));
        ArrayNode items = (ArrayNode) jsonNode.get("items");

        return StreamSupport.stream(items.spliterator(),false)
                .map(bookNode -> {
                    JsonNode volumeInfo = bookNode.get("volumeInfo");
                    return BookBuy.builder()
                            .title(volumeInfo.get("title").asText())
                            .isbn(getISBN(volumeInfo))
                            .description(getTextFromNode(volumeInfo,"description"))
                            .publisher(getTextFromNode(volumeInfo,"publisher"))
                            .publishYear(Objects.nonNull(volumeInfo.get("publishedDate"))?volumeInfo.get("publishedDate").asInt():null)
                            .author(getAuthors(volumeInfo))
                            .imageUrl(getTextFromNode(volumeInfo.get("imageLinks"),"thumbnail"))
                            .build();
                })
                        .collect(Collectors.toList());
    }

    private String getTextFromNode(JsonNode jsonNode,String field){
        if (jsonNode==null)
            return null;
        return Objects.nonNull(jsonNode.get(field))?jsonNode.get(field).asText():"";
    }

    private String getAuthors(JsonNode volumeInfo){
        StringBuilder stringBuilder=new StringBuilder();

        if (Objects.nonNull(volumeInfo.get("authors")))
        StreamSupport.stream(volumeInfo.get("authors").spliterator(),false)
                .forEach(jsonNode -> {
                    stringBuilder.append(jsonNode.asText()).append("; ");
                });

        return stringBuilder.toString();
    }

    private String getISBN(JsonNode volumeInfo){
        if (Objects.nonNull(volumeInfo.get("industryIdentifiers"))) {
            Optional<JsonNode> optional = StreamSupport.stream(volumeInfo.get("industryIdentifiers").spliterator(), false)
                    .filter(jsonNode -> jsonNode.get("type").asText().toUpperCase().contains("ISBN")).max((o1, o2) ->
                            o1.get("type").asText().compareToIgnoreCase(o2.get("type").asText()));
            return optional.map(jsonNode -> jsonNode.get("identifier").asText()).orElse(null);
        }else
            return null;
    }

}
