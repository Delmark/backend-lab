package by.delmark.backendlab.mapstruct;

import by.delmark.backendlab.pojo.model.User;
import by.delmark.backendlab.pojo.request.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(target = "login", source = "login"),
            @Mapping(target = "password", source = "password")
    })
    User toModel(UserRequest userRequest);
}
