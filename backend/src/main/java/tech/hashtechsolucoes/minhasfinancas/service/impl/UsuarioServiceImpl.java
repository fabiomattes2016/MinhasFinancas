package tech.hashtechsolucoes.minhasfinancas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.hashtechsolucoes.minhasfinancas.exception.ErroAutenticacaoException;
import tech.hashtechsolucoes.minhasfinancas.exception.RegraNegocioException;
import tech.hashtechsolucoes.minhasfinancas.model.entity.Usuario;
import tech.hashtechsolucoes.minhasfinancas.model.repository.UsuarioRepository;
import tech.hashtechsolucoes.minhasfinancas.service.UsuarioService;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository repository;

    public UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuario = repository.findByEmail(email);

        if (!usuario.isPresent()) {
            throw new ErroAutenticacaoException("Usuário não encontrado.");
        }

        if (!usuario.get().getSenha().equals(senha)) {
            throw new ErroAutenticacaoException("Senha inválida.");
        }

        return usuario.get();
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        return repository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
        boolean existe = repository.existsByEmail(email);

        if(existe) {
            throw new RegraNegocioException("Já existe um usuário cadastrado com este e-mail.");
        }
    }
}
