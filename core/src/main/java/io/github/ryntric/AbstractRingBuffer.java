package io.github.ryntric;

import static io.github.ryntric.SequencerType.SINGLE_PRODUCER;

/**
 * author: vbondarchuk
 * date: 8/29/25
 * time: 1:58 PM
 **/

public abstract class AbstractRingBuffer<E> {
    protected final int size;
    protected final long mask;
    protected final Sequencer sequencer;

    protected AbstractRingBuffer(int size, SequencerType sequencerType, WaitPolicy waitPolicy) {
        this.size = size;
        this.mask = size - 1;
        this.sequencer = sequencerType == SINGLE_PRODUCER ? new OneToOneSequencer(waitPolicy, size) : new ManyToOneSequencer(waitPolicy, size);
    }

    public abstract E get(long sequence);

    public final int size() {
        return size;
    }

    public final int distance() {
        return sequencer.distance();
    }

    public final Sequencer getSequencer() {
        return sequencer;
    }

    public final  <A> void publishEvent(EventTranslator.EventTranslatorOneArg<E, A> translator, A arg) {
        long next = sequencer.next();
        translator.translateTo(get(next), arg);
        sequencer.publish(next);
    }

    public final <A> void publishEvents(EventTranslator.EventTranslatorOneArg<E, A> translator, A[] args) {
        int batchSize = args.length;
        long high = sequencer.next(batchSize);
        long low = high - (batchSize - 1);
        long sequence = low;

        for (int i = 0; i < batchSize; i++) {
            translator.translateTo(get(sequence++), args[i]);
        }
        sequencer.publish(low, high);
    }

    public final <A, B> void publishEvent(EventTranslator.EventTranslatorTwoArg<E, A, B> translator, A arg0, B arg1) {
        long next = sequencer.next();
        translator.translateTo(get(next), arg0, arg1);
        sequencer.publish(next);
    }

    public final <A, B> void publishEvents(EventTranslator.EventTranslatorTwoArg<E, A, B> translator, A[] arg0, B[] arg1) {
        int batchSize = arg0.length;
        long high = sequencer.next(batchSize);
        long low = high - (batchSize - 1);
        long sequence = low;

        for (int i = 0; i < batchSize; i++) {
            translator.translateTo(get(sequence++), arg0[i], arg1[i]);
        }
        sequencer.publish(low, high);
    }

    public final <A, B, C> void publishEvent(EventTranslator.EventTranslatorThreeArg<E, A, B, C> translator, A arg0, B arg1, C arg2) {
        long next = sequencer.next();
        translator.translateTo(get(next), arg0, arg1, arg2);
        sequencer.publish(next);
    }

    public final <A, B, C> void publishEvents(EventTranslator.EventTranslatorThreeArg<E, A, B, C> translator, A[] arg0, B[] arg1, C[] arg2) {
        int batchSize = arg0.length;
        long high = sequencer.next(batchSize);
        long low = high - (batchSize - 1);
        long sequence = low;

        for (int i = 0; i < batchSize; i++) {
            translator.translateTo(get(sequence++), arg0[i], arg1[i], arg2[i]);
        }
        sequencer.publish(low, high);
    }

    public final <A, B, C, D> void publishEvent(EventTranslator.EventTranslatorFourArg<E, A, B, C, D> translator, A arg0, B arg1, C arg2, D arg3) {
        long next = sequencer.next();
        translator.translateTo(get(next), arg0, arg1, arg2, arg3);
        sequencer.publish(next);
    }

    public final <A, B, C, D> void publishEvents(EventTranslator.EventTranslatorFourArg<E, A, B, C, D> translator, A[] arg0, B[] arg1, C[] arg2, D[] arg3) {
        int batchSize = arg0.length;
        long high = sequencer.next(batchSize);
        long low = high - (batchSize - 1);
        long sequence = low;

        for (int i = 0; i < batchSize; i++) {
            translator.translateTo(get(sequence++), arg0[i], arg1[i], arg2[i], arg3[i]);
        }
        sequencer.publish(low, high);
    }

}
