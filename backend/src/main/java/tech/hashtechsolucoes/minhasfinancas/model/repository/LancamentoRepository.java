package tech.hashtechsolucoes.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.hashtechsolucoes.minhasfinancas.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
}
