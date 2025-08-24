package io.github.ryntric;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.L_Result;

/**
 * author: ryntric
 * date: 8/24/25
 * time: 9:39 PM
 **/

@JCStressTest
@Outcome(id = "resolved", expect = Expect.ACCEPTABLE)
@Outcome(id = "null", expect = Expect.FORBIDDEN)
public class AwaitableStressTest {

    @State
    public static class AwaitableState {
        private final Awaitable<Object> awaitable = new Awaitable<>();
    }

    @Actor
    public void provider(AwaitableState state) {
        state.awaitable.resolve("resolved");
    }

    @Actor
    public void reader1(AwaitableState state, L_Result result) {
        result.r1 = state.awaitable.join();
    }

    @Actor
    public void reader2(AwaitableState state, L_Result result) {
        result.r1 = state.awaitable.join();
    }

    //    @Actor
    //    public void reader2(AwaitableState state, L_Result result) {
    //        result.r1 = state.awaitable.join();
    //    }

}
