package net.prussaq.jesse.controller.rest;

import lombok.AllArgsConstructor;
import net.prussaq.jesse.model.Profile;
import net.prussaq.jesse.service.ActiveService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("active")
public class ActiveController {

    private final ActiveService service;

    @GetMapping("profile")
    public Profile getProfile() {
        return service.getProfile();
    }

}
