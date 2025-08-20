package io.github.ryntric;

/**
 * author: ryntric
 * date: 8/20/25
 * time: 9:51 AM
 **/

public interface ProducerWaitPolicy {
    long await(long wrapPoint, Sequence gatingSequence);
}
