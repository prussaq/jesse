package net.prussaq.controller.rest;

import lombok.AllArgsConstructor;
import net.prussaq.model.Equity;
import net.prussaq.service.EquityService;
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
