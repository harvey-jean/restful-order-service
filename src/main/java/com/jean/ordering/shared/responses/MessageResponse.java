package com.jean.ordering.shared.responses;

import lombok.*;

/**
 * Created by Harvey's on 7/10/2023.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
    private int status;
    private String message;
}
