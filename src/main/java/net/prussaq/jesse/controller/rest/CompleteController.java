package net.prussaq.jesse.controller.rest;

import lombok.RequiredArgsConstructor;
import net.prussaq.jesse.model.Profile;
import net.prussaq.jesse.service.CompleteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("complete")
public class CompleteController {

    private final CompleteService service;

    @GetMapping("profile")
    public Profile getProfile() {
        return service.getProfile();
    }

}
