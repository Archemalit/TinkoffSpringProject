package com.archemalt.TinkoffSpringProject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TranslateTextTest {
    @Test
    void basePageTest(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk()).andExpect(view().name("base"));
    }

    @Test
    void translateEnRuWhitespaceTest(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/translate")
                        .param("slan", "en").param("rlan", "ru")
                        .param("sep", " ").param("t", "Hello World!"))
                        .andExpect(status().isOk()).andExpect(view().name("base"))
                        .andExpect(view().name("base"))
                        .andExpect(model().attribute("srcLanguage", "en"))
                        .andExpect(model().attribute("resultLanguage", "ru"))
                        .andExpect(model().attribute("resultText", "Привет Мир!"));
    }

    @Test
    void translateEnRuSemicolonTest(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/translate")
                .param("slan", "ru").param("rlan", "de")
                .param("sep", ";").param("t", "Это;рабочая;программа!"))
                .andExpect(status().isOk()).andExpect(view().name("base"))
                .andExpect(view().name("base"))
                .andExpect(model().attribute("srcLanguage", "ru"))
                .andExpect(model().attribute("resultLanguage", "de"))
                .andExpect(model().attribute("resultText", "Das;Arbeiten;Programm!"));
    }

    @Test
    void notCorrectTranslateParamsTest(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/translate?slan=ru&t=Hello")).andExpect(status().isBadRequest());
    }

    @Test
    void pageNotFoundTest(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/profile")).andExpect(status().isNotFound());
    }

    @Test
    void nullTextTest(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/translate?t=&slan=en&rlan=ru&sep=+")).andExpect(status().isInternalServerError());
    }


}
