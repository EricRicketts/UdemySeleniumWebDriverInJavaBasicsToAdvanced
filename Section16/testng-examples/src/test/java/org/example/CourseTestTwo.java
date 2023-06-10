package org.example;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class CourseTestTwo {

    private Course firstCourse, secondCourse;

    @BeforeMethod
    public void setUp() {
        firstCourse = new Course();
        secondCourse = new Course(
                "Selenium WebDriver with Java - Basics to Advanced + Frameworks",
                "Rahul Shetty",
                new BigDecimal("54.5"),
                new BigDecimal("112.99")
        );
    }

    @Test
    public void testGetSecondCourseTitle() {
        String expectedTitle = "Selenium WebDriver with Java - Basics to Advanced + Frameworks";
        Assert.assertEquals(secondCourse.getTitle(), expectedTitle);
    }

    @Test
    public void testGetSecondCourseInstructor() {
        String expectedInstructor = "Rahul Shetty";
        Assert.assertEquals(secondCourse.getInstructor(), expectedInstructor);
    }

    @Test
    public void testGetSecondCourseHours() {
        BigDecimal expectedHours = new BigDecimal("54.5");
        Assert.assertEquals(secondCourse.getHours(), expectedHours);
    }

    @Test
    public void testGetSecondCoursePrice() {
        BigDecimal expectedCost = new BigDecimal("112.99");
        Assert.assertEquals(secondCourse.getCost(), expectedCost);
    }

    @Test
    public void testSetFirstCourseTitle() {
        String title = "Java Programming Master Class";
        firstCourse.setTitle(title);
        Assert.assertEquals(firstCourse.getTitle(), title);
    }

    @Test
    public void testSetFirstCourseInstructor() {
        String instructor = "Tim Buchalka";
        firstCourse.setInstructor(instructor);
        Assert.assertEquals(firstCourse.getInstructor(), instructor);
    }

    @Test
    public void testSetFirstCourseHours() {
        BigDecimal hours = new BigDecimal("118.5");
        firstCourse.setHours(hours);
        Assert.assertEquals(firstCourse.getHours(), hours);
    }

    @Test
    public void testSetFirstCourseCost() {
        BigDecimal cost = new BigDecimal("129.99");
        firstCourse.setCost(cost);
        Assert.assertEquals(firstCourse.getCost(), cost);
    }
}
