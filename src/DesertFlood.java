import java.io.*;
import java.util.*;
import org.jgrapht.*;


public class DesertFlood {
	
	// This method will permit us to read a CSV file, and returns a list of string arrays, with the data in the various columns as members
	public static List<String[]> readCSV(String csvName) throws IOException {
		List<String[]> finalFile = new LinkedList<String[]>();
        File file = new File(csvName);
        FileReader reader = new FileReader(file);
        BufferedReader buffer = new BufferedReader(reader);
        String line = "";
        String[] tempArr;
        while((line = buffer.readLine()) != null) {
           tempArr = line.split(",");
           finalFile.add(tempArr);
        }
        buffer.close();
        return finalFile;	
	}

	// This method takes in two airport objects, and calculates the distance between them using the Haversine formula
	// This should serve as the weight of the edges between this two graphs
	public static double airportDistance(Airports A, Airports B) {
		double latA = A.getLatitude();
		double longA = A.getLongitude();
		double latB = B.getLatitude();
		double longB = B.getLongitude();
		final int R = 6371; // Radius of the earth
		double latDistance = Math.toRadians(latB - latA);
		double longDistance = Math.toRadians(longB - longA);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(latA)) * Math.cos(Math.toRadians(latB))
	            * Math.sin(longDistance / 2) * Math.sin(longDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters
		double height = 0; // Making the assumption that the difference in altitude between the two airports is 0
		distance = Math.pow(distance, 2) + Math.pow(height, 2);
	    return Math.sqrt(distance);
	}
	
	// This method will be used to find the highest connected vertex
	public static String getMaxConnected(HashMap<String, PrivateLinkedList> hashMap) {
		Set<String> graphKeys = hashMap.keySet();
		int h = 0;
		String current = "";
		for(String c:graphKeys) {
			h = Math.max(h, hashMap.get(c).lengthList());
			if(Math.max(h, hashMap.get(c).lengthList()) == hashMap.get(c).lengthList()) {
				current = c;
			}
		}
		return current;
	}
	
	public static void printPaths(int id1, int id2, HashMap<String, Airports> airportHashmap, GraphImplementation airportGraph) {
		Airports H = airportHashmap.get("" + id1);
		Airports G = airportHashmap.get("" + id2);
		System.out.println(H.getName() + ", in " + H.getCity() + ": " + H.getCountry() + " to " + " ************** " + G.getName() + ", in " + G.getCity() + ": " + G.getCountry());
		GraphPath<Airports, Route> path2 = airportGraph.privateDijkstra(H,G);
		List<Route> pathRoutes2 = path2.getEdgeList();
		for(Route f:pathRoutes2) {
			System.out.println(f.getAirlineID() + " : " + f.getAirlineCode() + ". Flying from " + f.getSourceAirportCode()
								+ ": " + f.getSourceAirportID() + " TO " + f.getDestinationAirportCode() + " : " + f.getDestinationAirportID()
					);
		}
		
	}
	
	
	
	public static void main(String[] args) throws IOException {
		
		// Creating an instance of the Graph implementation
		GraphImplementation airportGraph = new GraphImplementation();
		List<String[]> temp = readCSV("airports.csv");
		
		// Creating the needed hashmaps
		HashMap<String, Airports> airportHashmap = new HashMap<String, Airports>();
		HashMap<RouteID, RouteList> routeHashmap = new HashMap<RouteID, RouteList>();
		
		// Creating airport objects from the information about the airports and then adding them as vertices in the graph.
		// The airport objects are also added to a Hashmap indexed by airport ID to optimize several processes
		for(String[] c:temp) {
			Double h;
			// The following trial and catching is neccesitated by errors in the data. Remark that giving all missing data the 
			// Default value of 0 has no effects on the meaning of the data.
			try {
				h = Double.parseDouble(c[9]);
			}
			catch (NumberFormatException e) {
				h = 0.0;
			}
			Airports A = new Airports(c[0], c[1], c[2], c[3], c[4], c[5], Double.parseDouble(c[6]), Double.parseDouble(c[7]), 
					Double.parseDouble(c[8]), h , c[9]);
			airportHashmap.put(c[0], A);
			airportGraph.BaseGraph.addVertex(A);
		}
		
		// Reading the routes and storing them in a HashMap appropriately 
		List<String[]> routesTemp = readCSV("routes.csv");
		for(String[] c:routesTemp) {
			RouteID routeID = new RouteID(c[3], c[5]);
			String d;
			// The following trial and catching is neccesitated by errors in the data. Remark that giving all missing data the 
			// Default value of 0 has no effects on the meaning of the data.
			try {
				d = c[8];
			}
			catch (ArrayIndexOutOfBoundsException e) {
				d = "\\N";
			}
			Route newRoute = new Route(c[0], c[1], c[2], c[3], c[4], c[5], c[6], c[7], d);
			RouteList routeListTemp = routeHashmap.get(routeID);
			if(routeListTemp != null) {
				routeListTemp.addRoute(newRoute);
				routeHashmap.put(routeID, routeListTemp);
			}
			else {
				RouteList updatedRouteList = new RouteList();
				updatedRouteList.addRoute(newRoute);
				routeHashmap.put(routeID, updatedRouteList);
			}
		}
		
		// Getting all the elements for each key in the route Hashmap and then adding them as edges to the graph
		Set<RouteID> allRouteKeys = routeHashmap.keySet();
		for(RouteID c:allRouteKeys) {
			String sourceID = c.getSourceID();
			String destID = c.getDestinationID();
			RouteList routeListForKey = routeHashmap.get(c);
			Airports sourceAirport = airportHashmap.get(sourceID);
			Airports destAirport = airportHashmap.get(destID);
			for(Route r:routeListForKey) {
				// The following trial and catching is neccesitated by errors in the data. Remark that giving all missing data the 
				// Default value of 0 has no effects on the meaning of the data.
				try {
				if((r != null) && (!sourceAirport.equals(destAirport))) {
				airportGraph.BaseGraph.addEdge(sourceAirport, destAirport, r);
				}
				} catch (NullPointerException e) {}
		
			}
		}
		
		// Adding the weights to the edges
		for(Route c:airportGraph.BaseGraph.edgeSet()) {
			String sourceID = c.getSourceAirportID();
			String destID = c.getDestinationAirportID();
			Airports sourceAirport = airportHashmap.get(sourceID);
			Airports destAirport = airportHashmap.get(destID);
			airportGraph.BaseGraph.setEdgeWeight(c, airportDistance(sourceAirport, destAirport));
			
		}
		
		
		// Building our own version of the graph using HashMaps
		// Creating an instance of the Graph implementation and initializing it
		HashMap<String, PrivateLinkedList> graphHashMap = new HashMap<String, PrivateLinkedList>();
		HashMap<String, PrivateLinkedList> graphHashMap2 = new HashMap<String, PrivateLinkedList>();
							
		// Adding the elements to the graph. This graph will help us to find the airport that has the most departing flights
		Set<RouteID> keys = routeHashmap.keySet();
		for(RouteID r:keys) {
			String sourceID = r.getSourceID();
			String destID = r.getDestinationID();
			if(sourceID.equals("\\N")) {
				continue;
			}
			Airports sourceAirport = airportHashmap.get(sourceID);
			Airports destAirport = airportHashmap.get(destID);
			try {
				double distance = airportDistance(sourceAirport, destAirport);
				PrivateLinkedList.Node newNode = new PrivateLinkedList.Node(destAirport, distance);
				PrivateLinkedList possibleFlights = graphHashMap.get(sourceID);
				if(possibleFlights == null) {
					PrivateLinkedList tempFlights = new PrivateLinkedList(newNode);
					graphHashMap.put(sourceID, tempFlights);
				}
				else {
					possibleFlights.addAirportNode(newNode);
					graphHashMap.put(sourceID,possibleFlights);
				}
			}
			catch (NullPointerException e) {
				
			}
		}
				
		// Creating another graph that helps us to get information about the airport that recieves the highest flights
		for(RouteID r:keys) {
			String sourceID = r.getSourceID();
			String destID = r.getDestinationID();
			if(sourceID.equals("\\N")) {
				continue;
			}
			Airports sourceAirport = airportHashmap.get(sourceID);
			Airports destAirport = airportHashmap.get(destID);
			try {
				double distance = airportDistance(sourceAirport, destAirport);
				PrivateLinkedList.Node newNode = new PrivateLinkedList.Node(sourceAirport, distance);
				PrivateLinkedList possibleFlights = graphHashMap2.get(destID);
				if(possibleFlights == null) {
					PrivateLinkedList tempFlights = new PrivateLinkedList(newNode);
					graphHashMap2.put(destID, tempFlights);
				}
				else {
					possibleFlights.addAirportNode(newNode);
					graphHashMap2.put(destID,possibleFlights);
				}
			}
			catch (NullPointerException e) {
				
			}
		}
		
		System.out.println("*********** DEMO FOR MOST IMPORTANT AIRPORTS **************\n" );
		// Getting the airport with the highest number of departing flights
		String highestLeaving = getMaxConnected(graphHashMap);
		System.out.println("The airport from which one can go to the highest number of airports is: " );
		System.out.println(airportHashmap.get(highestLeaving).getName() + " and has " + graphHashMap.get(highestLeaving).lengthList() + " departing\n" );
		
		// Getting the airport that recieves flights from the highest number of flights
		String highestRecieving = getMaxConnected(graphHashMap2);
		System.out.println("The airport that recieves most flights is: " );
		System.out.println(airportHashmap.get(highestRecieving).getName() + " recieves " + graphHashMap2.get(highestRecieving).lengthList() + " flights\n" );
		
		System.out.println("*********** DEMO FOR SHORTEST PATHS **************\n" );
		// Demonstrating the shortest path between Yaounde International Airport and Kotoka International Airport
		printPaths(4161, 248, airportHashmap, airportGraph );
		System.out.println("\n\n" );
		
		// Demonstrating the shortest path between Kotoka International Airport and Hartsfield Jackson Atlanta International Airport
		printPaths(248,3682, airportHashmap, airportGraph );
		System.out.println("\n\n" );
		
		
		// Demonstrating the shortest path between Ngjiva Pereira Airport (Angola) and London Heathrow Airport
		printPaths(5632,507, airportHashmap, airportGraph );
		System.out.println("\n\n" );
		
		System.out.println("\n\n\nPun Alert!! Thank you Dr Korsah, we learned a lot from this data structures project:)");
	}
	
	
}
