package com.thy.sensorDataGenerator.util;

import com.thy.sensorDataGenerator.model.PropulsionType;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class RandomGeneratorUtilTest {

    @Test
    void getRandomIntTest() {
        int upperValue = 1000;
        int randomInt = RandomGeneratorUtil.getRandomInt(upperValue);
        assertTrue(0<=randomInt,"Random generated value should be greater than or equal to 0.");
        assertTrue(randomInt<=upperValue,"Random generated value should be less than or equal to upper value.");
    }

    @Test
    void getRandomDoubleTest() {
        double upperValue = 1000.0;
        double randomDouble = RandomGeneratorUtil.getRandomDouble(upperValue);
        assertTrue(0<=randomDouble,"Random generated value should be greater than or equal to 0.");
        assertTrue(randomDouble<=upperValue,"Random generated value should be less than or equal to upper value.");
    }

    @Test
    void getRandomStringTest() {
        int length = 20;
        boolean useLetters = true;
        boolean useNumbers = false;
        String randomString = RandomGeneratorUtil.getRandomString(length, useLetters, useNumbers);
        assertEquals(length, randomString.length(), "The length of random generated value should be equal to " + length);
        assertTrue(Pattern.matches("[a-zA-Z]+", randomString),"Random generated value should contain only letters.");
    }

    @Test
    void getRandomStringTest2() {
        int length = 20;
        boolean useLetters = false;
        boolean useNumbers = true;
        String randomString = RandomGeneratorUtil.getRandomString(length, useLetters, useNumbers);
        assertEquals(length, randomString.length(), "The length of random generated value should be equal to " + length);
        assertTrue(Pattern.matches("[0-9]+", randomString),"Random generated value should contain only numbers.");
    }

    @Test
    void getRandomStringTest3() {
        int length = 20;
        boolean useLetters = true;
        boolean useNumbers = true;
        String randomString = RandomGeneratorUtil.getRandomString(length, useLetters, useNumbers);
        assertEquals(length, randomString.length(), "The length of random generated value should be equal to " + length);
        assertTrue((Pattern.matches(".*[0-9].*", randomString) &&
                        Pattern.matches(".*[A-Z].*", randomString)),
                "Random generated value should contain both numbers and letters." + ", generated string: " + randomString);
    }

    @Test
    void getRandomPropulsionTypeTest() {
        assertNotNull(RandomGeneratorUtil.getRandomPropulsionType(), "Generated PropulsionType object should not be null.");
    }

    @Test
    void getRandomTimeStampTest() {
        long randomTimeStamp = RandomGeneratorUtil.getRandomTimeStamp();

        long upper = System.currentTimeMillis();
        long lower = upper - 100000;

        assertTrue(lower<=randomTimeStamp,
                "Random generated timestamp value should be greater than or equal or lower value " + lower);
        assertTrue(randomTimeStamp<=upper,
                "Random generated timestamp value should be less than or equal or upper value " + upper);

    }
}