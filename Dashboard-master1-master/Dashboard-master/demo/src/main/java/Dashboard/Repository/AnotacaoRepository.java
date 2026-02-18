package Dashboard.Repository;

import Dashboard.Entity.Anotacao;
import Dashboard.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnotacaoRepository extends JpaRepository<Anotacao, Long> {
    Optional<Anotacao> findByUsuario(Usuario usuario);
}