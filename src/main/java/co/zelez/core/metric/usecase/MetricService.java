package co.zelez.core.metric.usecase;

import co.zelez.core.metric.entity.ChatMetrics;
import co.zelez.core.metric.repository.IMetricRepository;
import co.zelez.core.metric.repository.MetricRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;

public class MetricService implements IMetricService{
    private IMetricRepository metricRepository;
    private static MetricService metricService;

    private MetricService(IMetricRepository metricRepository){
        this.metricRepository = metricRepository;
    }

    public static MetricService metric() {
        if (metricService == null) {
            try {
                String url = System.getenv("DATABASE_URL");
                String user = System.getenv("DATABASE_USER");
                String password = System.getenv("DATABASE_PASSWORD");

                Connection connection = DriverManager.getConnection(url, user, password);
                Statement statement = connection.createStatement();
                metricService = metric(new MetricRepository(connection, statement));
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return metricService;
    }

    public static MetricService metric(IMetricRepository repository) {
        metricService = new MetricService(repository);
        return metricService;
    }

    @Override
    public void save(Timestamp timestamp, long userId) {
        metricRepository.saveMetric(timestamp, userId);
    }

    @Override
    public ChatMetrics get() {
        return metricRepository.getMetrics();
    }
}
