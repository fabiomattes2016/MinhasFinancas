package tech.hashtechsolucoes.minhasfinancas.api.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.hashtechsolucoes.minhasfinancas.api.dto.AtualizaStatusDTO;
import tech.hashtechsolucoes.minhasfinancas.api.dto.LancamentoDTO;
import tech.hashtechsolucoes.minhasfinancas.exception.RegraNegocioException;
import tech.hashtechsolucoes.minhasfinancas.model.entity.Lancamento;
import tech.hashtechsolucoes.minhasfinancas.model.entity.Usuario;
import tech.hashtechsolucoes.minhasfinancas.model.enums.StatusLancamento;
import tech.hashtechsolucoes.minhasfinancas.model.enums.TipoLancamento;
import tech.hashtechsolucoes.minhasfinancas.service.LancamentoService;
import tech.hashtechsolucoes.minhasfinancas.service.UsuarioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lancamentos")
@RequiredArgsConstructor
public class LancamentoResource {
    private final LancamentoService service;
    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity salvar(@RequestBody LancamentoDTO dto) {
        try {
            Lancamento entidade = converter(dto);
            entidade = service.salvar(entidade);
            return new ResponseEntity(entidade, HttpStatus.CREATED);
        } catch (RegraNegocioException exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LancamentoDTO dto) {
        return service.obterPorId(id).map(entity -> {
            try {
                Lancamento lancamento = converter(dto);
                lancamento.setId(entity.getId());
                service.atualizar(lancamento);

                return new ResponseEntity(lancamento, HttpStatus.OK);
            } catch (RegraNegocioException exception) {
                return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }).orElseGet(() -> new ResponseEntity("Lançamento não encontrado.", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id) {
        return service.obterPorId(id).map(entity -> {
            try {
                service.deletar(entity);
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            } catch (RegraNegocioException exception) {
                return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }).orElseGet(() -> new ResponseEntity("Lançamento não encontrado.", HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity buscar(
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "mes", required = false) Integer mes,
            @RequestParam(value = "ano", required = false) Integer ano,
            @RequestParam(value = "tipo", required = false) TipoLancamento tipo,
            @RequestParam("usuario") Long idUsuario
            ) {
        Lancamento lancamentoFiltro = new Lancamento();
        lancamentoFiltro.setDescricao(descricao);
        lancamentoFiltro.setMes(mes);
        lancamentoFiltro.setAno(ano);
        lancamentoFiltro.setTipo(tipo);

        Optional<Usuario> usuario = usuarioService.obterPorId(idUsuario);
        if(!usuario.isPresent()) {
            return new ResponseEntity("Não foi possível realizar a consulta. Usuário não encontrado!", HttpStatus.NOT_FOUND);
        } else {
            lancamentoFiltro.setUsuario(usuario.get());
        }

        List<Lancamento> lancamentos = service.buscar(lancamentoFiltro);

        return new ResponseEntity(lancamentos, HttpStatus.OK);
    }

    @PutMapping("{id}/atualiza-status")
    public ResponseEntity atualizarStatus( @PathVariable("id") Long id , @RequestBody AtualizaStatusDTO dto ) {
        return service.obterPorId(id).map( entity -> {
            StatusLancamento statusSelecionado = StatusLancamento.valueOf(dto.getStatus());

            if(statusSelecionado == null) {
                return ResponseEntity.badRequest().body("Não foi possível atualizar o status do lançamento, envie um status válido.");
            }

            try {
                entity.setStatus(statusSelecionado);
                service.atualizar(entity);
                return ResponseEntity.ok(entity);
            }catch (RegraNegocioException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }

        }).orElseGet( () ->
                new ResponseEntity("Lancamento não encontrado na base de Dados.", HttpStatus.BAD_REQUEST) );
    }

    private Lancamento converter(LancamentoDTO dto) {
        Lancamento lancamento = new Lancamento();
        lancamento.setId(dto.getId());
        lancamento.setDescricao(dto.getDescricao());
        lancamento.setAno(dto.getAno());
        lancamento.setMes(dto.getMes());
        lancamento.setValor(dto.getValor());

        Usuario usuario = usuarioService.obterPorId(dto.getUsuario()).orElseThrow(() -> new RegraNegocioException("Usuário não encontrado."));
        lancamento.setUsuario(usuario);

        if(dto.getTipo() != null) {
            lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
        }

        if(dto.getStatus() != null) {
            lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
        }

        return lancamento;
    }
}
