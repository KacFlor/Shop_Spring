package com.example.demo.Carddata;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path = {"carddata"}
)
public class CarddataController {
    private final CarddataService carddataService;

    @Autowired
    public CarddataController(CarddataService carddataService) {
        this.carddataService = carddataService;
    }

    @GetMapping
    public List<Carddata> getCarddata() {return this.carddataService.getCarddata();}
}
