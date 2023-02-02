package net.blueva.core.utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class StringUtil {
  public static @NotNull String listToString(ArrayList<String> list, String spacer) {
    return listToString(list, spacer, spacer);
  }
  
  public static @NotNull String listToString(@NotNull ArrayList<String> list, String spacer, String lastspacer) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < list.size(); i++) {
      if (i == list.size() - 1) {
        sb.append(list.get(i));
      } else if (i == list.size() - 2) {
        sb.append(String.valueOf(list.get(i)) + lastspacer);
      } else {
        sb.append(String.valueOf(list.get(i)) + spacer);
      } 
    } 
    return sb.toString();
  }
}
