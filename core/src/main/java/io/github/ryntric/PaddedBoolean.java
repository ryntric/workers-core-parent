package io.github.ryntric;

import io.github.ryntric.util.Util;

import java.lang.invoke.VarHandle;

/**
 * author: ryntric
 * date: 8/25/25
 * time: 6:11 PM
 **/

abstract class LeftBooleanPaddings {
    protected byte
            p10, p11, p12, p13, p14, p15, p16,
            p17, p20, p21, p22, p23, p24, p25,
            p26, p27, p30, p31, p32, p33, p34,
            p35, p36, p37, p40, p41, p42, p43,
            p44, p45, p46, p47, p50, p51, p52,
            p53, p54, p55, p56, p57, p60, p61,
            p62, p63, p64, p65, p66, p67, p70,
            p71, p72, p73, p74, p75, p76, p77,
            p80, p81, p82, p83, p84, p85, p86;
}

abstract class PaddedBooleanValue extends LeftBooleanPaddings {
    protected boolean value;
}

abstract class RightBooleanPaddings extends PaddedBooleanValue {
    protected byte
            p10, p11, p12, p13, p14, p15, p16,
            p17, p20, p21, p22, p23, p24, p25,
            p26, p27, p30, p31, p32, p33, p34,
            p35, p36, p37, p40, p41, p42, p43,
            p44, p45, p46, p47, p50, p51, p52,
            p53, p54, p55, p56, p57, p60, p61,
            p62, p63, p64, p65, p66, p67, p70,
            p71, p72, p73, p74, p75, p76, p77,
            p80, p81, p82, p83, p84, p85, p86;
}

public final class PaddedBoolean extends RightBooleanPaddings {
    private static final VarHandle VALUE_VH = Util.findVarHandlePrivate(PaddedBoolean.class, "value", boolean.class);

    public void setPlain(boolean value) {
        this.value = value;
    }

    public boolean getPlain() {
        return value;
    }

    public void setRelease(boolean value) {
        this.value = value;
        VarHandle.releaseFence();
    }

    public boolean getAcquire() {
        VarHandle.acquireFence();
        return value;
    }

    public boolean compareAndSetVolatile(boolean expect, boolean value) {
        return VALUE_VH.compareAndSet(this, expect, value);
    }

}


