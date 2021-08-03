package com.notion.service.review.domain.mapper;


import com.notion.service.review.domain.dto.ReviewRequestDto;
import com.notion.service.review.domain.dto.ReviewResponseDto;
import com.notion.service.review.domain.entity.Review;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class ReviewMapper {
    private final ModelMapper modelMapper;

    public ReviewMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

    public Review mapToEntity(ReviewRequestDto reviewRequestDto) {
        return modelMapper.map(reviewRequestDto, Review.class);
    }

    public ReviewResponseDto mapToDto(Review review) {
        return modelMapper.map(review, ReviewResponseDto.class);
    }


}
