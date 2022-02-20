package tech.hashtechsolucoes.minhasfinancas.api.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.hashtechsolucoes.minhasfinancas.api.dto.UsuarioDTO;
import tech.hashtechsolucoes.minhasfinancas.exception.ErroAutenticacaoException;
import tech.hashtechsolucoes.minhasfinancas.exception.RegraNegocioException;
import tech.hashtechsolucoes.minhasfinancas.model.entity.Usuario;
import tech.hashtechsolucoes.minhasfinancas.service.LancamentoService;
import tech.hashtechsolucoes.minhasfinancas.service.UsuarioService;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioResource {
    private final UsuarioService service;
    private final LancamentoService lancamentoService;

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

    @GetMapping("{id}/saldo")
    public ResponseEntity obterSaldo(@PathVariable("id") Long id) {
        Optional<Usuario> usuario = service.obterPorId(id);
        if(!usuario.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        BigDecimal saldo = lancamentoService.obterSaldoPorUsuario(id);
        return ResponseEntity.ok(saldo);
    }
}
