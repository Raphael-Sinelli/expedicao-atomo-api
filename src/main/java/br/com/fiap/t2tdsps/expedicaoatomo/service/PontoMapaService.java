package br.com.fiap.t2tdsps.expedicaoatomo.service;

import br.com.fiap.t2tdsps.expedicaoatomo.dto.PontoMapaRequest;
import br.com.fiap.t2tdsps.expedicaoatomo.model.PontoMapa;
import br.com.fiap.t2tdsps.expedicaoatomo.model.TipoPonto;
import org.springframework.data.domain.Page;

public interface PontoMapaService {

    Page<PontoMapa> listar(Long areaId, String uf, TipoPonto tipo, Integer page, Integer sizePage);

    Page<PontoMapa> buscarPorTitulo(String termo, Integer page, Integer sizePage);

    Page<PontoMapa> listarPorArea(Long areaId, Integer page, Integer sizePage);

    PontoMapa buscarPorId(Long id);

    PontoMapa criar(PontoMapaRequest request);

    PontoMapa atualizar(Long id, PontoMapaRequest request);

    void excluir(Long id);
}
