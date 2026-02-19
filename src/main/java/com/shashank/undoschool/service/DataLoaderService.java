package com.shashank.undoschool.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shashank.undoschool.document.CourseDocument;
import com.shashank.undoschool.repository.CourseRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class DataLoaderService {

    private final CourseRepository courseRepository;
    private final ObjectMapper objectMapper;

    public DataLoaderService(CourseRepository courseRepository,
                             ObjectMapper objectMapper) {
        this.courseRepository = courseRepository;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void loadData() {
        try {
            ClassPathResource resource = new ClassPathResource("sample-courses.json");
            InputStream inputStream = resource.getInputStream();

            List<CourseDocument> courses = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<CourseDocument>>() {}
            );

            courseRepository.saveAll(courses);

            System.out.println("✅ Sample data indexed into Elasticsearch!");

        } catch (Exception e) {
            System.out.println("❌ Failed to load sample data");
            e.printStackTrace();
        }
    }
}
