package net.prussaq.jesse.controller.rest;

import lombok.RequiredArgsConstructor;
import net.prussaq.jesse.service.DatabaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/database")
public class DatabaseController {

    private final DatabaseService service;

    @GetMapping("/backup")
    public void backup() {
        service.backup();
    }

    @GetMapping("restore")
    public void restore() {

    }
}
