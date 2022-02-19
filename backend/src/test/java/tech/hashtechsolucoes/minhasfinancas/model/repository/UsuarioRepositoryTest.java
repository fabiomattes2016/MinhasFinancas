package tech.hashtechsolucoes.minhasfinancas.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tech.hashtechsolucoes.minhasfinancas.model.entity.Usuario;

import java.util.Optional;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    @DisplayName("Deve retornar verdadeiro quando houver usuário cadastrado com e-mail.")
    public void existeEamil() {
        // Cenário
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);

        // Execução
        boolean result = repository.existsByEmail("usuario@email.com");

        // Verificação
        Assertions.assertThat(result).isTrue();

    }

    @Test
    @DisplayName("Deve retornar falso quando não houver usuário cadastrado com e-mail.")
    public void naoExisteEmail() {
        // Execução
        boolean result = repository.existsByEmail("usuario@email.com");

        // Verifocação
        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Deve persistir um usuário na base de dados.")
    public void salvarUsuario() {
        // Cenário
        Usuario usuario = criarUsuario();

        // Execução
        Usuario usuarioSalvo = repository.save(usuario);

        // Verificação
        Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
    }

    @Test
    @DisplayName("Deve buscar um usuário por e-mail.")
    public void buscarUsuarioPorEmail() {
        // Cenário
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);

        // Verificação
        Optional<Usuario> result = repository.findByEmail("usuario@email.com");
        Assertions.assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar usuario por e-mail quando não existe na base.")
    public void retornarVazioAoBuscarUsuarioPorEmail() {
        // Verificação
        Optional<Usuario> result = repository.findByEmail("usuario@email.com");
        Assertions.assertThat(result.isPresent()).isFalse();
    }

    public static Usuario criarUsuario() {
        return Usuario.builder().nome("usuario").email("usuario@email.com").senha("123456").build();
    }
}
