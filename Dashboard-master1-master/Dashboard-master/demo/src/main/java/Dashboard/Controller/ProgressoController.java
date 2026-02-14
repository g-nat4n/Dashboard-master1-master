package Dashboard.Controller;

import Dashboard.Entity.Usuario;
import Dashboard.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProgressoController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/progresso")
    public String exibirPaginaProgresso(Model model) {

        Usuario usuario = usuarioLogado();

        if (usuario == null) return "redirect:/login";

        model.addAttribute("usuario", usuario);

        int[] tarefasConcluidas = {5, 2, 8, 4, 7, 3, 6};
        model.addAttribute("dados", tarefasConcluidas);

        model.addAttribute("total", 35);
        model.addAttribute("media", "5.0");

        return "progresso";
    }

    private Usuario usuarioLogado() {

        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return usuarioRepository
                    .findByEmail(userDetails.getUsername())
                    .orElse(null);
        }

        return null;
    }
}