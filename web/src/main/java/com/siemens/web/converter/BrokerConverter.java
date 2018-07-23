package com.siemens.web.converter;

import com.siemens.core.model.Broker;
import com.siemens.web.dto.BrokerDTO;
import org.springframework.stereotype.Component;

@Component
public class BrokerConverter extends AbstractConverterBaseEntity<Broker, BrokerDTO> {

    @Override
    public Broker convertDtoToModel(BrokerDTO brokerDTO){
        Broker broker = Broker.builder()
                .name(brokerDTO.getName())
                .profit(brokerDTO.getProfit())
                .dividendFee(brokerDTO.getDividendFee())
                .shareFee(brokerDTO.getShareFee())
                .build();
        broker.setId(brokerDTO.getId());
        return broker;
    }

    @Override
    public BrokerDTO convertModelToDto(Broker broker) {
        BrokerDTO brokerDTO = BrokerDTO.builder()
                .name(broker.getName())
                .profit(broker.getProfit())
                .dividendFee(broker.getDividendFee())
                .shareFee(broker.getShareFee())
                .build();
        brokerDTO.setId(broker.getId());
        return brokerDTO;
    }
}
