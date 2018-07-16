package com.siemens.web.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BaseDTO implements Serializable {

    private Integer id;
}
