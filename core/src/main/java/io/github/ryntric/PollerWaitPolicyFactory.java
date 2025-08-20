package io.github.ryntric;

/**
 * author: ryntric
 * date: 8/17/25
 * time: 5:49 PM
 **/

public final class PollerWaitPolicyFactory {

    public static PollerWaitPolicy create(PollerWaitPolicyType type) {
        PollerWaitPolicy policy = null;
        switch (type) {
            case BLOCKING:
                policy = new PollerBlockingPolicy();
                break;
            case PARKING:
                policy = new PollerParkingPolicy();
                break;
            case SPINNING:
                policy = new PollerSpinningPolicy();
                break;
            case YIELDING:
                policy = new PollerYieldingPolicy();
                break;
        }
        return policy;
    }
}
