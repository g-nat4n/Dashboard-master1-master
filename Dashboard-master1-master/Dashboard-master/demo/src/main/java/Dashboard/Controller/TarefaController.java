package Dashboard.Controller;


import Dashboard.Entity.Usuario;
import Dashboard.Repository.UsuarioRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import Dashboard.Entity.Tarefa;
import Dashboard.Repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;



@RestController
@RequestMapping("/api/tarefas")
@CrossOrigin("*") // Permite que seu front-end acesse o back-end sem erros de CORS
public class TarefaController {

    @Autowired
    private TarefaRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @PostMapping
    public Tarefa adicionar(@RequestBody Tarefa tarefa, Principal principal) {

        if (tarefa.getDescricao() == null || tarefa.getDescricao().trim().isEmpty()) {
            throw new RuntimeException("Descrição vazia");
        }

        Usuario usuario = usuarioRepository
                .findByEmail(principal.getName())
                .orElseThrow();

        tarefa.setUsuario(usuario);
        tarefa.setConcluida(false);

        return repository.save(tarefa);
    }


    @GetMapping
    public List<Tarefa> listarTodas(Principal principal) {

        Usuario usuario = usuarioRepository
                .findByEmail(principal.getName())
                .orElseThrow();

        return repository.findByUsuario(usuario);
    }


    @PutMapping("/{id}")
    public Tarefa atualizar(@PathVariable Long id, @RequestBody Tarefa tarefaAtualizada) {

        return repository.findById(id).map(tarefa -> {

            // Só altera descrição se vier preenchida
            if (tarefaAtualizada.getDescricao() != null &&
                    !tarefaAtualizada.getDescricao().trim().isEmpty()) {

                tarefa.setDescricao(tarefaAtualizada.getDescricao());
            }

            tarefa.setConcluida(tarefaAtualizada.isConcluida());

            return repository.save(tarefa);

        }).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
    }
}