package me.fit.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import me.fit.model.Category;
import me.fit.model.Course;
import me.fit.model.Lesson;
import me.fit.model.Student;

import java.util.ArrayList;
import java.util.List;

@Dependent
public class CourseService {
    @Inject
    EntityManager em;

    @Transactional
    public List<Course> getAllCourses() {
        return em.createNamedQuery("Course.findAll", Course.class).getResultList();
    }

    @Transactional
    public Course getCourseById(Long id) {
        return em.createNamedQuery("Course.findById", Course.class).setParameter("id", id).getSingleResult();
    }

//    @Transactional
//    public Course createCourse(Course course) {
//        if (course.getLessons() != null) {
//            for (Lesson lesson : course.getLessons()) {
//                lesson.setCourse(course);
//                em.persist(lesson);
//            }
//        }
//        return em.merge(course);
//    }
@Transactional
public Course createCourse(Course course) {
    System.out.println("======= DODAVANJE KURSA =======");
    System.out.println("Course name: " + course.getName());
    System.out.println("Lessons size: " + (course.getLessons() != null ? course.getLessons().size() : 0));

    if (course.getLessons() != null) {
        for (Lesson lesson : course.getLessons()) {
            System.out.println("Lesson title: " + lesson.getTitle());
            lesson.setCourse(course);
            em.persist(lesson);
            System.out.println("Lesson persisted: " + lesson.getTitle());
        }
    }

    Course saved = em.merge(course);
    System.out.println("Course saved with id: " + saved.getId());
    return saved;
}

    @Transactional
    public List<Lesson> getLessonsByCourseId(Long id) {
//        return em.createNamedQuery(Lesson.GET_ALL_LESSONS_FOR_COURSE_ID, Lesson.class)
//                .setParameter("id", id)
//                .getResultList();
        List<Lesson> lessons = em.createNamedQuery(Lesson.GET_ALL_LESSONS_FOR_COURSE_ID, Lesson.class)
                .setParameter("id", id)
                .getResultList();

        for (Lesson lesson : lessons) {
            lesson.getUploadedFiles().size();
        }

        return lessons;
    }

    @Transactional
    public List<Course> getCoursesByCategoryId(Long categoryId) {
        return em.createNamedQuery("Course.findByCategoryId", Course.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    @Transactional
    public Course addCategoryToCourse(Long courseId, Long categoryId) {
        Course course = em.find(Course.class, courseId);
        Category category = em.find(Category.class, categoryId);

        if (course == null || category == null) {
            return null;
        }

        if (course.getCategories() == null) {
            course.setCategories(new ArrayList<>());
        }

        course.getCategories().add(category);
        return em.merge(course);
    }

    @Transactional
    public List<Student> getStudentsByCourseId(Long courseId) {
        return em.createQuery(
                        "SELECT s FROM Course c JOIN c.students s WHERE c.id = :courseId",
                        Student.class)
                .setParameter("courseId", courseId)
                .getResultList();
    }
}
