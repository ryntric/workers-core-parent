package io.github.ryntric;

/**
 * author: ryntric
 * date: 8/20/25
 * time: 10:08 AM
 **/

final class ProducerSpinningPolicy implements ProducerWaitPolicy {

    @Override
    public long await(long wrapPoint, Sequence gatingSequence) {
        long gating = 0L;
        while (wrapPoint > (gating = gatingSequence.getAcquire())) {
            Thread.onSpinWait();
        }
        return gating;
    }
}
