package br.com.fiap.t2tdsps.expedicaoatomo.mapper;

import br.com.fiap.t2tdsps.expedicaoatomo.dto.AreaAplicacaoResponse;
import br.com.fiap.t2tdsps.expedicaoatomo.model.AreaAplicacao;

public final class AreaAplicacaoMapper {

    private AreaAplicacaoMapper() {
    }

    public static AreaAplicacaoResponse toResponse(AreaAplicacao area) {
        return new AreaAplicacaoResponse(area.getId(), area.getNome(), area.getDescricao());
    }
}
