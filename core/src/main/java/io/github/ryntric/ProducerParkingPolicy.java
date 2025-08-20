package io.github.ryntric;

import java.util.concurrent.locks.LockSupport;

/**
 * author: ryntric
 * date: 8/20/25
 * time: 10:19 AM
 **/

final class ProducerParkingPolicy implements ProducerWaitPolicy {

    @Override
    public long await(long wrapPoint, Sequence gatingSequence) {
        long gating = 0L;
        while (wrapPoint > (gating = gatingSequence.getAcquire())) {
            LockSupport.parkNanos(1L);
        }
        return gating;
    }
}
