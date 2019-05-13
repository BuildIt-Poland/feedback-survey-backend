package com.buildit.survey;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class SurveyMapper {

    Survey mapToEntity(SurveyDTO surveyDTO) {
        Mapper mapper = initMapper();
        return mapper.map(surveyDTO, Survey.class);
    }

    List<SurveyDTO> mapToDTO(List<Survey> surveys) {
        Mapper mapper = initMapper();
        return surveys.stream()
                .map(survey -> mapper.map(survey, SurveyDTO.class))
                .collect(Collectors.toList());
    }

    private DozerBeanMapper initMapper() {
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.setMappingFiles(Collections.singletonList("dozer.xml"));
        return mapper;
    }
}
