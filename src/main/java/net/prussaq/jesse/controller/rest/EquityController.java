package net.prussaq.jesse.controller.rest;

import lombok.AllArgsConstructor;
import net.prussaq.jesse.model.Equity;
import net.prussaq.jesse.service.EquityService;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("equity")
public class EquityController {

    private final EquityService service;

    @GetMapping("/{ticket}")
    public Equity get(@PathVariable String ticket) {
        return service.get(ticket);
    }
    
    @PostMapping
    public void put(@RequestBody Equity equityDto) {
        service.put(equityDto);
    }
}
