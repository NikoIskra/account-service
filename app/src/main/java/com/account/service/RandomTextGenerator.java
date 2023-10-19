package com.account.service;

import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class RandomTextGenerator {

  static String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

  public static String addRandomSuffixToUsername(String username) {
    Random random = new Random();
    StringBuilder sb = new StringBuilder(4);

    for (int i = 0; i < 4; i++) {
      int randomIndex = random.nextInt(CHARACTERS.length());
      char randomChar = CHARACTERS.charAt(randomIndex);
      sb.append(randomChar);
    }

    return username + "_" + sb;
  }
}
