package at.ac.tuwien.sepr.groupphase.backend.unittests;

import at.ac.tuwien.sepr.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.DetailedMessageDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.SimpleMessageDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.MessageMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.Message;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class MessageMappingTest implements TestData {

    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", postgres::getJdbcUrl);
        propertyRegistry.add("spring.datasource.username", postgres::getUsername);
        propertyRegistry.add("spring.datasource.password", postgres::getPassword);
    }

    private final Message message = Message.MessageBuilder.aMessage()
                                                          .withId(ID)
                                                          .withTitle(TEST_NEWS_TITLE)
                                                          .withSummary(TEST_NEWS_SUMMARY)
                                                          .withText(TEST_NEWS_TEXT)
                                                          .withPublishedAt(TEST_NEWS_PUBLISHED_AT)
                                                          .build();
    @Autowired
    private MessageMapper messageMapper;

    @Test
    void givenNothing_whenMapDetailedMessageDtoToEntity_thenEntityHasAllProperties() {
        DetailedMessageDto detailedMessageDto = messageMapper.messageToDetailedMessageDto(message);
        assertAll(
            () -> assertEquals(ID, detailedMessageDto.getId()),
            () -> assertEquals(TEST_NEWS_TITLE, detailedMessageDto.getTitle()),
            () -> assertEquals(TEST_NEWS_SUMMARY, detailedMessageDto.getSummary()),
            () -> assertEquals(TEST_NEWS_TEXT, detailedMessageDto.getText()),
            () -> assertEquals(TEST_NEWS_PUBLISHED_AT, detailedMessageDto.getPublishedAt()));
    }

    @Test
    void givenNothing_whenMapListWithTwoMessageEntitiesToSimpleDto_thenGetListWithSizeTwoAndAllProperties() {
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        messages.add(message);

        List<SimpleMessageDto> simpleMessageDtos = messageMapper.messageToSimpleMessageDto(messages);
        assertEquals(2, simpleMessageDtos.size());
        SimpleMessageDto simpleMessageDto = simpleMessageDtos.get(0);
        assertAll(
            () -> assertEquals(ID, simpleMessageDto.getId()),
            () -> assertEquals(TEST_NEWS_TITLE, simpleMessageDto.getTitle()),
            () -> assertEquals(TEST_NEWS_SUMMARY, simpleMessageDto.getSummary()),
            () -> assertEquals(TEST_NEWS_PUBLISHED_AT, simpleMessageDto.getPublishedAt()));
    }
}
