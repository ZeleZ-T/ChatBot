package co.zelez.core.metric.repository;

import co.zelez.core.metric.entity.ChatMetrics;

import java.sql.Timestamp;

public interface IMetricRepository {
    void saveMetric(Timestamp timestamp, long userId);
    ChatMetrics getMetrics();
}
