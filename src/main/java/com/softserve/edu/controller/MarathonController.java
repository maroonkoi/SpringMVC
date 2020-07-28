package com.softserve.edu.controller;


import com.softserve.edu.model.Marathon;
import com.softserve.edu.service.MarathonService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
@Data
public class MarathonController {
    private MarathonService marathonService;

    @GetMapping("/marathons")
    public String getAllMarathons(Model model) {
        model.addAttribute("marathons", marathonService.getAll());
        return "marathons";
    }

    @GetMapping("/marathons/edit")
    public String edit(Model model) {
        return "editMarathon";
    }

    @GetMapping("/marathons/create")
    public String create(Model model) {
        model.addAttribute("marathon", new Marathon());
        return "createMarathon";
    }

    @PostMapping("/marathons/create")
    public String addMarathon(@ModelAttribute(name="marathon") Marathon marathon) {
        marathonService.createOrUpdate(marathon);
        return "redirect:/marathons";
    }
}
