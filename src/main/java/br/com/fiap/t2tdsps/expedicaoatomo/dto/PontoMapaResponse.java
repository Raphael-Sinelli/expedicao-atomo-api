package br.com.fiap.t2tdsps.expedicaoatomo.dto;

import br.com.fiap.t2tdsps.expedicaoatomo.model.TipoPonto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Ponto do mapa como o app recebe, com o id e o nome da área")
public record PontoMapaResponse(
        @Schema(description = "Id do ponto", example = "2")
        Long id,

        @Schema(description = "Nome do lugar no mapa", example = "Reator Multipropósito Brasileiro (RMB)")
        String titulo,

        @Schema(description = "Texto de cerca de um minuto de leitura",
                example = "O RMB é um reator de pesquisa em obras desde 2025 em Iperó, ao lado do Centro Experimental Aramar, da Marinha. Com até 30 MW térmicos, vai produzir radioisótopos para mais de 30 tipos de radiofármacos. A criticalidade, primeira reação em cadeia sustentada do reator, está prevista para 2032.")
        String resumo,

        @Schema(description = "Fato concreto sobre o lugar; pode vir nulo",
                example = "Hoje o Brasil importa o molibdênio-99, que dá origem ao tecnécio-99m, usado em 80% dos procedimentos de medicina nuclear. Com o RMB, a CNEN prevê que o país fique autossuficiente.")
        String curiosidade,

        @Schema(description = "Tipo do ponto", example = "INSTALACAO")
        TipoPonto tipo,

        @Schema(description = "Cidade", example = "Iperó")
        String cidade,

        @Schema(description = "Sigla da UF", example = "SP")
        String uf,

        @Schema(description = "Latitude em graus decimais", example = "-23.390300")
        BigDecimal latitude,

        @Schema(description = "Longitude em graus decimais", example = "-47.621800")
        BigDecimal longitude,

        @Schema(description = "Link da fonte oficial",
                example = "https://www.gov.br/cnen/pt-br/rmb/os-avancos-para-a-saude-publica-no-brasil")
        String urlFonte,

        @Schema(description = "Data e hora em que o ponto foi cadastrado, gerada pelo servidor",
                example = "2026-10-29T10:15:30.123456")
        LocalDateTime dataCadastro,

        @Schema(description = "Id da área de aplicação", example = "1")
        Long areaId,

        @Schema(description = "Nome da área de aplicação", example = "Saúde")
        String areaNome
) {
}
