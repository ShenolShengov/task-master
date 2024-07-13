package bg.softuni.taskmaster.mappers;

import bg.softuni.taskmaster.model.dto.AnswerDetailsDTO;
import bg.softuni.taskmaster.model.dto.QuestionBaseInfoDTO;
import bg.softuni.taskmaster.model.dto.QuestionDetailsInfoDTO;
import bg.softuni.taskmaster.model.entity.Answer;
import bg.softuni.taskmaster.model.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);


    @Mapping(target = "userUsername", source = "user.username")
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "createdTime", source = "createdTime", dateFormat = "MMM dd, yyyy 'at' kk:mm")
    QuestionDetailsInfoDTO toDetailsInfo(Question question);

    @Mapping(target = "userUsername", source = "user.username")
    @Mapping(target = "createdTime", source = "createdTime", dateFormat = "MMM dd, yyyy 'at' kk:mm")
    AnswerDetailsDTO toAnswerDetails(Answer answer);

    @Mapping(target = "createdTime", source = "createdTime", dateFormat = "MMM dd, yyyy 'at' kk:mm")
    @Mapping(target = "tags", ignore = true)
    QuestionBaseInfoDTO toBaseInfo(Question question);
}
