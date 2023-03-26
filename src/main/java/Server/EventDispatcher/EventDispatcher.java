package Server.EventDispatcher;

import java.util.ArrayList;
import java.util.HashMap;

public class EventDispatcher {
    private static final HashMap<Byte, HashMap<Byte, ArrayList<SocketMessageEvent>>> _events = new HashMap<>();

    private static void createDefaultEvents(byte head, byte subHead) {
        if (!_events.containsKey(head)) _events.put(head, new HashMap<>());
        if (!_events.get(head).containsKey(subHead)) _events.get(head).put(subHead, new ArrayList<>());
    }

    private static ArrayList<SocketMessageEvent> getEvents(byte head, byte subHead) {
        createDefaultEvents(head, subHead);
        return _events.get(head).get(subHead);
    }

    public static void startListening(byte head, byte subHead, SocketMessageEvent event) {
        getEvents(head, subHead).add(event);
    }

    public static void stopListening(byte head, byte subHead, SocketMessageEvent event) {
        getEvents(head, subHead).remove(event);
    }

    public static void emitEvent(SocketMessage data) {
        emitEvent(data.msg.head, data.msg.subHead, data);
    }

    public static void emitEvent(byte head, byte subHead, SocketMessage data) {
        ArrayList<SocketMessageEvent> events = getEvents(head, subHead);

        for (SocketMessageEvent event : events) {
            event.execute(data);
        }
    }
}
