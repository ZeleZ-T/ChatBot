package co.zelez.core.metric.repository;

import co.zelez.core.metric.entity.ChatMetrics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MetricRepositoryTest {
    private Connection connection;
    private MetricRepository metricRepository;

    @BeforeEach
    void setUp() {
        try {
            connection = spy();
            metricRepository = new MetricRepository(connection);
        } catch (Exception ignored) { }
    }

    @Test
    void When_saveMetric_Then_return_void() throws SQLException {
        //Arrange
        Timestamp testTime = new Timestamp(123456789L);
        long testUserId = 1234567890;

        PreparedStatement prepared = spy();
        doReturn(prepared).when(connection).prepareStatement(any());

        doNothing().when(prepared).setLong(1, testUserId);
        doNothing().when(prepared).setTimestamp(2, testTime);

        doReturn(0).when(prepared).executeUpdate();

        //Act
        metricRepository.saveMetric(testTime, testUserId);

        //Assert
        verify(prepared, atLeastOnce()).setLong(1, testUserId);
        verify(prepared, atLeastOnce()).setTimestamp(2, testTime);
        verify(prepared, atLeastOnce()).executeUpdate();
    }

    @Test
    void When_getMetrics_Then_return_chat_metrics() throws SQLException {
        //Arrange
        String totalMessagesQuery = "SELECT COUNT(*) AS total_messages_answered FROM metrics;";
        String uniqueUsersQuery = "SELECT COUNT(DISTINCT chat_id) AS total_unique_users FROM metrics;";
        String lastWeekMessagesQuery = "SELECT COUNT(*) AS last_week_messages_answered FROM metrics WHERE timestamp > ?;";
        String lastWeekActiveUsersQuery = "SELECT COUNT(DISTINCT chat_id) AS last_week_active_users FROM metrics WHERE timestamp > ?;";

        PreparedStatement totalMessagesStmt = spy();
        PreparedStatement uniqueUsersStmt = spy();
        PreparedStatement lastWeekMessagesStmt = spy();
        PreparedStatement lastWeekActiveUsersStmt = spy();

        doReturn(totalMessagesStmt).when(connection).prepareStatement(totalMessagesQuery);
        doReturn(uniqueUsersStmt).when(connection).prepareStatement(uniqueUsersQuery);
        doReturn(lastWeekMessagesStmt).when(connection).prepareStatement(lastWeekMessagesQuery);
        doReturn(lastWeekActiveUsersStmt).when(connection).prepareStatement(lastWeekActiveUsersQuery);

        doNothing().when(lastWeekMessagesStmt).setTimestamp(eq(1), any(Timestamp.class));
        doNothing().when(lastWeekActiveUsersStmt).setTimestamp(eq(1), any(Timestamp.class));

        ResultSet totalMessagesResult = spy();
        ResultSet uniqueUsersResult = spy();
        ResultSet lastWeekMessagesResult = spy();
        ResultSet lastWeekActiveUsersResult = spy();

        doReturn(totalMessagesResult).when(totalMessagesStmt).executeQuery();
        doReturn(uniqueUsersResult).when(uniqueUsersStmt).executeQuery();
        doReturn(lastWeekMessagesResult).when(lastWeekMessagesStmt).executeQuery();
        doReturn(lastWeekActiveUsersResult).when(lastWeekActiveUsersStmt).executeQuery();

        doReturn(true).when(totalMessagesResult).next();
        doReturn(true).when(uniqueUsersResult).next();
        doReturn(true).when(lastWeekMessagesResult).next();
        doReturn(true).when(lastWeekActiveUsersResult).next();

        doReturn(0L).when(totalMessagesResult).getLong("total_messages_answered");
        doReturn(1L).when(uniqueUsersResult).getLong("total_unique_users");
        doReturn(2L).when(lastWeekMessagesResult).getLong("last_week_messages_answered");
        doReturn(3L).when(lastWeekActiveUsersResult).getLong("last_week_active_users");

        //Act
        ChatMetrics result = metricRepository.getMetrics();

        //Assert
        assertNotNull(result);
        assertEquals(0L, result.totalMessagesAnswered());
        assertEquals(1L, result.totalUniqueUsers());
        assertEquals(2L, result.lastWeekMessagesAnswered());
        assertEquals(3L, result.lastWeekActiveUsers());
    }

}