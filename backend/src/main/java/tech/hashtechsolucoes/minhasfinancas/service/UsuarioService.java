package tech.hashtechsolucoes.minhasfinancas.service;

import tech.hashtechsolucoes.minhasfinancas.model.entity.Usuario;

public interface UsuarioService {

    Usuario autenticar(String email, String senha);
    Usuario salvarUsuario(Usuario usuario);
    void validarEmail(String email);
}
