package io.github.ryntric;

import io.github.ryntric.util.Util;

/**
 * author: ryntric
 * date: 8/8/25
 * time: 10:38 PM
 **/

@SuppressWarnings("unchecked")
public final class OnHeapRingBuffer<E> extends AbstractRingBuffer<E> {
    private final E[] buffer;

    public OnHeapRingBuffer(EventFactory<E> factory, SequencerType sequencerType, WaitPolicy waitPolicy, int size) {
        super(size, sequencerType, waitPolicy);
        this.buffer = Util.fillEventBuffer(factory, (E[]) new Object[(Constants.OBJECT_ARRAY_PADDING << 1) + size]);
    }

    private int wrapIndex(long sequence, long mask) {
        return Util.wrapIndex(sequence, mask) + Constants.OBJECT_ARRAY_PADDING;
    }

    @Override
    public E get(long sequence) {
        return buffer[wrapIndex(sequence, mask)];
    }

}
