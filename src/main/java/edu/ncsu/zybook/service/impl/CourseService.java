package edu.ncsu.zybook.service.impl;

import edu.ncsu.zybook.domain.model.Chapter;
import edu.ncsu.zybook.domain.model.Course;
import edu.ncsu.zybook.domain.model.Section;
import edu.ncsu.zybook.service.ICourseService;
import edu.ncsu.zybook.persistence.repository.ICourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService implements ICourseService {

    private final ICourseRepository courseRepository;

    public CourseService(ICourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    @Override
    public Course create(Course course) {
        Optional<Course> result = courseRepository.findById(course.getCourseId());
        if (result.isEmpty()) {
            return courseRepository.create(course);
        }
        else{
            throw new RuntimeException("Course already exists!");
        }
    }

    @Override
    @Transactional
    public Optional<Course> update(Course course) {
        if(courseRepository.findById(course.getCourseId()).isPresent()){
            return courseRepository.update(course);
        }
        else{
            throw new RuntimeException("Course does not exist with id:" + course.getCourseId());
        }
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        Optional<Course> result = courseRepository.findById(id);
        if (result.isPresent()) {
            return courseRepository.delete(id);
        }
        else{
            throw new RuntimeException("Course does not exist with id:" + id);
        }
    }

    @Override
    public Optional<Course> findById(String id) {
        Optional<Course> result = courseRepository.findById(id);
        if(result.isPresent()) {
            Course course = result.get();
            //List<Section> sections = sectionRepository.findAllByChapter(tbookId, cno);
            return Optional.of(course);
        }
        return Optional.empty();
    }

    @Override
    public List<Course> findAll(int offset, int limit, String sortBy, String sortDirection) {
        List<Course> result = courseRepository.findAll(offset, limit, sortBy, sortDirection);
        return result;
    }

    @Override
    public Optional<Course> findByTitle(String title) {
        Optional<Course> result = courseRepository.findByTitle(title);
        if(result.isPresent()) {
            Course course = result.get();
            return Optional.of(course);
        }
        else{
            throw new RuntimeException("Course does not exist with title:" + title);
        }
    }
}
