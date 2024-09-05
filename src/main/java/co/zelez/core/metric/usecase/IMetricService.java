package co.zelez.core.metric.usecase;

import co.zelez.core.metric.entity.ChatMetrics;

import java.sql.Timestamp;

public interface IMetricService {
    void save(Timestamp timestamp, long userId);
    ChatMetrics get();
}