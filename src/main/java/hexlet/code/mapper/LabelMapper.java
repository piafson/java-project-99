package hexlet.code.mapper;

import hexlet.code.dto.LabelDTO;
import hexlet.code.model.Label;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class LabelMapper {

    public abstract Label map(LabelDTO dto);

    public abstract LabelDTO map(Label model);

    @InheritConfiguration
    public abstract void update(LabelDTO dto, @MappingTarget Label model);
}
