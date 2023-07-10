package com.jean.ordering.validations;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

/**
 * Created by Harvey's on 7/8/2023.
 */
@Service
public class AgeValidator implements Predicate<Integer> {

    @Override
    public boolean test(Integer age) {
        return age >= 18;
    }
}
