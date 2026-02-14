package Dashboard.Controller;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;



import Dashboard.Entity.Usuario;

import Dashboard.Repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;



import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;




import java.io.File;

import java.io.IOException;

import java.nio.file.Files;

import java.nio.file.Path;

import java.nio.file.Paths;
import java.security.Principal;


@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;


    private Usuario usuarioLogado() {

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


    // ================= FOTO =================

    @PostMapping("/upload-foto")
    public ResponseEntity<String> uploadFoto(
            @RequestParam("foto") MultipartFile arquivo,
            Principal principal) {

        try {
            if (arquivo.isEmpty()) {
                return ResponseEntity.badRequest().body("Arquivo vazio");
            }

            Usuario usuario = usuarioRepository
                    .findByEmail(principal.getName())
                    .orElseThrow();

            String pastaDestino = System.getProperty("user.dir")
                    + "/src/main/resources/static/uploads/";

            File dir = new File(pastaDestino);
            if (!dir.exists()) dir.mkdirs();

            String nomeArquivo = System.currentTimeMillis() + "_" + arquivo.getOriginalFilename();
            Path caminho = Paths.get(pastaDestino + nomeArquivo);

            Files.write(caminho, arquivo.getBytes());

            usuario.setFotoUrl("/uploads/" + nomeArquivo);

            usuarioRepository.save(usuario); // UPDATE real

            return ResponseEntity.ok("Foto salva com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro interno ao processar imagem");
        }
    }

    // ================= DADOS =================

    @PutMapping("/atualizar-dados")
    public ResponseEntity<String> atualizarDados(
            @RequestBody Usuario dadosNovos,
            Principal principal) {

        try {
            Usuario usuario = usuarioRepository
                    .findByEmail(principal.getName())
                    .orElseThrow();

            usuario.setNome(dadosNovos.getNome());
            usuario.setEmail(dadosNovos.getEmail());

            if (dadosNovos.getSenha() != null && !dadosNovos.getSenha().isBlank()) {
                usuario.setSenha(dadosNovos.getSenha());
            }

            usuarioRepository.save(usuario); // UPDATE

            return ResponseEntity.ok("Dados atualizados!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao salvar dados");
        }
    }
}