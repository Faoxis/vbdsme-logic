package ru.vbdsme.vbdsmelogic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.vbdsme.vbdsmelogic.controller.CategoryController;
import ru.vbdsme.vbdsmelogic.controller.ItemController;
import ru.vbdsme.vbdsmelogic.domain.Category;
import ru.vbdsme.vbdsmelogic.domain.Item;
import ru.vbdsme.vbdsmelogic.repository.CategoryRepository;
import ru.vbdsme.vbdsmelogic.repository.ItemRepository;

import javax.transaction.Transactional;
import java.time.Duration;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.*;

@ContextConfiguration(initializers = {TestBDSM.Initialize.class})
@SpringBootTest(webEnvironment = RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class TestBDSM {

    private ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .modules(new JavaTimeModule())
            .build();

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer =
            (PostgreSQLContainer) new PostgreSQLContainer("postgres:9.6")
                    .withDatabaseName("vbdsme")
                    .withUsername("postgres")
                    .withPassword("postgres")
                    .withStartupTimeout(Duration.ofSeconds(60));


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testSavingCategoryAndItem() throws Exception {

        // saving category
        Category categoryToSave = new Category()
                .setName("Super hard");

        Category savedCategory = makeSuccessPostRequest(CategoryController.URL, categoryToSave, Category.class);
        assertEquals(categoryToSave.getName(), savedCategory.getName());

        boolean isCategoryInDB = categoryRepository
                .findByName(categoryToSave.getName())
                .isPresent();
        assertTrue(isCategoryInDB);

        // Saving item
        Item itemToSave = new Item()
                .setName("Bloody J2EE")
                .setPrice(10000)
                .setQuantity(100)
                .setCategories(Stream.of(savedCategory).collect(Collectors.toSet()));

        Item savedItem = makeSuccessPostRequest(ItemController.URL, itemToSave, Item.class);

        assertEquals(itemToSave.getName(), savedItem.getName());
        assertEquals(itemToSave.getPrice(), savedItem.getPrice());
        assertEquals(itemToSave.getQuantity(), savedItem.getQuantity());

        boolean isItemInDB = itemRepository
                .findByName(savedItem.getName())
                .isPresent();
        assertTrue(isItemInDB);

    }

    private <T> T makeSuccessPostRequest(String url, T request, Class<T> clazz) throws Exception {
        String resultJson = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertNotNull(resultJson);

        return objectMapper.readValue(resultJson, clazz);
    }


    static class Initialize
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {


        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(applicationContext.getEnvironment());
        }
    }

}
