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
    private Statement statement;
    private MetricRepository metricRepository;

    @BeforeEach
    void setUp() {
        try {
            connection = spy();
            statement = spy();
            metricRepository = new MetricRepository(connection, statement);
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
        ResultSet resultSet = spy();
        doReturn(resultSet).when(statement).executeQuery(anyString());

        doReturn(1L).when(resultSet).getLong("total_messages_answered");
        doReturn(2L).when(resultSet).getLong("total_unique_users");
        doReturn(3L).when(resultSet).getLong("last_week_messages_answered");
        doReturn(4L).when(resultSet).getLong("last_week_active_users");

        //Act
        ChatMetrics result = metricRepository.getMetrics();

        //Assert
        assertNotNull(result);
        assertEquals(1L, result.totalMessagesAnswered());
        assertEquals(2L, result.totalUniqueUsers());
        assertEquals(3L, result.lastWeekMessagesAnswered());
        assertEquals(4L, result.lastWeekActiveUsers());
    }

}