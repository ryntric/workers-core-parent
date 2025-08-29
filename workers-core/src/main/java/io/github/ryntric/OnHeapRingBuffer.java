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
        this.buffer = Util.fillEventBuffer(factory, (E[]) new Object[Constants.ARRAY_PADDING * 2 + size]);
    }

    @Override
    public E get(long sequence) {
        return buffer[Util.wrapPaddedIndex(sequence, mask)];
    }

}
