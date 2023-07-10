package com.jean.ordering.validations;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

/**
 * Created by Harvey's on 7/7/2023.
 */
@Service
public class PhoneNumberValidator implements Predicate<String> {
    @Override
    public boolean test(String phone) {
        return phone.startsWith("+48") && phone.length() == 12;
    }
}
