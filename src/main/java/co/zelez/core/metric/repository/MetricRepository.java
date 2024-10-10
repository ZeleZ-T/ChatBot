package co.zelez.core.metric.repository;

import co.zelez.core.metric.entity.ChatMetrics;
import lombok.AllArgsConstructor;

import java.sql.*;

@AllArgsConstructor
public class MetricRepository implements IMetricRepository {
    private Connection connection;

    @Override
    public void saveMetric(Timestamp timestamp, long userId) {
        try {
            String query = "INSERT INTO metrics (chat_id, timestamp) VALUES (?, ?);";
            PreparedStatement prepared = connection.prepareStatement(query);
            prepared.setLong(1, userId);
            prepared.setTimestamp(2, timestamp);

            prepared.executeUpdate();
        } catch (Exception ignored) {
        }
    }

    @Override
    public ChatMetrics getMetrics() {
        try {
            String totalMessagesQuery = "SELECT COUNT(*) AS total_messages_answered FROM metrics;";
            String uniqueUsersQuery = "SELECT COUNT(DISTINCT chat_id) AS total_unique_users FROM metrics;";
            String lastWeekMessagesQuery = "SELECT COUNT(*) AS last_week_messages_answered FROM metrics WHERE timestamp > ?;";
            String lastWeekActiveUsersQuery = "SELECT COUNT(DISTINCT chat_id) AS last_week_active_users FROM metrics WHERE timestamp > ?;";

            PreparedStatement totalMessagesStmt = connection.prepareStatement(totalMessagesQuery);
            PreparedStatement uniqueUsersStmt = connection.prepareStatement(uniqueUsersQuery);

            Timestamp lastWeekTimestamp = new Timestamp(System.currentTimeMillis() - 7L * 24 * 60 * 60 * 1000);
            PreparedStatement lastWeekMessagesStmt = connection.prepareStatement(lastWeekMessagesQuery);
            lastWeekMessagesStmt.setTimestamp(1, lastWeekTimestamp);

            PreparedStatement lastWeekActiveUsersStmt = connection.prepareStatement(lastWeekActiveUsersQuery);
            lastWeekActiveUsersStmt.setTimestamp(1, lastWeekTimestamp);

            ResultSet totalMessagesResult = totalMessagesStmt.executeQuery();
            ResultSet uniqueUsersResult = uniqueUsersStmt.executeQuery();
            ResultSet lastWeekMessagesResult = lastWeekMessagesStmt.executeQuery();
            ResultSet lastWeekActiveUsersResult = lastWeekActiveUsersStmt.executeQuery();

            long total_messages_answered = 0;
            long total_unique_users = 0;
            long last_week_messages_answered = 0;
            long last_week_active_users = 0;

            if (totalMessagesResult.next()) {
                total_messages_answered = totalMessagesResult.getLong("total_messages_answered");
            }
            if (uniqueUsersResult.next()) {
                total_unique_users = uniqueUsersResult.getLong("total_unique_users");
            }
            if (lastWeekMessagesResult.next()) {
                last_week_messages_answered = lastWeekMessagesResult.getLong("last_week_messages_answered");
            }
            if (lastWeekActiveUsersResult.next()) {
                last_week_active_users = lastWeekActiveUsersResult.getLong("last_week_active_users");
            }

            ChatMetrics chatMetrics = new ChatMetrics(
                    total_messages_answered,
                    total_unique_users,
                    last_week_messages_answered,
                    last_week_active_users
            );
            return chatMetrics;
        } catch (Exception ignored) {
        }
        return null;
    }
}