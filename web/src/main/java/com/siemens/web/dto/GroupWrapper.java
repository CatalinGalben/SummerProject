package com.siemens.web.dto;

import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupWrapper {
    private String groupName;
    private Set<CompanyDTO> companies;
    private UserDTO user;
}
