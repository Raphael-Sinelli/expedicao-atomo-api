package br.com.fiap.t2tdsps.expedicaoatomo.mapper;

import br.com.fiap.t2tdsps.expedicaoatomo.dto.PontoMapaResponse;
import br.com.fiap.t2tdsps.expedicaoatomo.model.PontoMapa;

public final class PontoMapaMapper {

    private PontoMapaMapper() {
    }

    public static PontoMapaResponse toResponse(PontoMapa ponto) {
        return new PontoMapaResponse(
                ponto.getId(),
                ponto.getTitulo(),
                ponto.getResumo(),
                ponto.getCuriosidade(),
                ponto.getTipo(),
                ponto.getCidade(),
                ponto.getUf(),
                ponto.getLatitude(),
                ponto.getLongitude(),
                ponto.getUrlFonte(),
                ponto.getDataCadastro(),
                ponto.getArea().getId(),
                ponto.getArea().getNome()
        );
    }
}
