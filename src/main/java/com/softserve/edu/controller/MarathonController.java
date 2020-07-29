package com.softserve.edu.controller;


import com.softserve.edu.model.Marathon;
import com.softserve.edu.service.MarathonService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/marathons/edit/{id}")
    public String edit(@PathVariable(name = "id") Long id, Model model) {
        Marathon marathon = marathonService.getMarathonById(id);
        model.addAttribute("marathon", marathon);
        return "editMarathon";
    }

    @PostMapping("/marathons/edit")
    public String edit(@ModelAttribute(name = "marathon") Marathon marathon) {
        marathonService.createOrUpdate(marathon);
        return "redirect:/marathons";
    }


    @GetMapping("/marathons/create")
    public String create(Model model) {
        model.addAttribute("marathon", new Marathon());
        return "createMarathon";
    }

    @PostMapping("/marathons/create")
    public String create(Marathon marathon) {
        marathonService.createOrUpdate(marathon);
        return "redirect:/marathons";
    }


    @GetMapping("/marathons/delete/{id}")
    public String delete(@PathVariable(name = "id") Long id, Model model) {
        marathonService.deleteMarathonById(id);
        return "redirect:/marathons";
    }
}
