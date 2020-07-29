package com.softserve.edu.controller;

import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.User;
import com.softserve.edu.service.MarathonService;
import com.softserve.edu.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Controller
@AllArgsConstructor
@Data
public class StudentController {

    private UserService userService;
    private MarathonService marathonService;

    @GetMapping("/students")
    public String viewAllUsers(Model model) {
        model.addAttribute("students", getAllStudents(userService.getAll()));
        return "students";
    }


    @GetMapping("/students_marathon/{id}")
    public String viewAllUsersByMarathon (@PathVariable Long id, Model model){
        Marathon marathon = marathonService.getMarathonById(id);
        Set<User> users = marathon.getUsers();
        for (User user : users) {
            if (user.getRole().equals(User.Role.MENTOR)) {
                users.remove(user);
            }
        }
        model.addAttribute("students", users).addAttribute("marathon", marathon);
        return "students_marathon";

    }
    @GetMapping("/students/{marathonId}/delete/{studentId}")
    public String deleteStudent (@PathVariable Long marathonId, @PathVariable Long studentId){
        User user = userService.getUserById(studentId);
        Marathon marathon = marathonService.getMarathonById(marathonId);
        marathon.getUsers().remove(user);
        return "redirect:/students_marathon/";
    }

    @GetMapping("/student/{studentId}")
    public String showStudent (@PathVariable Long studentId, Model model){
        User user = userService.getUserById(studentId);
        model.addAttribute("student", user);
        return "student";
    }
//    @GetMapping("/marathons/delete/{id}")
//    public String delete(@PathVariable(name = "id") Long id, Model model) {
//        marathonService.deleteMarathonById(id);
//        return "redirect:/marathons";
//    }


    public List<User> getAllStudents (List<User> users) {
        List<User> students = new ArrayList<>();
        for (User user : users) {
            if (user.getRole().equals(User.Role.TRAINEE)) {
                students.add(user);
            }
        }
        return students;

    }

    //TODO implement needed methods





}
