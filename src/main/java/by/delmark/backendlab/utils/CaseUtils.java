package by.delmark.backendlab.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CaseUtils {

    public String toCamelCase(String input) {
        if (input.indexOf('_') == -1) {
            return input;
        }
        StringBuilder sb = new StringBuilder(input.length());
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (currentChar == '_') {
                if (i + 1 < input.length() && input.charAt(i + 1) != '_') {
                    sb.append(Character.toUpperCase(input.charAt(i + 1)));
                    i++; // пропускаем след. символ
                }
                continue;
            }
            sb.append(currentChar);
        }

        if (!sb.isEmpty() && Character.isUpperCase(sb.charAt(0))) {
            sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
        }

        return sb.toString();
    }
}
