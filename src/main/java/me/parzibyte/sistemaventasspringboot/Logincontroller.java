package me.parzibyte.sistemaventasspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
@Controller
@RequestMapping
public class Logincontroller {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @GetMapping("/login")
    public ModelAndView modelAndView(){return new ModelAndView("/login");
    }

    @PostMapping("/login")
    public String procesarFormularioLogin(@RequestParam String nombreUsuario, @RequestParam String contrasena, Model model) {
        // Obtener el usuario de la base de datos por su nombre de usuario
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario);

        if (usuario != null && usuario.getContrasena().equals(contrasena)) {
            // Autenticación exitosa
            return "redirect:/productos/mostrar"; // Redirigir a la página de inicio
        } else {
            return "/login";
        }
    }

}





