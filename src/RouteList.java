import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
public class RouteList implements Iterable<Route>{
	private List<Route> routeList;
	
	public RouteList() {
		routeList = new LinkedList<Route>();
	}
	
	// Confirms that the route is not false, and then adds it to the route list.
	public boolean addRoute(Route c) {
		if(c!=null) {
		this.routeList.add(c);
		return true;
		}
		return false;
	}
	
	
	public List<Route> getRouteList() {
		return this.routeList;
	}
	
	public int length() {
		Iterator<Route> iter = this.iterator();
		int i =0;
		while(iter.hasNext()) {
			i += 1;
		}
		return i;
	}
	@Override
	public Iterator<Route> iterator() {
		return routeList.iterator();
	}
}
