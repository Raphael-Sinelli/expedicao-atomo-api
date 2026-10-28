package br.com.fiap.t2tdsps.expedicaoatomo.service;

import br.com.fiap.t2tdsps.expedicaoatomo.dto.AreaResumoResponse;
import br.com.fiap.t2tdsps.expedicaoatomo.model.AreaAplicacao;

import java.util.List;

public interface AreaAplicacaoService {

    List<AreaAplicacao> listar();

    AreaAplicacao buscarPorId(Long id);

    List<AreaResumoResponse> resumir();
}
