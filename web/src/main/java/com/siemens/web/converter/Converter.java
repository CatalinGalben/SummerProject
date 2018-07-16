package com.siemens.web.converter;

import model.BaseEntity;
import dto.BaseDto;



public interface Converter<Model,Dto> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);

}