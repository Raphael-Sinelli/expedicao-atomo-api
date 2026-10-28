package br.com.fiap.t2tdsps.expedicaoatomo.service;

import br.com.fiap.t2tdsps.expedicaoatomo.dto.PontoMapaRequest;
import br.com.fiap.t2tdsps.expedicaoatomo.exceptions.ResourceNotFoundException;
import br.com.fiap.t2tdsps.expedicaoatomo.exceptions.TituloDuplicadoException;
import br.com.fiap.t2tdsps.expedicaoatomo.model.AreaAplicacao;
import br.com.fiap.t2tdsps.expedicaoatomo.model.PontoMapa;
import br.com.fiap.t2tdsps.expedicaoatomo.model.TipoPonto;
import br.com.fiap.t2tdsps.expedicaoatomo.repository.AreaAplicacaoRepository;
import br.com.fiap.t2tdsps.expedicaoatomo.repository.PontoMapaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PontoMapaServiceImpl implements PontoMapaService {

    private final PontoMapaRepository pontoMapaRepository;
    private final AreaAplicacaoRepository areaAplicacaoRepository;

    public PontoMapaServiceImpl(PontoMapaRepository pontoMapaRepository,
                                AreaAplicacaoRepository areaAplicacaoRepository) {
        this.pontoMapaRepository = pontoMapaRepository;
        this.areaAplicacaoRepository = areaAplicacaoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PontoMapa> listar(Long areaId, String uf, TipoPonto tipo, Integer page, Integer sizePage) {
        return pontoMapaRepository.filtrar(areaId, uf, tipo, paginaPorTitulo(page, sizePage));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PontoMapa> buscarPorTitulo(String termo, Integer page, Integer sizePage) {
        return pontoMapaRepository.findByTituloContainingIgnoreCase(termo.trim(), paginaPorTitulo(page, sizePage));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PontoMapa> listarPorArea(Long areaId, Integer page, Integer sizePage) {
        if (!areaAplicacaoRepository.existsById(areaId)) {
            throw new ResourceNotFoundException("Área de aplicação", areaId);
        }
        return pontoMapaRepository.findByAreaId(areaId, paginaPorTitulo(page, sizePage));
    }

    @Override
    @Transactional(readOnly = true)
    public PontoMapa buscarPorId(Long id) {
        return pontoMapaRepository.findWithAreaById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ponto do mapa", id));
    }

    @Override
    @Transactional
    public PontoMapa criar(PontoMapaRequest request) {
        String titulo = request.titulo().trim();
        if (pontoMapaRepository.existsByTituloIgnoreCase(titulo)) {
            throw new TituloDuplicadoException(titulo);
        }
        // O id nunca vem do cliente e a data de cadastro é preenchida pelo @PrePersist da entidade.
        PontoMapa ponto = new PontoMapa();
        preencher(ponto, request);
        return pontoMapaRepository.save(ponto);
    }

    @Override
    @Transactional
    public PontoMapa atualizar(Long id, PontoMapaRequest request) {
        // Busca o ponto existente e troca só os campos editáveis: id e dataCadastro continuam os mesmos.
        PontoMapa ponto = buscarPorId(id);
        String titulo = request.titulo().trim();
        if (pontoMapaRepository.existsByTituloIgnoreCaseAndIdNot(titulo, id)) {
            throw new TituloDuplicadoException(titulo);
        }
        preencher(ponto, request);
        return pontoMapaRepository.save(ponto);
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        PontoMapa ponto = buscarPorId(id);
        pontoMapaRepository.delete(ponto);
    }

    private void preencher(PontoMapa ponto, PontoMapaRequest request) {
        ponto.setArea(buscarArea(request.idArea()));
        ponto.setTitulo(request.titulo().trim());
        ponto.setResumo(request.resumo().trim());
        ponto.setCuriosidade(textoOpcional(request.curiosidade()));
        ponto.setTipo(request.tipo());
        ponto.setCidade(request.cidade().trim());
        ponto.setUf(request.uf().trim());
        ponto.setLatitude(request.latitude());
        ponto.setLongitude(request.longitude());
        ponto.setUrlFonte(request.urlFonte().trim());
    }

    private AreaAplicacao buscarArea(Long idArea) {
        return areaAplicacaoRepository.findById(idArea)
                .orElseThrow(() -> new ResourceNotFoundException("Área de aplicação", idArea));
    }

    // Curiosidade é opcional: texto só com espaços é gravado como nulo.
    private String textoOpcional(String texto) {
        return texto == null || texto.isBlank() ? null : texto.trim();
    }

    private Pageable paginaPorTitulo(Integer page, Integer sizePage) {
        return PageRequest.of(page, sizePage, Sort.by("titulo"));
    }
}
