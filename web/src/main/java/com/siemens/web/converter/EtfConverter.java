package com.siemens.web.converter;

import com.siemens.core.model.Etf;
import com.siemens.core.model.Fund;
import com.siemens.web.dto.EtfDTO;
import org.springframework.stereotype.Component;

@Component
public class EtfConverter extends AbstractConverterBaseEntity<Etf, EtfDTO> {

    @Override
    public Etf convertDtoToModel(EtfDTO etfDTO) {
        Etf etf = Etf.etfBuilder()
                .type(etfDTO.getType())
                .nav(etfDTO.getNav())
                .ter(etfDTO.getTer())
                .build();
        etf.setId(etfDTO.getId());
        return etf;
    }

    @Override
    public EtfDTO convertModelToDto(Etf etf)
    {
        EtfDTO etfDTO = EtfDTO.etfDtoBuilder()
                .type(etf.getType())
                .nav(etf.getNav())
                .ter(etf.getTer())
                .build();
        etfDTO.setId(etf.getId());
        return etfDTO;

    }
}

