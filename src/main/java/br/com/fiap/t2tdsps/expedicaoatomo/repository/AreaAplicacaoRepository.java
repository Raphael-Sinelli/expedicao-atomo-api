package br.com.fiap.t2tdsps.expedicaoatomo.repository;

import br.com.fiap.t2tdsps.expedicaoatomo.dto.AreaResumoResponse;
import br.com.fiap.t2tdsps.expedicaoatomo.model.AreaAplicacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AreaAplicacaoRepository extends JpaRepository<AreaAplicacao, Long> {

    // JPQL com projeção em record. O left join mantém no resultado a área que ainda não tem ponto, com quantidade 0.
    @Query("""
            select new br.com.fiap.t2tdsps.expedicaoatomo.dto.AreaResumoResponse(a.id, a.nome, count(p))
            from AreaAplicacao a left join a.pontos p
            group by a.id, a.nome
            order by a.nome
            """)
    List<AreaResumoResponse> resumirPorArea();
}
