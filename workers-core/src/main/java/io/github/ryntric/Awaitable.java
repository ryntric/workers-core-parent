package io.github.ryntric;

import io.github.ryntric.util.Util;

import java.lang.invoke.VarHandle;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.LockSupport;

/**
 * author: ryntric
 * date: 8/24/25
 * time: 11:02 PM
 **/

@SuppressWarnings({"FieldMayBeFinal"})
public final class Awaitable<R> {
    private static final VarHandle STATE_VH = Util.findVarHandlePrivate(Awaitable.class, "state", byte.class);

    private static final byte UNRESOLVED = 0;
    private static final byte RESOLVED = 1;
    private static final byte CANCELLED = 2;

    private byte state;

    private R result;
    private Throwable exception;

    private void await() {
        while (!isDone()) {
            LockSupport.parkNanos(1L);
        }
    }

    public void resolve(R value) {
        result = value;
        STATE_VH.weakCompareAndSetRelease(this, UNRESOLVED, RESOLVED);
    }

    public void fail(Throwable ex) {
        this.exception = ex;
        STATE_VH.weakCompareAndSetRelease(this, UNRESOLVED, RESOLVED);
    }

    public void reset() {
        this.result = null;
        this.exception = null;
        STATE_VH.setRelease(this, UNRESOLVED);
    }

    public boolean cancel() {
        return STATE_VH.weakCompareAndSetRelease(this, UNRESOLVED, CANCELLED);
    }

    public boolean isCancelled() {
        return (byte) STATE_VH.getAcquire(this) == CANCELLED;
    }

    public boolean isDone() {
        return (byte) STATE_VH.getAcquire(this) != UNRESOLVED;
    }

    public R get() throws Throwable {
        await();
        if (exception != null) throw exception;
        return result;
    }

    public R get(long timeout, TimeUnit timeUnit) throws Throwable {
        long timeoutNanos = System.nanoTime() + timeUnit.toNanos(timeout);
        while (!isDone()) {
            if (System.nanoTime() >= timeoutNanos) throw new TimeoutException();
            LockSupport.parkNanos(1L);
        }
        if (exception != null) throw exception;
        return result;
    }

    public R join() {
        await();
        if (exception != null) throw new RuntimeException(exception);
        return result;
    }

}
