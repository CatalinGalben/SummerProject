package com.siemens.web.dto;

import lombok.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO extends BaseDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String type;
    private Double balance;
    private String dob;
    private Set<HoldingRecordDTO> holdingRecords;

    @Override
    public String toString() {
        return "UserDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", type='" + type + '\'' +
                ", dob=" + dob +
                ", balance=" + balance +
                ", holdingRecords=" + holdingRecords +
                '}';
    }
}
