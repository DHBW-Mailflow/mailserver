package de.dhbw.karlsruhe.students.mailflow.core.application.imap.james;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.james.metrics.api.Metric;

public class JamesRecordingMetric implements Metric {
    private final AtomicInteger value;

    JamesRecordingMetric() {
        this(new AtomicInteger());
    }

    JamesRecordingMetric(AtomicInteger value) {
        this.value = value;
    }

    @Override
    public void increment() {
        value.incrementAndGet();
    }

    @Override
    public void decrement() {
        value.updateAndGet(currentValue -> subtractFrom(currentValue, 1));
    }

    @Override
    public void add(int i) {
        value.addAndGet(i);
    }

    @Override
    public void remove(int i) {
        value.updateAndGet(currentValue -> subtractFrom(currentValue, i));
    }

    @Override
    public long getCount() {
        return value.longValue();
    }

    private int subtractFrom(int currentValue, int minus) {
        int result = currentValue - minus;
        if (result < 0) {
            throw new UnsupportedOperationException("metric counter is supposed to be a non-negative number," +
                    " thus this operation cannot be applied");
        }
        return result;
    }
}
