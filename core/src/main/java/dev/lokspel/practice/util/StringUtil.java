package dev.lokspel.practice.util;

import dev.lokspel.practice.config.PracticeConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.apache.commons.lang3.StringUtils;

public final class StringUtil {

  private StringUtil() {}

  public static String cc(String string) {
    if (string == null) {
      return "";
    }
    return LegacyComponentSerializer.legacySection()
        .serialize(LegacyComponentSerializer.legacyAmpersand().deserialize(string));
  }

  public static List<String> cc(List<String> stringlist) {
    List<String> list = new ArrayList<>();
    for (String string : stringlist) {
      list.add(cc(string));
    }
    return list;
  }

  private static final Map<Character, String> LEGACY_TO_MM =
      Map.ofEntries(
          Map.entry('0', "black"),
          Map.entry('1', "dark_blue"),
          Map.entry('2', "dark_green"),
          Map.entry('3', "dark_aqua"),
          Map.entry('4', "dark_red"),
          Map.entry('5', "dark_purple"),
          Map.entry('6', "gold"),
          Map.entry('7', "gray"),
          Map.entry('8', "dark_gray"),
          Map.entry('9', "blue"),
          Map.entry('a', "green"),
          Map.entry('b', "aqua"),
          Map.entry('c', "red"),
          Map.entry('d', "light_purple"),
          Map.entry('e', "yellow"),
          Map.entry('f', "white"),
          Map.entry('k', "obfuscated"),
          Map.entry('l', "bold"),
          Map.entry('m', "strikethrough"),
          Map.entry('n', "underlined"),
          Map.entry('o', "italic"),
          Map.entry('r', "reset"));

  public static String legacyToMiniMessage(String text) {
    if (text == null || text.isEmpty()) {
      return text;
    }

    StringBuilder out = new StringBuilder();

    for (int i = 0; i < text.length(); i++) {
      char c = text.charAt(i);

      if ((c == '&' || c == '§') && i + 1 < text.length()) {
        char code = Character.toLowerCase(text.charAt(++i));

        // hex
        if (code == '#' && i + 6 < text.length()) {
          String hex = text.substring(i + 1, i + 7);

          if (hex.matches("[0-9a-fA-F]{6}")) {
            out.append("<#").append(hex).append(">");
            i += 6;
            continue;
          }
        }

        // legacy
        String tag = LEGACY_TO_MM.get(code);
        if (tag != null) {
          out.append('<').append(tag).append('>');
          continue;
        }

        out.append(c).append(code);
        continue;
      }

      out.append(c);
    }

    return out.toString();
  }

  public static String replaceSecondString(String string, double seconds) {
    if ((seconds == Math.floor(seconds)) && !Double.isInfinite(seconds)) {
      return string
          .replace("%seconds%", String.valueOf(NumberUtil.doubleToInt(seconds)))
          .replace(
              "%secondName%",
              (seconds < 2
                  ? PracticeConfig.language().getSecondName().getOneSecond()
                  : PracticeConfig.language().getSecondName().getUnderOneSecond()));
    } else {
      return string
          .replace("%seconds%", String.valueOf(seconds))
          .replace(
              "%secondName%",
              (seconds < 2
                  ? PracticeConfig.language().getSecondName().getOneSecond()
                  : PracticeConfig.language().getSecondName().getUnderOneSecond()));
    }
  }

  private static final Pattern OBFUSCATION_TAG_PATTERN =
      Pattern.compile("(?i)</?obf(?:uscated)?[^>]*>|[&§]k");

  public static String stripObfuscationTags(String text) {
    if (text == null || text.isEmpty()) {
      return text;
    }

    return OBFUSCATION_TAG_PATTERN.matcher(text).replaceAll("");
  }

  public static String getNormalizedName(String name) {
    return StringUtils.capitalize(name.replace("_", " ").toLowerCase());
  }
}
