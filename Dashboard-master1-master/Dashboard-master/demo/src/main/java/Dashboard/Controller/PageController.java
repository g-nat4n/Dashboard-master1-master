package Dashboard.Controller;

import Dashboard.Entity.Usuario;
import Dashboard.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/cadastro")
    public String paginaCadastro(Model model, Authentication authentication) {

        String email = authentication.getName(); // email do usuário logado

        Usuario usuario = usuarioRepository
                .findByEmail(email)
                .orElse(new Usuario());

        model.addAttribute("usuario", usuario);

        return "cadastro";
    }

    @GetMapping("/dashboard")
    public String abrirDashboard(Model model, Authentication authentication) {

        String email = authentication.getName();

        Usuario usuario = usuarioRepository
                .findByEmail(email)
                .orElse(new Usuario());

        model.addAttribute("usuario", usuario);

        return "dashboard";
    }
}