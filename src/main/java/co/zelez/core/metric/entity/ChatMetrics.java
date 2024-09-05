package co.zelez.core.metric.entity;

public record ChatMetrics(
        long totalMessagesAnswered,
        long totalUniqueUsers,
        long lastWeekMessagesAnswered,
        long lastWeekActiveUsers
) { }
