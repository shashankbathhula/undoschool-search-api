package com.shashank.undoschool.controller;

import com.shashank.undoschool.document.CourseDocument;
import com.shashank.undoschool.service.CourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public List<CourseDocument> getAllCourses() {
        return courseService.getAllCourses();
    }
}
