package br.com.fiap.t2tdsps.expedicaoatomo.repository;

import br.com.fiap.t2tdsps.expedicaoatomo.model.PontoMapa;
import br.com.fiap.t2tdsps.expedicaoatomo.model.TipoPonto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

// Toda leitura que devolve PontoMapa usa @EntityGraph(attributePaths = "area"): a área vem no mesmo select,
// e o mapper consegue ler area.getNome() mesmo com open-in-view desligado.
public interface PontoMapaRepository extends JpaRepository<PontoMapa, Long> {

    @EntityGraph(attributePaths = "area")
    Optional<PontoMapa> findWithAreaById(Long id);

    // Busca por texto com Query Method: o Spring Data gera o "like %termo%" sem diferenciar maiúsculas.
    @EntityGraph(attributePaths = "area")
    Page<PontoMapa> findByTituloContainingIgnoreCase(String termo, Pageable pageable);

    @EntityGraph(attributePaths = "area")
    Page<PontoMapa> findByAreaId(Long areaId, Pageable pageable);

    // Filtros opcionais: parâmetro nulo desliga a condição correspondente.
    @EntityGraph(attributePaths = "area")
    @Query(value = """
            select p from PontoMapa p
            where (:areaId is null or p.area.id = :areaId)
              and (:uf is null or p.uf = :uf)
              and (:tipo is null or p.tipo = :tipo)
            """,
            countQuery = """
            select count(p) from PontoMapa p
            where (:areaId is null or p.area.id = :areaId)
              and (:uf is null or p.uf = :uf)
              and (:tipo is null or p.tipo = :tipo)
            """)
    Page<PontoMapa> filtrar(@Param("areaId") Long areaId,
                            @Param("uf") String uf,
                            @Param("tipo") TipoPonto tipo,
                            Pageable pageable);

    boolean existsByTituloIgnoreCase(String titulo);

    boolean existsByTituloIgnoreCaseAndIdNot(String titulo, Long id);
}
