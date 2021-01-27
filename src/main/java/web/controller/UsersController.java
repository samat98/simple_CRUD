package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String getUsers(Model model) {
        List<User> users = userService.listUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/{id}/edit")
    public String getEditForm(@PathVariable Long id, Model model) {
        User userEdit = userService.findUser(id);
        model.addAttribute("user", userEdit);
        return "update";
    }

    @PostMapping("/adduser")
    public String addUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    @PostMapping("update")
    public String updateUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/users";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute(new User());
        return "create";
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id, Model model) {
        User user = userService.findUser(id);
        if(user != null) {
            userService.deleteUser(id);
            model.addAttribute("messege", "user " + user.getName() + " succesfully deleted");
        } else {
            model.addAttribute("messege", "no such user");
        }

        return "redirect:/users";
    }

}
