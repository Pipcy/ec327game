// HIGH LEVEL DOCUMENTATION FOR TESTS

/* 
Unit tests were implemented for all applicable functions, especially those that format data to be displayed onto the screen, and those accessing and processing data through APIs. These were done through methods such as mocking. 

Interface and system tests were implemented manually through user input and demos. For example, we know that a function works when its intended result shows up on the screen.
/*

package com.example.game2d;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;
//
import android.content.Context;
import android.content.SharedPreferences;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {
    @Mock
    ChalkActivity testObj;
    CharacterActivity testObj2;


    @Test
    public void randomizeOrderTest() {
        int[] original = new int[] { 0, 1, 2, 3 };
        ChalkActivity testObj = new ChalkActivity();
        int[] created = testObj.randomizeOrders();
        for (int i = 0; i < 4; i++) {
            System.out.print(created[i]);
            System.out.print(" ");
        }
        System.out.println();
        assertEquals(original, testObj.randomizeOrders());
    }
    // QuestionAnswer.java
    @Test
    public void firstReturnsCorrectString () {
        List<String> fullName = new ArrayList<>();
        fullName.add("Antonio Alonso - AntonioAlonso - 18 Jan 2023");
        fullName.add("Efe Sencan - EfeSencan - 18 Jan 2023");
        fullName.add("Rishav De - RishavDe - 18 Jan 2023");
        QuestionAnswer testObj = new QuestionAnswer();

        String result1 = testObj.first(fullName, 0);
        String result2 = testObj.first(fullName, 1);
        String result3 = testObj.first(fullName, 2);

        //System.out.println(result1);
        //System.out.println(result2);
        //System.out.println(result3);

        assertEquals("Antonio", result1);
        assertEquals("Efe", result2);
        assertEquals("Rishav", result3);
    }

    @Test
    public void lastReturnsCorrectString () {
        List<String> fullName = new ArrayList<>();
        fullName.add("Antonio Alonso - AntonioAlonso - 18 Jan 2023");
        fullName.add("Efe Sencan - EfeSencan - 18 Jan 2023");
        fullName.add("Rishav De - RishavDe - 18 Jan 2023");
        QuestionAnswer testObj = new QuestionAnswer();

        String result1 = testObj.last(fullName, 0);
        String result2 = testObj.last(fullName, 1);
        String result3 = testObj.last(fullName, 2);

        //System.out.println(result1);
        //System.out.println(result2);
        //System.out.println(result3);

        assertEquals("Alonso", result1);
        assertEquals("Sencan", result2);
        assertEquals("De", result3);
    }

    @Test
    public void randomIndexTest() {
        List<String> fullName = new ArrayList<>();
        fullName.add("Antonio Alonso - AntonioAlonso - 18 Jan 2023");
        fullName.add("Efe Sencan - EfeSencan - 18 Jan 2023");
        fullName.add("Rishav De - RishavDe - 18 Jan 2023");
        QuestionAnswer testObj = new QuestionAnswer();

        for (int i = 0; i < 10; i++) {
            int result = testObj.randomIndex(fullName);
            System.out.print(result);
            System.out.print(" ");
            Assert.assertTrue(result >= 0 && result <= 3);
        }
        System.out.println();
    }

    @Test
    public void saveNameTest() {
        String username = "AriBerg";
        CharacterActivity testObj = new CharacterActivity();

        String expectedID = UUID.randomUUID().toString();
        testObj.SaveName(username);

        SharedPreferences user_info = testObj.getSharedPreferences("USER_CREDENTIALS", 0);
        String actualID = user_info.getString("user_ID", null);
        Assert.assertNotNull(actualID);
        if (actualID.equals(expectedID)) {
            Assert.assertEquals(expectedID, actualID);
        } else {
            Assert.assertTrue(actualID.matches("^[a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8}$"));
        }
    }

    @Test
    public void restartQuizTest() {
        NameActivity testObj = new NameActivity();

        testObj.restartQuiz();
        Assert.assertTrue(NameActivity.score == 0);
    }

    @Test
    public void correctAnswer() {
        ChalkQuestionAnswer testObj = new ChalkQuestionAnswer();
        boolean isThere = false;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (testObj.correctAnswers[i] == testObj.choices[i][j]) {
                    isThere = true;
                }
            }
            assertTrue(isThere);
        }
    }
}
