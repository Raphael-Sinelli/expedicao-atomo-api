package br.com.fiap.t2tdsps.expedicaoatomo.dto;

import br.com.fiap.t2tdsps.expedicaoatomo.model.TipoPonto;
import br.com.fiap.t2tdsps.expedicaoatomo.validation.UfValida;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Dados para cadastrar ou atualizar um ponto do mapa. O id e a data de cadastro são gerados pelo servidor.")
public record PontoMapaRequest(
        @Schema(description = "Nome do lugar no mapa. Não pode repetir o de outro ponto, sem diferenciar maiúsculas",
                example = "Reator Argonauta do IEN")
        @NotBlank(message = "Título é obrigatório")
        @Size(max = 150, message = "Título deve ter no máximo 150 caracteres")
        String titulo,

        @Schema(description = "Texto de cerca de um minuto de leitura que explica o lugar",
                example = "O Argonauta, no Instituto de Engenharia Nuclear, no campus da UFRJ na Ilha do Fundão, é um reator de pesquisa que atingiu a primeira criticalidade em 20 de fevereiro de 1965. Foi o primeiro reator do país construído e montado por empresas nacionais.")
        @NotBlank(message = "Resumo é obrigatório")
        @Size(max = 400, message = "Resumo deve ter no máximo 400 caracteres")
        String resumo,

        @Schema(description = "Fato concreto sobre o lugar. Campo opcional",
                example = "O projeto do reator foi doado ao Brasil pelo programa Átomos pela Paz, dos Estados Unidos.")
        @Size(max = 400, message = "Curiosidade deve ter no máximo 400 caracteres")
        String curiosidade,

        @Schema(description = "INSTALACAO para instalação nuclear, NATURAL para radiação natural e HISTORICO para fato histórico",
                example = "INSTALACAO")
        @NotNull(message = "Tipo é obrigatório: INSTALACAO, NATURAL ou HISTORICO")
        TipoPonto tipo,

        @Schema(description = "Cidade onde fica o ponto", example = "Rio de Janeiro")
        @NotBlank(message = "Cidade é obrigatória")
        @Size(max = 80, message = "Cidade deve ter no máximo 80 caracteres")
        String cidade,

        @Schema(description = "Sigla oficial da unidade da federação, em maiúsculas", example = "RJ")
        @NotNull(message = "UF é obrigatória")
        @UfValida
        String uf,

        @Schema(description = "Latitude em graus decimais, dentro do território brasileiro", example = "-22.8656")
        @NotNull(message = "Latitude é obrigatória")
        @DecimalMin(value = "-33.75", message = "Latitude deve estar entre -33.75 e 5.27, do Chuí (RS) ao Monte Caburaí (RR)")
        @DecimalMax(value = "5.27", message = "Latitude deve estar entre -33.75 e 5.27, do Chuí (RS) ao Monte Caburaí (RR)")
        @Digits(integer = 3, fraction = 6, message = "Latitude aceita no máximo 6 casas decimais")
        BigDecimal latitude,

        @Schema(description = "Longitude em graus decimais, dentro do território brasileiro", example = "-43.2266")
        @NotNull(message = "Longitude é obrigatória")
        @DecimalMin(value = "-73.99", message = "Longitude deve estar entre -73.99 e -28.84, os limites do território brasileiro")
        @DecimalMax(value = "-28.84", message = "Longitude deve estar entre -73.99 e -28.84, os limites do território brasileiro")
        @Digits(integer = 3, fraction = 6, message = "Longitude aceita no máximo 6 casas decimais")
        BigDecimal longitude,

        @Schema(description = "Link https da fonte oficial que sustenta o resumo",
                example = "https://www.gov.br/ien/pt-br/instalacoes/reator-nuclear-de-pesquisa-argonauta")
        @NotBlank(message = "URL da fonte é obrigatória")
        @Size(max = 500, message = "URL da fonte deve ter no máximo 500 caracteres")
        @Pattern(regexp = "^https://\\S+$", message = "URL da fonte deve começar com https:// e não pode ter espaços")
        String urlFonte,

        @Schema(description = "Id da área de aplicação: 1 Saúde, 2 Indústria, 3 Agricultura, 4 Pesquisa Científica, 5 Energia",
                example = "4")
        @NotNull(message = "idArea é obrigatório")
        @Positive(message = "idArea deve ser maior que zero")
        Long idArea
) {
}
