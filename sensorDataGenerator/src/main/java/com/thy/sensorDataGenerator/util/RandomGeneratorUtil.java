package com.thy.sensorDataGenerator.util;

import com.thy.sensorDataGenerator.model.PropulsionType;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;

public final class RandomGeneratorUtil {

    public static int getRandomInt(int upperLimit) {
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.nextInt(upperLimit);
    }

    public static double getRandomDouble(double upperLimit) {
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.nextDouble(upperLimit);
    }

    public static String getRandomString(int length, boolean useLetters, boolean useNumbers) {
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    public static PropulsionType getRandomPropulsionType() {
        SecureRandom secureRandom = new SecureRandom();
        PropulsionType[] values = PropulsionType.values();
        return values[secureRandom.nextInt(values.length)];
    }

    public static long getRandomTimeStamp() {
        SecureRandom secureRandom = new SecureRandom();
        long upper = System.currentTimeMillis();
        long lower = upper - 100000;

        return secureRandom.nextLong(lower, upper);
    }
}
