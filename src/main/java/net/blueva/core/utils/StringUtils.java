/*
 *  ____  _             ____
 * | __ )| |_   _  ___ / ___|___  _ __ ___
 * |  _ \| | | | |/ _ | |   / _ \| '__/ _ \
 * | |_) | | |_| |  __| |__| (_) | | |  __/
 * |____/|_|\__,_|\___|\____\___/|_|  \___|
 *
 * This file is part of Blue Core.
 *
 * Blue Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 as
 * published by the Free Software Foundation.
 *
 * Blue Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License version 3 for more details.
 *
 * Blue Core plugin developed by Blueva Development.
 * Website: https://blueva.net/
 * GitHub repository: https://github.com/BluevaDevelopment/BlueCore
 *
 * Copyright (c) 2024 Blueva Development. All rights reserved.
 */

package net.blueva.core.utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StringUtils {
  public static @NotNull String listToString(ArrayList<String> list, String spacer) {
    return listToString(list, spacer, spacer);
  }
  
  public static @NotNull String listToString(@NotNull ArrayList<String> list, String spacer, String lastspacer) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < list.size(); i++) {
      if (i == list.size() - 1) {
        sb.append(list.get(i));
      } else if (i == list.size() - 2) {
        sb.append(list.get(i)).append(lastspacer);
      } else {
        sb.append(list.get(i)).append(spacer);
      } 
    } 
    return sb.toString();
  }

  public static boolean isNumber(String str) {
    String numericRegex = "^\\d*\\.?\\d+$";
    return str.matches(numericRegex);
  }

  public static boolean isNumber(String[] array) {
    String numericRegex = "^\\d*\\.?\\d+$";
    for (String element : array) {
      if (!element.matches(numericRegex)) {
        return false;
      }
    }
    return true;
  }
}
