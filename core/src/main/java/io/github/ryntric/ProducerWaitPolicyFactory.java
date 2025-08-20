package io.github.ryntric;

/**
 * author: vbondarchuk
 * date: 8/20/25
 * time: 10:21 AM
 **/

public final class ProducerWaitPolicyFactory {
    private ProducerWaitPolicyFactory() {}

    public static ProducerWaitPolicy create(ProducerWaitPolicyType type) {
        ProducerWaitPolicy producerWaitPolicy = null;
        switch (type) {
            case PARKING:
                producerWaitPolicy = new ProducerParkingPolicy();
                break;
            case SPINNING:
                producerWaitPolicy = new ProducerSpinningPolicy();
                break;
            case YIELDING:
                producerWaitPolicy = new ProducerYieldingPolicy();
                break;
        }
        return producerWaitPolicy;
    }
}
