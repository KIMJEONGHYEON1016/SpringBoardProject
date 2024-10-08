package org.zzzang.tour.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zzzang.global.exceptions.ExceptionProcessor;

import java.util.List;

@Controller
@RequestMapping("/tour")
public class TourController implements ExceptionProcessor {
    @GetMapping("/view")
    public String view(Model model) {

        model.addAttribute("addCommonScript", List.of("map"));
        model.addAttribute("addScript", List.of("tour/view"));
        return "front/tour/view";
    }
}
