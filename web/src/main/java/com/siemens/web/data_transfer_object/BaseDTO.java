package com.siemens.web.data_transfer_object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseDTO <ID extends Serializable> implements Serializable {

    private ID id;

    @Override
    public String toString() {
        return "BaseDTO{" +
                "id=" + id +
                '}';
    }
}
