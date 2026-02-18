package Dashboard.Controller;

import Dashboard.Entity.Anotacao;
import Dashboard.Entity.Usuario;
import Dashboard.Repository.AnotacaoRepository;
import Dashboard.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/anotacoes")
@CrossOrigin("*")
public class AnotacaoController {

    @Autowired
    private AnotacaoRepository anotacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @GetMapping("")
    @Transactional(readOnly = true)
    public Anotacao listar(Principal principal) {

        Usuario usuario;

        if (principal == null) {
            // Guia anônima → usuário genérico "anonimo@dashboard.com"
            usuario = usuarioRepository.findByEmail("anonimo@dashboard.com")
                    .orElseThrow(() -> new RuntimeException("Usuário anônimo não encontrado"));
        } else {
            // Usuário logado
            usuario = usuarioRepository.findByEmail(principal.getName())
                    .orElseThrow();
        }

        return anotacaoRepository.findByUsuario(usuario)
                .orElseGet(() -> {
                    Anotacao nova = new Anotacao();
                    nova.setUsuario(usuario);
                    nova.setTexto("");
                    return nova;
                });
    }

    // POST → salvar anotação
    @PostMapping("")
    @Transactional
    public Anotacao salvar(@RequestBody Anotacao anotacao, Principal principal) {

        Usuario usuario;

        if (principal == null) {
            // Guia anônima → usuário genérico
            usuario = usuarioRepository.findByEmail("anonimo@dashboard.com")
                    .orElseThrow(() -> new RuntimeException("Usuário anônimo não encontrado"));
        } else {
            // Usuário logado
            usuario = usuarioRepository.findByEmail(principal.getName())
                    .orElseThrow();
        }

        Anotacao existente = anotacaoRepository.findByUsuario(usuario)
                .orElseGet(() -> {
                    Anotacao nova = new Anotacao();
                    nova.setUsuario(usuario);
                    return nova;
                });

        existente.setTexto(anotacao.getTexto());
        return anotacaoRepository.save(existente);
    }
}