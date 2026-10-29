package br.com.fiap.t2tdsps.expedicaoatomo.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PontoMapaControllerTest {

    private static final String PONTO_VALIDO = """
            {"titulo": "%s",
             "resumo": "Reator de pesquisa do Instituto de Engenharia Nuclear, no campus da UFRJ na Ilha do Fundão.",
             "curiosidade": "O projeto foi doado ao Brasil pelo programa Átomos pela Paz.",
             "tipo": "INSTALACAO", "cidade": "Rio de Janeiro", "uf": "RJ",
             "latitude": -22.8656, "longitude": -43.2266,
             "urlFonte": "https://www.gov.br/ien/pt-br/instalacoes/reator-nuclear-de-pesquisa-argonauta",
             "idArea": 4}
            """;

    private final MockMvc mockMvc;

    PontoMapaControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void buscaPorReatorEncontraRmbETriga() throws Exception {
        mockMvc.perform(get("/api/pontos/busca").param("termo", "reator"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is(2)))
                .andExpect(jsonPath("$.content[*].titulo", containsInAnyOrder(
                        "Reator Multipropósito Brasileiro (RMB)", "Reator de pesquisa TRIGA do CDTN")));
    }

    @Test
    void filtroPorUfDevolveSoPontosDeSaoPaulo() throws Exception {
        mockMvc.perform(get("/api/pontos").param("uf", "SP"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is(4)))
                .andExpect(jsonPath("$.content[*].uf", everyItem(is("SP"))));
    }

    @Test
    void criarDevolve201ComLocation() throws Exception {
        mockMvc.perform(post("/api/pontos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PONTO_VALIDO.formatted("Reator Argonauta do IEN")))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", matchesPattern("/api/pontos/\\d+")))
                .andExpect(jsonPath("$.titulo", is("Reator Argonauta do IEN")))
                .andExpect(jsonPath("$.areaNome", is("Pesquisa Científica")));
    }

    @Test
    void criarComCamposInvalidosDevolve400ComListaDeCampos() throws Exception {
        String invalido = PONTO_VALIDO.formatted("Ponto inválido")
                .replace("\"uf\": \"RJ\"", "\"uf\": \"XX\"")
                .replace("\"latitude\": -22.8656", "\"latitude\": 10")
                .replace("https://www.gov.br", "http://www.gov.br");

        mockMvc.perform(post("/api/pontos").contentType(MediaType.APPLICATION_JSON).content(invalido))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[*].field", containsInAnyOrder("uf", "latitude", "urlFonte")));
    }

    @Test
    void criarComTituloRepetidoEmOutraCaixaDevolve409() throws Exception {
        mockMvc.perform(post("/api/pontos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PONTO_VALIDO.formatted("RADIOFÁRMACOS DO IPEN")))
                .andExpect(status().isConflict());
    }

    @Test
    void buscarIdInexistenteDevolve404() throws Exception {
        mockMvc.perform(get("/api/pontos/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Ponto do mapa com id 9999 não existe")));
    }

    @Test
    void buscarIdZeroDevolve400() throws Exception {
        mockMvc.perform(get("/api/pontos/0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field", is("id")));
    }

    @Test
    void atualizarDevolve200EMantemDataCadastro() throws Exception {
        String antes = mockMvc.perform(get("/api/pontos/1"))
                .andReturn().getResponse().getContentAsString();
        String dataCadastro = JsonPath.read(antes, "$.dataCadastro");

        mockMvc.perform(put("/api/pontos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PONTO_VALIDO.formatted("Radiofármacos do IPEN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.cidade", is("Rio de Janeiro")))
                .andExpect(jsonPath("$.dataCadastro", is(dataCadastro)));
    }

    @Test
    void excluirDevolve204EDepoisOPontoNaoExiste() throws Exception {
        mockMvc.perform(delete("/api/pontos/11"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/pontos/11"))
                .andExpect(status().isNotFound());
    }
}
