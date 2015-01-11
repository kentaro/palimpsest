package org.kentarok.android.palimpsest.utils;

import com.squareup.otto.Bus;

public final class BusHolder {
    public static final Bus bus = new Bus();

    public static Bus getInstance() {
        return bus;
    }
}
