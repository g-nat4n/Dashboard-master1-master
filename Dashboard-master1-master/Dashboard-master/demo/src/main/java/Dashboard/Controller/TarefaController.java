package Dashboard.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import Dashboard.Entity.Tarefa;
import Dashboard.Repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/api/tarefas")
@CrossOrigin("*") // Permite que seu front-end acesse o back-end sem erros de CORS
public class TarefaController {

    @Autowired
    private TarefaRepository repository;


    @PostMapping
    public Tarefa adicionar(@RequestBody Tarefa tarefa) {
        return repository.save(tarefa);
    }


    @GetMapping
    public List<Tarefa> listarTodas() {
        return repository.findAll();
    }


    @PutMapping("/{id}")
    public Tarefa atualizar(@PathVariable Long id, @RequestBody Tarefa tarefaAtualizada) {
        return repository.findById(id)
                .map(tarefa -> {
                    tarefa.setDescricao(tarefaAtualizada.getDescricao());
                    tarefa.setConcluida(tarefaAtualizada.isConcluida());
                    return repository.save(tarefa);
                }).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        repository.deleteById(id);
    }
}