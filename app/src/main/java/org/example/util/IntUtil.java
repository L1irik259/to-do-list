package org.example.util;

import java.util.Map;

public class IntUtil {
    // Маппинг приоритетов
    private static final Map<String, Integer> PRIORITY_MAP = Map.of(
        "низкий", 1,
        "низкая", 1,
        "low", 1,
        "средний", 2,
        "средняя", 2,
        "medium", 2,
        "высокий", 3,
        "высокая", 3,
        "high", 3
    );

    // Маппинг статусов
    private static final Map<String, Integer> STATUS_MAP = Map.of(
        "в процессе", 1,
        "в работе", 1,
        "in progress", 1,
        "завершена", 2,
        "выполнена", 2,
        "done", 2,
        "отложена", 3,
        "отложено", 3,
        "отложенный", 3,
        "pending", 3
    );

    public static int parsePriority(String priorityStr) {
        return parseValue(priorityStr, PRIORITY_MAP, "приоритет");
    }

    public static int parseStatus(String statusStr) {
        return parseValue(statusStr, STATUS_MAP, "статус");
    }
    
    private static int parseValue(String valueStr, Map<String, Integer> valueMap, String valueType) {
        if (valueStr == null || valueStr.trim().isEmpty()) {
            throw new IllegalArgumentException(String.format("%s не может быть пустым", valueType));
        }

        String normalized = valueStr.trim().toLowerCase();
        Integer value = valueMap.get(normalized);

        if (value == null) {
            throw new IllegalArgumentException(
                String.format("Неизвестный %s: '%s'. Допустимые значения: %s",
                valueType, valueStr, String.join(", ", valueMap.keySet()))
            );
        }

        return value;
    }

    public static boolean isValidPriority(String priorityStr) {
        return isValidValue(priorityStr, PRIORITY_MAP);
    }

    public static boolean isValidStatus(String statusStr) {
        return isValidValue(statusStr, STATUS_MAP);
    }

    private static boolean isValidValue(String valueStr, Map<String, Integer> valueMap) {
        try {
            parseValue(valueStr, valueMap, "");
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}