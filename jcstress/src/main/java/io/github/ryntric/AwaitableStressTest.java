package io.github.ryntric;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Mode;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.Signal;
import org.openjdk.jcstress.annotations.State;

/**
 * author: ryntric
 * date: 8/24/25
 * time: 9:39 PM
 **/

@State
@JCStressTest(Mode.Termination)
@Outcome(id = "TERMINATED", expect = Expect.ACCEPTABLE)
public class AwaitableStressTest {
    private final Awaitable<Object> awaitable = new Awaitable<>();

    @Actor
    public void provider() {
        awaitable.resolve("resolved");
    }

    @Signal
    public void receiver() {
        awaitable.join();
    }

}
