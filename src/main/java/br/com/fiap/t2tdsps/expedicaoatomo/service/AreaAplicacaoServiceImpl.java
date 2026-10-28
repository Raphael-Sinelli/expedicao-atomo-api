package br.com.fiap.t2tdsps.expedicaoatomo.service;

import br.com.fiap.t2tdsps.expedicaoatomo.dto.AreaResumoResponse;
import br.com.fiap.t2tdsps.expedicaoatomo.exceptions.ResourceNotFoundException;
import br.com.fiap.t2tdsps.expedicaoatomo.model.AreaAplicacao;
import br.com.fiap.t2tdsps.expedicaoatomo.repository.AreaAplicacaoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AreaAplicacaoServiceImpl implements AreaAplicacaoService {

    private final AreaAplicacaoRepository areaAplicacaoRepository;

    public AreaAplicacaoServiceImpl(AreaAplicacaoRepository areaAplicacaoRepository) {
        this.areaAplicacaoRepository = areaAplicacaoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AreaAplicacao> listar() {
        return areaAplicacaoRepository.findAll(Sort.by("nome"));
    }

    @Override
    @Transactional(readOnly = true)
    public AreaAplicacao buscarPorId(Long id) {
        return areaAplicacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Área de aplicação", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AreaResumoResponse> resumir() {
        return areaAplicacaoRepository.resumirPorArea();
    }
}
