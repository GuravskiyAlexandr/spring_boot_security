package com.example.demo.controller;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.google.common.collect.Sets;


@Controller
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/admin")
    public String allUsers(Model model) {
        model.addAttribute("myUsersList", userService.getAllUsers());
        model.addAttribute("user", new User(Sets.newHashSet(new Role("ROLE_USER"), new Role("ROLE_ADMIN"))));
        return "admin";
    }

    @PostMapping("/admin/userAdd")
    public String addNewUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/edit")
    public String editUser(@ModelAttribute("user") User newUser, Model model) {
        User oldUser = userService.findUserById(newUser.getId());
        if (!userService.checkNewPassword(newUser, oldUser)) {
            newUser.setRoles(oldUser.getRoles());
            model.addAttribute("user", newUser);
            model.addAttribute("passwordOld", "Старый пароль не совпадают");
            return "/edit";
        }
        userService.userEdit(newUser);
        return "redirect:/admin";
    }

    @GetMapping(value = "/edit")
    public String setForm(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("userRoles", Sets.newHashSet(new Role(1L,"ROLE_USER"), new Role(2L,"ROLE_ADMIN")));
        return "edit";
    }

    @GetMapping(value = "/admin/delete")
    public String delete(@RequestParam("id") Long id) {
        userService.userDelete(id);
        return "redirect:/admin";
    }
}
