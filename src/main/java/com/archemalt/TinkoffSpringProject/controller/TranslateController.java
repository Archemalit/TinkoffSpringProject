package com.archemalt.TinkoffSpringProject.controller;

import com.archemalt.TinkoffSpringProject.service.TranslateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.ExecutionException;

@Controller
@AllArgsConstructor
public class TranslateController {
    public TranslateService translateService;

    @GetMapping("/")
    public String base() {
        return "base";
    }

    @GetMapping("/translate")
    public String translate(@RequestParam(value = "t") String text,
                            @RequestParam(value = "slan") String srcLanguage,
                            @RequestParam(value = "rlan") String resultLanguage,
                            @RequestParam(value = "sep") Character separator,
                            Model model, HttpServletRequest request) {
        try {
            String resultText = translateService.translateText(request.getRemoteAddr(), text, srcLanguage, resultLanguage, separator);
            model.addAttribute("srcLanguage", srcLanguage);
            model.addAttribute("resultLanguage", resultLanguage);
            model.addAttribute("text", text);
            model.addAttribute("resultText", resultText);
            model.addAttribute("separator", separator);
        } catch (ExecutionException | InterruptedException e) {
            throw new IllegalArgumentException(e);
        }
        return "base";
    }
}
