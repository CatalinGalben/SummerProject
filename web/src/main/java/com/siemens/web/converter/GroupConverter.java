package com.siemens.web.converter;

import com.siemens.core.model.Group;
import com.siemens.web.dto.GroupDTO;
import org.springframework.stereotype.Component;

@Component
public class GroupConverter extends AbstractConverterBaseEntity<Group, GroupDTO> {

    @Override
    public Group convertDtoToModel(GroupDTO groupDTO){
        Group group = Group.builder()
                .name(groupDTO.getName())
                .parentGroup(null)
                .build();
        group.setId(groupDTO.getId());
        return group;
    }

    @Override
    public GroupDTO convertModelToDto(Group group){
        GroupDTO groupDTO = GroupDTO.builder()
                .name(group.getName())
                .parentGroupID(group.getParentGroup().getId())
                .build();
        groupDTO.setId(group.getId());
        return groupDTO;
    }

}
