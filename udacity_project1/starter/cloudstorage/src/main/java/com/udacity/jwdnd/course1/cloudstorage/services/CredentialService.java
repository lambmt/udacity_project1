package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

public interface CredentialService {
    String insertCredential(@ModelAttribute Credential credential, String userName);

    String deleteCredential( Integer credentialId);
}
