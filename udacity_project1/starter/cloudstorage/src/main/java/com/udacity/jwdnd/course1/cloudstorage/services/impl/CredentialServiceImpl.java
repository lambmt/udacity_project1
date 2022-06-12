package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.constant.CommonConstants;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class CredentialServiceImpl implements CredentialService {

    private final UserService userService;
    private final EncryptionService encryptionService;
    private final CredentialMapper credentialMapper;

    public CredentialServiceImpl(UserService userService, EncryptionService encryptionService, CredentialMapper credentialMapper) {
        this.userService = userService;
        this.encryptionService = encryptionService;
        this.credentialMapper = credentialMapper;
    }

    @Override
    public String insertCredential(Credential credential, String userName) {
        Integer userId = userService.getUser(userName).getUserId();

        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[16];
        secureRandom.nextBytes(key);
        credential.setKey(Base64.getEncoder().encodeToString(key));
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), credential.getKey()));

        if (credential.getCredentialId() != null) {
            int res = credentialMapper.updateCredential(credential);
            if (res < 0) {
                return CommonConstants.UPLOAD_CREDENTIAL_FAIL;
            }
        } else {

            int res = credentialMapper.insertCredential(credential, userId);
            if (res < 0) {
                return CommonConstants.UPDATE_CREDENTIAL_FAIL;
            }
        }
        return null;
    }

    @Override
    public String deleteCredential(Integer credentialId) {
        int result = credentialMapper.deleteCredential(credentialId);
        if (result < 0) {
            return CommonConstants.DELETE_CREDENTIAL_FAIL;
        }
        return null;
    }
}
