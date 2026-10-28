package br.com.fiap.t2tdsps.expedicaoatomo.dto;

import br.com.fiap.t2tdsps.expedicaoatomo.model.TipoPonto;

import java.math.BigDecimal;

public record PontoMapaRequest(
        String titulo,
        String resumo,
        String curiosidade,
        TipoPonto tipo,
        String cidade,
        String uf,
        BigDecimal latitude,
        BigDecimal longitude,
        String urlFonte,
        Long idArea
) {
}
