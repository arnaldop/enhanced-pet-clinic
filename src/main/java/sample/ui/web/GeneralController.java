package sample.ui.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@ControllerAdvice
public class GeneralController {

    @RequestMapping("/")
    public String index() {
        return "welcome";
    }

    @RequestMapping(value = "/router")
    public String accessDeniedRouter(@RequestParam("q") String resource) {
        return "redirect:/" + resource;
    }

//    @RequestMapping(value = "/logout")
//    public String logoutSuccess() {
//        return "redirect:/";
//    }

    @RequestMapping(value = "/unauthorized")
    public ModelAndView accessDenied() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("timestamp", new Date());
        mav.setViewName("unauthorized");
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest req, Exception e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("timestamp", new Date());
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("exception");
        return mav;
    }
}
