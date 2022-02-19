package tech.hashtechsolucoes.minhasfinancas.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.hashtechsolucoes.minhasfinancas.api.dto.UsuarioDTO;
import tech.hashtechsolucoes.minhasfinancas.exception.ErroAutenticacaoException;
import tech.hashtechsolucoes.minhasfinancas.exception.RegraNegocioException;
import tech.hashtechsolucoes.minhasfinancas.model.entity.Usuario;
import tech.hashtechsolucoes.minhasfinancas.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {
    private UsuarioService service;

    public UsuarioResource(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody UsuarioDTO dto) {
        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .build();

        try {
            Usuario usuarioSalvo = service.salvarUsuario(usuario);
            return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
        } catch (RegraNegocioException exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity autenticar(@RequestBody UsuarioDTO dto) {
        try {
            Usuario usuario = service.autenticar(dto.getEmail(), dto.getSenha());
            return new ResponseEntity(usuario, HttpStatus.OK);
        } catch (ErroAutenticacaoException exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
