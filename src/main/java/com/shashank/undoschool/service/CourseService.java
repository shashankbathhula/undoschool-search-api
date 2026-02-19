package com.shashank.undoschool.service;

import com.shashank.undoschool.document.CourseDocument;
import com.shashank.undoschool.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseDocument> getAllCourses() {
        return (List<CourseDocument>) courseRepository.findAll();
    }
}
