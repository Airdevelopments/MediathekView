package mediathek.mac;

import mediathek.tool.threads.IndicatorThread;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * This thread will update the percentage drawn on the dock icon on OS X.
 */
class OsxIndicatorThread extends IndicatorThread {

    private boolean bFirstUpdate = true;
    private double oldPercentage;

    public OsxIndicatorThread() {
        super();
        setName("OsxIndicatorThread");
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                final double percentage = calculateOverallPercentage();

                if (bFirstUpdate) {
                    Taskbar.getTaskbar().setProgressValue(0);
                    bFirstUpdate = false;
                }

                //update in 1pct steps...
                if (percentage % 1 == 0) {
                    //if icon was already drawn, don´ do it again
                    if (oldPercentage != percentage) {
                        final int percent = (int)percentage;
                        Taskbar.getTaskbar().setProgressValue(percent);
                    }

                    oldPercentage = percentage;
                }
                TimeUnit.MILLISECONDS.sleep(500);
            }
        } catch (Exception ignored) {
        } finally {
            //reset the application dock icon
            Taskbar.getTaskbar().setProgressValue(100);
            oldPercentage = 0.0;
        }
    }
}
