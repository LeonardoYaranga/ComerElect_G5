package ec.edu.monster.controllers;

import ec.edu.monster.models.ErrorViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/privacy")
    public String privacy() {
        return "privacy";
    }

    @GetMapping("/error")
    public String error(Model model) {
        ErrorViewModel errorViewModel = new ErrorViewModel();
        errorViewModel.setRequestId(java.util.UUID.randomUUID().toString()); // Simulate request ID
        model.addAttribute("errorViewModel", errorViewModel);
        return "error";
    }
}