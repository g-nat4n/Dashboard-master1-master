package Dashboard.Controller;

import Dashboard.Entity.Usuario;
import Dashboard.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario getUsuarioLogado() {

        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return usuarioRepository.findByEmail(userDetails.getUsername())
                    .orElse(null);
        }

        return null;
    }

    @GetMapping("/cadastro")
    public String paginaCadastro(Model model) {

        Usuario usuario = getUsuarioLogado();
        model.addAttribute("usuario", usuario);

        return "cadastro";
    }

    @GetMapping("/dashboard")
    public String abrirDashboard(Model model) {

        Usuario usuario = getUsuarioLogado();
        model.addAttribute("usuario", usuario);

        return "dashboard";
    }
}