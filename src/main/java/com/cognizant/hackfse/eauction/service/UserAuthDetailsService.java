package com.cognizant.hackfse.eauction.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.cognizant.hackfse.eauction.entity.PersonEntity;
import com.cognizant.hackfse.eauction.model.UserPrincipal;
import com.cognizant.hackfse.eauction.repository.PersonRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserAuthDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    public UserAuthDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserPrincipal loadUserByUsername(String s) throws UsernameNotFoundException {
    	log.info(">> inside UserAuthDetailsService >> s {}",s);
        PersonEntity personEntity = personRepository
                .findByEmailAddress(s)
                .orElseThrow(() -> new UsernameNotFoundException("User name " + s + "Not Found in DB"));
        return UserPrincipal.create(personEntity);

    }
}
