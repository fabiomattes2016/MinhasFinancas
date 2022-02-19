package tech.hashtechsolucoes.minhasfinancas.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tech.hashtechsolucoes.minhasfinancas.exception.ErroAutenticacaoException;
import tech.hashtechsolucoes.minhasfinancas.exception.RegraNegocioException;
import tech.hashtechsolucoes.minhasfinancas.model.entity.Usuario;
import tech.hashtechsolucoes.minhasfinancas.model.repository.UsuarioRepository;
import tech.hashtechsolucoes.minhasfinancas.service.impl.UsuarioServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {
    @SpyBean
    private UsuarioServiceImpl service;

    @MockBean
    private UsuarioRepository repository;

    @Test
    @DisplayName("Deve salvar um usuário.")
    public void salvarUsuario() {
        // Cenário
        Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
        Usuario usuario = criarUsuario("usuario@email.com", "123456");
        Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        // Execução
        Usuario usuarioSalvo = service.salvarUsuario(new Usuario());

        // Verificação
        assertThat(usuarioSalvo).isNotNull();
        assertThat(usuarioSalvo.getId()).isEqualTo(1L);
        assertThat(usuarioSalvo.getNome()).isEqualTo("usuario");
        assertThat(usuarioSalvo.getEmail()).isEqualTo("usuario@email.com");
        assertThat(usuarioSalvo.getSenha()).isEqualTo("123456");
    }

    @Test
    @DisplayName("Deve autenticar um usuário com sucesso.")
    public void autenticarUsuarioSucesso() {
        // Cenário
        String email = "usuario@email.com";
        String senha = "123456";

        Usuario usuario = criarUsuario(email, senha);
        Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

        // Execução
        Usuario result = service.autenticar(email, senha);

        // Verificação
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Deve lançar erro quando não encontrar usuário cadastrado com e-mail informado.")
    public void lancaErroEmailNaoEncontrado() {
        // Cenário
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        // Execução
        Throwable exception = catchThrowable(() -> service.autenticar("usuario@email.com", "123456"));
        assertThat(exception).isInstanceOf(ErroAutenticacaoException.class).hasMessage("Usuário não encontrado.");
    }

    @Test
    @DisplayName("Deve lançar erro quando a senha estiver invalida.")
    public void lancaErroSenhaInvalida() {
        // Cenário
        String senha = "12346";
        String email = "usuario@email.com";
        Usuario usuario = criarUsuario(email, senha);
        Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

        // Execução
        Throwable exception = catchThrowable(() -> service.autenticar(email, "senha"));
        assertThat(exception).isInstanceOf(ErroAutenticacaoException.class).hasMessage("Senha inválida.");
    }

    @Test
    @DisplayName("Deve validar e-mail.")
    public void validarEmail() {
        // Cenário
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

        // Execução
        service.validarEmail("usuario@email.com");
    }

    @Test
    @DisplayName("Deve lançar erro ao validar e-mail quando existir e-mail cadastrado.")
    public void existeEmail() {
        // Cenário
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

        // Execução
        Throwable exception = catchThrowable(() -> service.validarEmail("usuario@email.com"));
        assertThat(exception).isInstanceOf(RegraNegocioException.class).hasMessage("Já existe um usuário cadastrado com este e-mail.");
    }

    public static Usuario criarUsuario(String email, String senha) {
        return Usuario.builder().nome("usuario").email(email).senha(senha).id(1L).build();
    }
}
