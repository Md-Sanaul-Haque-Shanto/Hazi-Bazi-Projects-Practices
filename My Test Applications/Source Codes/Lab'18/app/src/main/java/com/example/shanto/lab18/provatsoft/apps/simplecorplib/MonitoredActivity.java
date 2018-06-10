package com.example.shanto.lab18.provatsoft.apps.simplecorplib;

import android.app.Activity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Iterator;

public class MonitoredActivity extends Activity {
    private final ArrayList<LifeCycleListener> mListeners;

    public interface LifeCycleListener {
        void onActivityCreated(MonitoredActivity monitoredActivity);

        void onActivityDestroyed(MonitoredActivity monitoredActivity);

        void onActivityPaused(MonitoredActivity monitoredActivity);

        void onActivityResumed(MonitoredActivity monitoredActivity);

        void onActivityStarted(MonitoredActivity monitoredActivity);

        void onActivityStopped(MonitoredActivity monitoredActivity);
    }

    public static class LifeCycleAdapter implements LifeCycleListener {
        public void onActivityCreated(MonitoredActivity activity) {
        }

        public void onActivityDestroyed(MonitoredActivity activity) {
        }

        public void onActivityPaused(MonitoredActivity activity) {
        }

        public void onActivityResumed(MonitoredActivity activity) {
        }

        public void onActivityStarted(MonitoredActivity activity) {
        }

        public void onActivityStopped(MonitoredActivity activity) {
        }
    }

    public MonitoredActivity() {
        this.mListeners = new ArrayList();
    }

    public void addLifeCycleListener(LifeCycleListener listener) {
        if (!this.mListeners.contains(listener)) {
            this.mListeners.add(listener);
        }
    }

    public void removeLifeCycleListener(LifeCycleListener listener) {
        this.mListeners.remove(listener);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((LifeCycleListener) it.next()).onActivityCreated(this);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((LifeCycleListener) it.next()).onActivityDestroyed(this);
        }
    }

    protected void onStart() {
        super.onStart();
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((LifeCycleListener) it.next()).onActivityStarted(this);
        }
    }

    protected void onStop() {
        super.onStop();
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((LifeCycleListener) it.next()).onActivityStopped(this);
        }
    }
}
