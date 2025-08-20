package io.github.ryntric;

/**
 * author: vbondarchuk
 * date: 8/20/25
 * time: 10:50 AM
 **/

final class ProducerYieldingPolicy implements ProducerWaitPolicy {

    @Override
    public long await(long wrapPoint, Sequence gatingSequence) {
        long gating = 0L;
        while (wrapPoint > (gating = gatingSequence.getAcquire())) {
            Thread.yield();
        }
        return gating;
    }
}
