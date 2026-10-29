package br.com.fiap.t2tdsps.expedicaoatomo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Erro de validação com a lista de todos os campos ou parâmetros inválidos")
public record ValidationErrorResponse(
        @Schema(description = "Momento do erro", example = "2026-10-29T10:15:30.123")
        LocalDateTime timestamp,

        @Schema(description = "Código HTTP", example = "400")
        int status,

        @Schema(description = "Nome do código HTTP", example = "Bad Request")
        String error,

        @Schema(description = "Um item por campo ou parâmetro que falhou na validação")
        List<ErrorFieldCustom> errors
) {
}
