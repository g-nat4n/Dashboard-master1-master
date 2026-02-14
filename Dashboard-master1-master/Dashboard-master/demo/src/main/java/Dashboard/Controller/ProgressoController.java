package Dashboard.Controller;

import Dashboard.Entity.Usuario;
import Dashboard.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;
import java.util.List;

@Controller
public class ProgressoController {

    @Autowired
    private UsuarioRepository usuarioRepository; // Injeta o repositório para buscar no banco



    // funcao para chamar dados do banco( usari oe foto)-------------------------------
    @GetMapping("/progresso")
    public String exibirPaginaProgresso(Model model) {
        // BUSCA PELO ID 1, exatamente como no Dashboard
        Usuario usuario = usuarioRepository.findById(3L).orElse(new Usuario());
        model.addAttribute("usuario", usuario);

        // Dados do gráfico e cards
        int[] tarefasConcluidas = {5, 2, 8, 4, 7, 3, 6};
        model.addAttribute("dados", tarefasConcluidas);

        int total = 35;
        model.addAttribute("total", total);
        model.addAttribute("media", "5.0");

        return "progresso";
    }

    // aqui termina a funçao para chamar os dados do usaurio(foto e nome)-----------

}