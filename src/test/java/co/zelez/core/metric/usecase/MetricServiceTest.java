package co.zelez.core.metric.usecase;

import co.zelez.core.metric.repository.IMetricRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Date;

import static co.zelez.core.metric.usecase.MetricService.metric;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;

class MetricServiceTest {

    private IMetricRepository repository;
    private MetricService metricService;

    @BeforeEach
    void setUp() {
        repository = spy();
        metricService = metric(repository);
    }

    @Test
    void When_not_null_Then_return_metric_service() {
        //Act
        MetricService result = metric();

        //Assert
        assertEquals(metricService, result);
    }

    @Test
    void When_save_Then_call_repository_save_method() {
        //Arrange
        Timestamp testTime = new Timestamp(123456789L);
        long testUserId = 1234567890;

        doNothing().when(repository).saveMetric(testTime, testUserId);

        //Act
        metric().save(testTime, testUserId);

        //Assert
        verify(repository, atLeastOnce()).saveMetric(testTime, testUserId);
    }

    @Test
    void When_get_Then_call_repository_getMetric() {
        //Arrange
        doReturn(null).when(repository).getMetrics();

        //Act
        metric().get();

        //Assert
        verify(repository, atLeastOnce()).getMetrics();
    }
}