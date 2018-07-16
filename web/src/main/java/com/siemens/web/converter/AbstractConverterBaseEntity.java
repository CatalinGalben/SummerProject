package com.siemens.web.converter;

import com.siemens.core.model.BaseEntity;
import com.siemens.web.dto.BaseDTO;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractConverterBaseEntity<Model extends BaseEntity<Integer>, Dto extends BaseDTO>
        extends AbstractConverter<Model, Dto> implements ConverterBaseEntity<Model, Dto> {

    public Set<Integer> convertModelsToIDs(Set<Model> models) {
        return models.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toSet());
    }

    public Set<Integer> convertDTOsToIDs(Set<Dto> dtos) {
        return dtos.stream()
                .map(BaseDTO::getId)
                .collect(Collectors.toSet());
    }

}
