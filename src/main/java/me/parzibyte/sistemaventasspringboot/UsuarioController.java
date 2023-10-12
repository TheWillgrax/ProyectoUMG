package me.parzibyte.sistemaventasspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping(value = "/agregar")
    public String agregarUsuarioForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/agregar_usuario";
    }

    @PostMapping(value = "/agregar")
    public String agregarUsuario(@ModelAttribute @Valid Usuario usuario, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return "usuarios/agregar_usuario";
        }

        usuarioRepository.save(usuario);
        redirectAttrs
                .addFlashAttribute("mensaje", "Usuario agregado correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:/usuarios/agregar";
    }

    @GetMapping(value = "/mostrar")
    public String mostrarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "usuarios/mostrar_usuarios";
    }

    @GetMapping(value = "/editar/{id}")
    public String editarUsuarioForm(@PathVariable Long id, Model model) {
        Usuario usuario = (Usuario) usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return "redirect:/usuarios/mostrar";
        }
        model.addAttribute("usuario", usuario);
        return "usuarios/editar_usuario";
    }

    @PostMapping(value = "/editar/{id}")
    public String editarUsuario(@PathVariable Long id, @ModelAttribute @Valid Usuario usuario, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return "usuarios/editar_usuario";
        }

        usuario.setId(id);
        usuarioRepository.save(usuario);
        redirectAttrs
                .addFlashAttribute("mensaje", "Usuario editado correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:/usuarios/mostrar";
    }

    @GetMapping(value = "/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        usuarioRepository.deleteById(id);
        redirectAttrs
                .addFlashAttribute("mensaje", "Usuario eliminado correctamente")
                .addFlashAttribute("clase", "warning");
        return "redirect:/usuarios/mostrar";
    }
}
