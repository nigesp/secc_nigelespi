package com.secretescapes.codingchallenge.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Nigel on 2016-02-29.
 */
@Controller
@RequestMapping({"/", "/home"})
public class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    public String home() {
        return "redirect:account/list";
    }

}
