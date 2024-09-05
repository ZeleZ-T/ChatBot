package co.zelez.core.metric.repository;

import co.zelez.core.metric.entity.ChatMetrics;
import lombok.AllArgsConstructor;

import java.sql.*;

@AllArgsConstructor
public class MetricRepository implements IMetricRepository {
    private Connection connection;
    private Statement statement;

    @Override
    public void saveMetric(Timestamp timestamp, long userId) {
        try {
            String query = "INSERT INTO metrics (timestamp, user_id) VALUES (?, ?);";
            PreparedStatement prepared = connection.prepareStatement(query);
            prepared.setTimestamp(1, timestamp);
            prepared.setLong(2, userId);

            statement.executeQuery(query);
        } catch (Exception ignored) { }
    }

    @Override
    public ChatMetrics getMetrics() {
        try {
            String query = "SELECT * FROM metrics;";
            ResultSet resultSet = statement.executeQuery(query);

            ChatMetrics chatMetrics = new ChatMetrics(
                    resultSet.getLong("total_messages_answered"),
                    resultSet.getLong("total_unique_users"),
                    resultSet.getLong("last_week_messages_answered"),
                    resultSet.getLong("last_week_active_users")
            );
            return chatMetrics;
        } catch (Exception ignored) { }
        return null;
    }
}
