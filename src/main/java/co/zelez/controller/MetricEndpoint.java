package co.zelez.controller;

import co.zelez.core.metric.entity.ChatMetrics;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static co.zelez.core.metric.usecase.MetricService.metric;

@NoArgsConstructor
@RestController
public class MetricEndpoint {
    @GetMapping("/metrics")
    public ChatMetrics getMetrics() {
       return metric().get();
    }
}
