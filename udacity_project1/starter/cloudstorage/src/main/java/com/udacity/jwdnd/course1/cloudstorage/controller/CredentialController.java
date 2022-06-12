package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class CredentialController {
    private final CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }


    @PostMapping("/credential")
    public String insertCredential(@ModelAttribute Credential credential, Principal principal, Model model) {

        String userName = principal.getName();
        String error = credentialService.insertCredential(credential, userName);
        if (error != null) {
            model.addAttribute("error", error);
        } else {
            model.addAttribute("success", true);
        }
        return "res";
    }

    @GetMapping("/credential/delete")
    public String deleteCredential(@RequestParam Integer credentialId, Model model) {
        String error = credentialService.deleteCredential(credentialId);
        if (error != null) {
            model.addAttribute("error", error);
        } else {
            model.addAttribute("success", true);
        }
        return "res";
    }

}
