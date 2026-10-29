package br.com.fiap.t2tdsps.expedicaoatomo.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AreaAplicacaoControllerTest {

    private final MockMvc mockMvc;

    AreaAplicacaoControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void listarDevolveAsCincoAreasEmOrdemAlfabetica() throws Exception {
        mockMvc.perform(get("/api/areas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].nome", is("Agricultura")))
                .andExpect(jsonPath("$[4].nome", is("Saúde")));
    }

    @Test
    void resumoSomaOsOnzePontosDeTeste() throws Exception {
        String json = mockMvc.perform(get("/api/areas/resumo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andReturn().getResponse().getContentAsString();

        List<Integer> quantidades = JsonPath.read(json, "$[*].quantidadePontos");
        assertThat(quantidades.stream().mapToInt(Integer::intValue).sum()).isEqualTo(11);
    }

    @Test
    void listarPontosDeAreaInexistenteDevolve404() throws Exception {
        mockMvc.perform(get("/api/areas/99/pontos"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Área de aplicação com id 99 não existe")));
    }
}
