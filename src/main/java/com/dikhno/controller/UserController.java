package com.dikhno.controller;

import com.dikhno.model.User;
import com.dikhno.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dexter on 04.04.2015.
 */
@Controller
public class UserController {

    private static int currentPage = 0;
    private static int lastPage = 0;
    private static String searchName = "";

    @Autowired
    private UserService userService;

    @RequestMapping(value="/")
    public ModelAndView userPage() {
        ModelAndView modelAndView = new ModelAndView("list-of-users");
        List<User> users = userService.getUsers(currentPage, searchName);
        lastPage = userService.getNumber(searchName);
        modelAndView.addObject("users", users);
        modelAndView.addObject("first", "First");
        modelAndView.addObject("last", "Last");
        modelAndView.addObject("previous", currentPage!=0 ? "Previous page" : "");
        modelAndView.addObject("current", currentPage);
        int startPage = currentPage - 5 > 0 ? currentPage - 5 : 1;
        int endPage = startPage + 9;
        if(endPage > lastPage) {endPage = lastPage;}
        modelAndView.addObject("startPage", startPage);
        modelAndView.addObject("endPage", endPage);
        modelAndView.addObject("number", lastPage);
        modelAndView.addObject("next", currentPage<lastPage-1 ? "Next page" : "");
        modelAndView.addObject("reset", ( searchName.length()!=0) ? " [Reset]" : "");
        return modelAndView;
    }

    @RequestMapping(value = "/user/add", method=RequestMethod.GET)
    public ModelAndView addUserPage(){
        ModelAndView modelAndView = new ModelAndView("add-user-form");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @RequestMapping(value="/user/add", method=RequestMethod.POST)
    public ModelAndView addingUser(@RequestParam(value = "name") String name, @RequestParam(value = "age") String age, @RequestParam(value = "isAdmin") String admin ) {
        String message = validation(name, age);
        ModelAndView modelAndView;
        if (message.length()!=0) {
            modelAndView = new ModelAndView("add-user-form");
        }
        else{
            remove();
            User user = new User();
            user.setName(name);
            user.setAge(Integer.parseInt(age));
            user.setIsAdmin(Boolean.parseBoolean(admin));
            userService.addUser(user);
            modelAndView = userPage();
            message = "User was successfully added.";
        }

        modelAndView.addObject("message", message);
        return modelAndView;
    }

    private String validation(String name, String ageValid){
        String message = "";
        int age= 14;
        Pattern pattern = Pattern.compile("\\p{Punct}");
        Matcher matcher = pattern.matcher(name);
        if (name.trim()==""){
            message = "Name should not be empty!<br/>";
        } else if (matcher.find()) {
            message = "Name should not have punctuation!<br/>";
        }
        else if (name.length() > 25){
            message = "The name is too long!";
        }
        try {
            age = Integer.parseInt(ageValid);
            if (age<14) {
                if (age<1) message += "Age must be positive!";
                else if ((age > 0) || (age < 14)) message += "You are too young!";
            }
            if (age>100) {
                message += age + "You are too old!";
            }
        } catch (NumberFormatException e){
            message += "Enter correct value of age";
        }
        return message;
    }


    @RequestMapping(value="/user/list")
    public ModelAndView listOfUsers(@RequestParam(value = "page") int page) {
        currentPage = page;
        return userPage();
    }

    @RequestMapping(value = "/user/last")
    public ModelAndView lastPage(){
        currentPage = lastPage - 1;
            return userPage();
    }

    @RequestMapping(value = "/user/first")
    public ModelAndView firstPage(){
        if (currentPage != 0)
            currentPage = 0;
        return userPage();
    }

    @RequestMapping(value="/user/next")
    public ModelAndView nextPage() {
        if (currentPage < userService.getNumber(searchName)-1)
            currentPage++;
        return userPage();
    }

    @RequestMapping(value="/user/previous")
    public ModelAndView previousPage() {
        if (currentPage!=0) currentPage--;
        return userPage();
    }

    @RequestMapping(value="/user/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editUserPage(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView("edit-user-form");
        User user = userService.getUser(id);
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    @RequestMapping(value="/user/edit/{id}", method=RequestMethod.POST)
    public ModelAndView editingUser(@RequestParam(value = "name") String name, @RequestParam(value = "age") String age, @RequestParam(value = "isAdmin") String admin, @PathVariable Integer id) {
        String message = validation(name, age);
        ModelAndView modelAndView;
        User user = userService.getUser(id);

        if (message.length()!=0) {
            modelAndView = new ModelAndView("edit-user-form");
            modelAndView.addObject("user",user);
        }
        else{
            user.setName(name);
            user.setAge(Integer.parseInt(age));
            user.setIsAdmin(Boolean.parseBoolean(admin));
            userService.updateUser(user);
            modelAndView = userPage();
            message = "User was successfully edited.";
        }
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @RequestMapping(value="/user/delete/{id}", method=RequestMethod.GET)
    public ModelAndView deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        String message = "User was successfully deleted.";
        ModelAndView modelAndView = userPage();
        if (currentPage==lastPage) modelAndView = previousPage();
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @RequestMapping(value="/user/search")
    public ModelAndView search(@RequestParam(value = "name") String name) {
        if (name != "") searchName = name;
        currentPage = 0;
        return userPage();
    }
    @RequestMapping(value="/user/remove")
    public ModelAndView remove() {
        currentPage = 0;
        searchName = "";
        return userPage();
    }
}