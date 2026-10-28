package br.com.fiap.t2tdsps.expedicaoatomo.dto;

import br.com.fiap.t2tdsps.expedicaoatomo.model.TipoPonto;
import br.com.fiap.t2tdsps.expedicaoatomo.validation.UfValida;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PontoMapaRequest(
        @NotBlank(message = "Título é obrigatório")
        @Size(max = 150, message = "Título deve ter no máximo 150 caracteres")
        String titulo,

        @NotBlank(message = "Resumo é obrigatório")
        @Size(max = 400, message = "Resumo deve ter no máximo 400 caracteres")
        String resumo,

        @Size(max = 400, message = "Curiosidade deve ter no máximo 400 caracteres")
        String curiosidade,

        @NotNull(message = "Tipo é obrigatório: INSTALACAO, NATURAL ou HISTORICO")
        TipoPonto tipo,

        @NotBlank(message = "Cidade é obrigatória")
        @Size(max = 80, message = "Cidade deve ter no máximo 80 caracteres")
        String cidade,

        @NotNull(message = "UF é obrigatória")
        @UfValida
        String uf,

        @NotNull(message = "Latitude é obrigatória")
        @DecimalMin(value = "-33.75", message = "Latitude deve estar entre -33.75 e 5.27, do Chuí (RS) ao Monte Caburaí (RR)")
        @DecimalMax(value = "5.27", message = "Latitude deve estar entre -33.75 e 5.27, do Chuí (RS) ao Monte Caburaí (RR)")
        @Digits(integer = 3, fraction = 6, message = "Latitude aceita no máximo 6 casas decimais")
        BigDecimal latitude,

        @NotNull(message = "Longitude é obrigatória")
        @DecimalMin(value = "-73.99", message = "Longitude deve estar entre -73.99 e -28.84, os limites do território brasileiro")
        @DecimalMax(value = "-28.84", message = "Longitude deve estar entre -73.99 e -28.84, os limites do território brasileiro")
        @Digits(integer = 3, fraction = 6, message = "Longitude aceita no máximo 6 casas decimais")
        BigDecimal longitude,

        @NotBlank(message = "URL da fonte é obrigatória")
        @Size(max = 500, message = "URL da fonte deve ter no máximo 500 caracteres")
        @Pattern(regexp = "^https://\\S+$", message = "URL da fonte deve começar com https:// e não pode ter espaços")
        String urlFonte,

        @NotNull(message = "idArea é obrigatório")
        @Positive(message = "idArea deve ser maior que zero")
        Long idArea
) {
}
