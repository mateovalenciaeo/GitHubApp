package com.example.github.utils;

import android.os.SystemClock;
import android.util.ArrayMap;

import java.util.concurrent.TimeUnit;

public class RateLimiter<KEY> {
    private ArrayMap<KEY, Long> timestamp = new ArrayMap<>();
    private final long timeout;

    public RateLimiter(int timeout, TimeUnit timeUnit){
        this.timeout = timeUnit.toMillis(timeout);
    }

    public synchronized boolean shouldFetch(KEY key){
        Long lastFetched = timestamp.get(key);
        long now = now();
        if(lastFetched == null){
            timestamp.put(key, now);
            return true;
        }

        if(now - lastFetched > timeout){
            timestamp.put(key, now);
            return true;
        }

        return false;
    }

    private long now(){
        return SystemClock.uptimeMillis();
    }

    public synchronized void reset(KEY key){
        timestamp.remove(key);
    }
}
