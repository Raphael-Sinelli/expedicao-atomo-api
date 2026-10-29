package br.com.fiap.t2tdsps.expedicaoatomo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Um campo ou parâmetro inválido e o motivo")
public record ErrorFieldCustom(
        @Schema(description = "Nome do campo do corpo ou do parâmetro da URL", example = "uf")
        String field,

        @Schema(description = "O que está errado e como corrigir",
                example = "UF deve ser uma das 27 siglas oficiais em maiúsculas, como SP")
        String message
) {
}
