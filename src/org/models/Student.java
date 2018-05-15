package org.models;

import org.staticData.ExceptionMessages;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Student {

    private String userName;
    private LinkedHashMap<String, Course> enrolledCourses;
    private LinkedHashMap<String, Double> marksByCourseName;

    public Student(String userName) {
        this.setUserName(userName);
        this.enrolledCourses = new LinkedHashMap<>();
        this.marksByCourseName = new LinkedHashMap<>();
    }

    public void setUserName(String userName) {
        if (userName == null || userName.equals("")) {
            throw new IllegalArgumentException(ExceptionMessages.NULL_OR_EMPTY_VALUE);
        }

        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public Map<String, Course> getEnrolledCourses() {
        return Collections.unmodifiableMap(this.enrolledCourses);
    }

    public Map<String, Double> getMarksByCourseName() {
        return Collections.unmodifiableMap(this.marksByCourseName);
    }

    public void enrollInCourse(Course course) {
        if (this.enrolledCourses.containsKey(course.getName())) {
            String message = String.format(
                    ExceptionMessages.STUDENT_ALREADY_ENROLLED_IN_COURSE,
                    this.userName, course.getName());
            throw new IllegalArgumentException(message);
        }

        this.enrolledCourses.put(course.getName(), course);
    }

    public void setMarksOnCourse(String courseName, int... scores) {
        if (!this.enrolledCourses.containsKey(courseName)) {
            throw new IllegalArgumentException(ExceptionMessages.NOT_ENROLLED_IN_COURSE);
        }

        if (scores.length > Course.NUMBER_OF_TASKS_ON_EXAM) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_NUMBER_OF_SCORES);
        }

        double mark = calculateMark(scores);
        this.marksByCourseName.put(courseName, mark);
    }

    private double calculateMark(int[] scores) {
        double percentageOfSolvedExam = Arrays.stream(scores).sum() /
                (double) (Course.NUMBER_OF_TASKS_ON_EXAM * Course.MAX_SCORE_ON_EXAM);
        return percentageOfSolvedExam * 4 + 2;
    }
}
