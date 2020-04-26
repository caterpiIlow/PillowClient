package caterpillow.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import caterpillow.event.Listeners.GUIRenderListener;
import caterpillow.event.Listeners.RenderListener;

/**
 * Created by Hexeption on 18/12/2016.
 */
public class EventManager {

	private final HashMap<Class<? extends EventListener>, ArrayList<? extends EventListener>> listenerMap = new HashMap<>();

	{
		/// listenerMap.put(ChatInputListener.class, new
		/// ArrayList<ChatInputListener>());
		// listenerMap.put(ChatOutputListener.class, new
		/// ArrayList<ChatOutputListener>());
		// listenerMap.put(DeathListener.class, new ArrayList<DeathListener>());
		listenerMap.put(GUIRenderListener.class, new ArrayList<GUIRenderListener>());
		// listenerMap.put(LeftClickListener.class, new ArrayList<LeftClickListener>());
		// listenerMap.put(RightClickListener.class, new
		// ArrayList<RightClickListener>());
		// listenerMap.put(PacketInputListener.class, new
		// ArrayList<PacketInputListener>());
		// listenerMap.put(PacketOutputListener.class, new
		// ArrayList<PacketOutputListener>());
		listenerMap.put(RenderListener.class, new ArrayList<RenderListener>());
		// listenerMap.put(UpdateListener.class, new ArrayList<UpdateListener>());
	}
	/*
	 * @SuppressWarnings("unchecked") public <T extends Event> void fire(T event) {
	 * if (!WurstClient.INSTANCE.isEnabled()) return;
	 * 
	 * try { event.fire(listenerMap.get(event.getListenerType())); } catch
	 * (Throwable e) { e.printStackTrace(); CrashReport crashReport =
	 * CrashReport.makeCrashReport(e, "Firing Wurst event"); CrashReportCategory
	 * crashreportcategory = crashReport.makeCategory("Affected event");
	 * crashreportcategory.setDetail("Event class", () -> { return
	 * event.getClass().getName(); }); throw new ReportedException(crashReport); } }
	 */
	/*
	 * @SuppressWarnings("unchecked") public <T extends EventListener> void
	 * add(Class<T> type, T listener) { try { ((ArrayList<T>)
	 * listenerMap.get(type)).add(listener); } catch (Throwable e) {
	 * e.printStackTrace(); CrashReport crashReport = CrashReport.makeCrashReport(e,
	 * "Adding Wurst event listener"); CrashReportCategory crashreportcategory =
	 * crashReport.makeCategory("Affected listener");
	 * crashreportcategory.setDetail("Listener type", () -> { return type.getName();
	 * }); crashreportcategory.setDetail("Listener class", () -> { return
	 * listener.getClass().getName(); }); throw new ReportedException(crashReport);
	 * } }
	 */
	/*
	 * @SuppressWarnings("unchecked") public <T extends EventListener> void
	 * remove(Class<T> type, T listener) { try { ((ArrayList<T>)
	 * listenerMap.get(type)).remove(listener); } catch (Throwable e) {
	 * e.printStackTrace(); CrashReport crashReport = CrashReport.makeCrashReport(e,
	 * "Removing Wurst event listener"); CrashReportCategory crashreportcategory =
	 * crashReport.makeCategory("Affected listener");
	 * crashreportcategory.setDetail("Listener type", () -> { return type.getName();
	 * }); crashreportcategory.setDetail("Listener class", () -> { return
	 * listener.getClass().getName(); }); throw new ReportedException(crashReport);
	 * } }
	 */
	private static final Map<Class<? extends Event>, ArrayHelper<Data>> REGISTRY_MAP;

	public static void register(final Object o) {

		for (final Method method : o.getClass().getDeclaredMethods()) {
			if (!isMethodBad(method)) {
				register(method, o);
			}
		}
	}

	public static void register(final Object o, final Class<? extends Event> clazz) {

		for (final Method method : o.getClass().getDeclaredMethods()) {
			if (!isMethodBad(method, clazz)) {
				register(method, o);
			}
		}
	}

	private static void register(final Method method, final Object o) {

		final Class<?> clazz = method.getParameterTypes()[0];
		final Data methodData = new Data(o, method, method.getAnnotation(EventTarget.class).value());

		if (!methodData.target.isAccessible()) {
			methodData.target.setAccessible(true);
		}

		if (EventManager.REGISTRY_MAP.containsKey(clazz)) {
			if (!EventManager.REGISTRY_MAP.get(clazz).contains(methodData)) {
				EventManager.REGISTRY_MAP.get(clazz).add(methodData);
				sortListValue((Class<? extends Event>) clazz);
			}
		} else {
			EventManager.REGISTRY_MAP.put((Class<? extends Event>) clazz, new ArrayHelper<Data>() {

				{
					this.add(methodData);
				}
			});
		}
	}

	public static void unregister(final Object o) {

		for (final ArrayHelper<Data> flexibalArray : EventManager.REGISTRY_MAP.values()) {
			for (final Data methodData : flexibalArray) {
				if (methodData.source.equals(o)) {
					flexibalArray.remove(methodData);
				}
			}
		}

		cleanMap(true);
	}

	public static void unregister(final Object o, final Class<? extends Event> clazz) {

		if (EventManager.REGISTRY_MAP.containsKey(clazz)) {
			for (final Data methodData : EventManager.REGISTRY_MAP.get(clazz)) {
				if (methodData.source.equals(o)) {
					EventManager.REGISTRY_MAP.get(clazz).remove(methodData);
				}
			}

			cleanMap(true);
		}
	}

	public static void cleanMap(final boolean b) {

		final Iterator<Map.Entry<Class<? extends Event>, ArrayHelper<Data>>> iterator = EventManager.REGISTRY_MAP
				.entrySet().iterator();

		while (iterator.hasNext()) {
			if (!b || iterator.next().getValue().isEmpty()) {
				iterator.remove();
			}
		}
	}

	public static void removeEnty(final Class<? extends Event> clazz) {

		final Iterator<Map.Entry<Class<? extends Event>, ArrayHelper<Data>>> iterator = EventManager.REGISTRY_MAP
				.entrySet().iterator();

		while (iterator.hasNext()) {
			if (iterator.next().getKey().equals(clazz)) {
				iterator.remove();
				break;
			}
		}
	}

	private static void sortListValue(final Class<? extends Event> clazz) {

		final ArrayHelper<Data> flexibleArray = new ArrayHelper<Data>();

		for (final byte b : Priority.VALUE_ARRAY) {
			for (final Data methodData : EventManager.REGISTRY_MAP.get(clazz)) {
				if (methodData.priority == b) {
					flexibleArray.add(methodData);
				}
			}
		}

		EventManager.REGISTRY_MAP.put(clazz, flexibleArray);
	}

	private static boolean isMethodBad(final Method method) {

		return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(EventTarget.class);
	}

	private static boolean isMethodBad(final Method method, final Class<? extends Event> clazz) {

		return isMethodBad(method) || method.getParameterTypes()[0].equals(clazz);
	}

	public static ArrayHelper<Data> get(final Class<? extends Event> clazz) {

		return EventManager.REGISTRY_MAP.get(clazz);
	}

	public static void shutdown() {

		EventManager.REGISTRY_MAP.clear();
	}

	static {
		REGISTRY_MAP = new HashMap<Class<? extends Event>, ArrayHelper<Data>>();
	}

}
