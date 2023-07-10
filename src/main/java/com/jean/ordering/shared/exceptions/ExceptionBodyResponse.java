package com.jean.ordering.shared.exceptions;

import lombok.*;

/**
 * Created by Harvey's on 7/8/2023.
 */
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class ExceptionBodyResponse {

    private int status;
    private String message;

}
