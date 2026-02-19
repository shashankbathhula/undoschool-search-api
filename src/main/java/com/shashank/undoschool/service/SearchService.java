package com.shashank.undoschool.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.shashank.undoschool.document.CourseDocument;
import com.shashank.undoschool.repository.CourseRepository;

@Service
public class SearchService {

    private final CourseRepository courseRepository;

    public SearchService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

public List<CourseDocument> search(
        String keyword,
        String category,
        String type,
        Double minPrice,
        Double maxPrice,
        Integer minAge,
        Integer maxAge,
        String sort,
        int page,
        int size) {

    List<CourseDocument> results = StreamSupport
            .stream(courseRepository.findAll().spliterator(), false)

            .filter(c -> keyword == null || keyword.isBlank()
                    || c.getTitle().toLowerCase().contains(keyword.toLowerCase())
                    || c.getDescription().toLowerCase().contains(keyword.toLowerCase()))

            .filter(c -> category == null || category.isBlank()
                    || c.getCategory().equalsIgnoreCase(category))

            .filter(c -> type == null || type.isBlank()
                    || c.getType().equalsIgnoreCase(type))

            .filter(c -> minPrice == null || c.getPrice() >= minPrice)
            .filter(c -> maxPrice == null || c.getPrice() <= maxPrice)

            .filter(c -> minAge == null || c.getMinAge() >= minAge)
            .filter(c -> maxAge == null || c.getMaxAge() <= maxAge)

            .collect(Collectors.toList());

    // sorting
    if ("price".equalsIgnoreCase(sort)) {
        results.sort((a, b) -> Double.compare(a.getPrice(), b.getPrice()));
    } else if ("date".equalsIgnoreCase(sort)) {
        results.sort((a, b) -> a.getNextSessionDate().compareTo(b.getNextSessionDate()));
    }

    // pagination
    int start = page * size;
    int end = Math.min(start + size, results.size());

    if (start >= results.size()) {
        return List.of();
    }

    return results.subList(start, end);
    }
}
