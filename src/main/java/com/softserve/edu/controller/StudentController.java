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

    @GetMapping("/students/{id}")
    public String viewAllUsersByMarathon (@PathVariable String id, Model model){
        Marathon marathon = marathonService.getMarathonById(Long.valueOf(id));
        Set<User> users = (Set<User>) getAllStudents((List<User>) marathon.getUsers());
        model.addAttribute("students", users);
        return "students";
    }
    @GetMapping("/students/{marathonId}/delete/{studentId}")
    public String deleteStudent (@PathVariable Long marathonId, @PathVariable Long studentId, Model model ){
        User user = userService.getUserById(studentId);
        Marathon marathon = marathonService.getMarathonById(marathonId);
        marathon.getUsers().remove(user);
        return "redirect:/students/"+marathonId;
    }



    private List<User> getAllStudents (List<User> users) {
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
