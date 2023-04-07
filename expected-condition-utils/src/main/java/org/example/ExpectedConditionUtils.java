package org.example;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

/*
 * Created by dennis.sandjaya
 * Date on 2020-12-04
 */
public class ExpectedConditionUtils {

    public static ExpectedCondition<Boolean> isVisibleInViewport(WebElement element) {
        return new ExpectedCondition<>() {
            @Nullable
            @Override
            public Boolean apply(@Nullable WebDriver input) {
                if (input != null) {
                    return (Boolean) ((JavascriptExecutor) input).executeScript(
                            "var element = arguments[0]; " +
                                    "var rect = element.getBoundingClientRect(); " +
                                    "return ( " +
                                    "rect.top >= 0 && " +
                                    "rect.left >= 0 && " +
                                    "rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && " +
                                    "rect.right <= (window.innerWidth || document.documentElement.clientWidth) " +
                                    ");"
                            , element);
                } else {
                    return false;
                }
            }
        };
    }

    public static ExpectedCondition<Boolean> isNotObstructed(WebElement element) {
        return new ExpectedCondition<>() {
            @Nullable
            @Override
            public Boolean apply(@Nullable WebDriver input) {
                if (input != null) {
                    return (Boolean) ((JavascriptExecutor) input).executeScript(
                            "var element = arguments[0]; " +
                                    "var rect = element.getBoundingClientRect(); " +
                                    "var cx = rect.left + rect.width / 2; " +
                                    "var cy = rect.top + rect.height / 2; " +
                                    "var el = document.elementFromPoint(cx, cy); " +
                                    "var isNotObstructed = false; " +
                                    "for(; el; el = el.parentElement) { " +
                                    "   if (el === element) { " +
                                    "      isNotObstructed = true; " +
                                    "      break;" +
                                    "   }" +
                                    "}" +
                                    "return isNotObstructed;"
                            , element);
                } else {
                    return false;
                }
            }
        };
    }

    public static ExpectedCondition<Boolean> isVisibleInViewportAndNotObstructed(WebElement element) {
        return new ExpectedCondition<>() {
            @Nullable
            @Override
            public Boolean apply(@Nullable WebDriver input) {

                if (input != null) {
                    return (Boolean) ((JavascriptExecutor) input).executeScript(
                            "var element = arguments[0]; " +
                                    "var rect = element.getBoundingClientRect(); " +
                                    "var cx = rect.left + rect.width / 2; " +
                                    "var cy = rect.top + rect.height / 2; " +
                                    "var el = document.elementFromPoint(cx, cy); " +
                                    "var isInViewport = ( " +
                                    "   rect.top >= 0 && " +
                                    "   rect.left >= 0 && " +
                                    "   rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && " +
                                    "   rect.right <= (window.innerWidth || document.documentElement.clientWidth) " +
                                    "); " +
                                    "var isNotObstructed = false; " +
                                    "for(; el; el = el.parentElement) { " +
                                    "   if (el === element) { " +
                                    "      isNotObstructed = true; " +
                                    "      break; " +
                                    "   } " +
                                    "} " +
                                    "return isInViewport && isNotObstructed;"
                            , element);
                } else {
                    return false;
                }
            }
        };
    }

}