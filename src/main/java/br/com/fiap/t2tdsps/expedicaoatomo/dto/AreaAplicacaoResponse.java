package br.com.fiap.t2tdsps.expedicaoatomo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Área de aplicação da tecnologia nuclear citada no enunciado do CTMSP")
public record AreaAplicacaoResponse(
        @Schema(description = "Id da área", example = "1")
        Long id,

        @Schema(description = "Nome da área", example = "Saúde")
        String nome,

        @Schema(description = "O que a área cobre, com um exemplo brasileiro",
                example = "Radiofármacos são remédios com substâncias radioativas que mostram em imagens como um órgão funciona e ajudam a tratar doenças como o câncer. A radioterapia usa radiação para destruir células de tumores. No Brasil, o IPEN, em São Paulo, fornece radiofármacos a 430 clínicas e hospitais.")
        String descricao
) {
}
