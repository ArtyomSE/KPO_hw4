package mirea.artemtask.Controllers;

import mirea.artemtask.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private Authentication authentication;
    @Autowired
    UserRepository userRepository;
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String home(Model m) {
        //m.addAttribute("user", userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        return "index";
    }

    @GetMapping("/#command")
    public String command() {
        return "index#command";
    }

    @GetMapping("/#services")
    public String services() {
        return "index#services";
    }

    @GetMapping("/#contact")
    public String contact() {
        return "index#contact";
    }
}
