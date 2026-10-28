package br.com.fiap.t2tdsps.expedicaoatomo.dto;

import br.com.fiap.t2tdsps.expedicaoatomo.model.TipoPonto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PontoMapaResponse(
        Long id,
        String titulo,
        String resumo,
        String curiosidade,
        TipoPonto tipo,
        String cidade,
        String uf,
        BigDecimal latitude,
        BigDecimal longitude,
        String urlFonte,
        LocalDateTime dataCadastro,
        Long areaId,
        String areaNome
) {
}
