package com.siemens.web.converter;

import com.siemens.core.model.BaseEntity;
import com.siemens.web.dto.BaseDTO;

public interface ConverterBaseEntity<Model extends BaseEntity<Integer>, Dto extends BaseDTO>
        extends Converter<Model, Dto> {
}
