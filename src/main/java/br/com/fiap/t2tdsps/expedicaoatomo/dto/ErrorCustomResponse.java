package br.com.fiap.t2tdsps.expedicaoatomo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Erro com uma mensagem só, usado em 400 de formato, 404, 405, 409 e 500")
public record ErrorCustomResponse(
        @Schema(description = "Momento do erro", example = "2026-10-29T10:15:30.123")
        LocalDateTime timestamp,

        @Schema(description = "Código HTTP", example = "404")
        int status,

        @Schema(description = "Nome do código HTTP", example = "Not Found")
        String error,

        @Schema(description = "Explicação do erro em português", example = "Ponto do mapa com id 9999 não existe")
        String message
) {
}
