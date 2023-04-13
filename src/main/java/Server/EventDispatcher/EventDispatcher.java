package Server.EventDispatcher;

import Server.ServerInstance.Sender;

import java.util.ArrayList;
import java.util.HashMap;

public class EventDispatcher {
    private static final HashMap<Byte, HashMap<Byte, ArrayList<SocketMessageEvent>>> socketEvents = new HashMap<>();
    private static final HashMap<String, ArrayList<ExecutableData<Object>>> events = new HashMap<>();

    private static void createDefaultEvents(byte head, byte subHead) {
        if (!socketEvents.containsKey(head)) socketEvents.put(head, new HashMap<>());
        if (!socketEvents.get(head).containsKey(subHead)) socketEvents.get(head).put(subHead, new ArrayList<>());
    }

    private static void createDefaultEvents(String event) {
        if (!events.containsKey(event)) events.put(event, new ArrayList<>());
    }

    private static ArrayList<SocketMessageEvent> getEvents(byte head, byte subHead) {
        createDefaultEvents(head, subHead);
        return socketEvents.get(head).get(subHead);
    }

    private static ArrayList<ExecutableData<Object>> getEvents(String event) {
        createDefaultEvents(event);
        return events.get(event);
    }

    public static void startListening(SocketMessageEvent event) {
        var head = event.getHeadByte();
        var subHead = event.getSubHeadByte();
        getEvents(head, subHead).add(event);
    }

    public static void startListening(String event, ExecutableData<Object> data) {
        getEvents(event).add(data);
    }

    public static void stopListening(byte head, byte subHead, SocketMessageEvent event) {
        getEvents(head, subHead).remove(event);
    }

    public static void stopListening(String event, ExecutableData<Object> data) {
        getEvents(event).remove(data);
    }

    public static void emitEvent(Sender server, SocketMessage data) {
        emitEvent(server, data.msg.head, data.msg.subHead, data);
    }

    public static void emitEvent(String event, Object data) {
        ArrayList<ExecutableData<Object>> events = getEvents(event);

        for (ExecutableData<Object> e : events) {
            e.execute(data);
        }
    }

    public static void emitEvent(Sender server, byte head, byte subHead, SocketMessage data) {
        ArrayList<SocketMessageEvent> events = getEvents(head, subHead);

        for (SocketMessageEvent event : events) {
            event.execute(server, data);
        }
    }
}
