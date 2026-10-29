package br.com.fiap.t2tdsps.expedicaoatomo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Área com a quantidade de pontos cadastrados, para os filtros do mapa no app")
public record AreaResumoResponse(
        @Schema(description = "Id da área", example = "1")
        Long id,

        @Schema(description = "Nome da área", example = "Saúde")
        String nome,

        @Schema(description = "Quantidade de pontos da área", example = "3")
        Long quantidadePontos
) {
}
