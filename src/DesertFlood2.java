import java.io.*;
import java.util.*;

public class DesertFlood2 {
	
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
		

	public static void main(String[] args) throws IOException {
	
		// Creating an instance of the Graph implementation and initializing it
		HashMap<String, PrivateLinkedList> graphHashMap = new HashMap<String, PrivateLinkedList>();
		List<String[]> temp = readCSV("airports.csv");
				
		// Creating the needed hashmaps
		HashMap<String, Airports> airportHashmap = new HashMap<String, Airports>();
		HashMap<RouteID, RouteList> routeHashmap = new HashMap<RouteID, RouteList>();
		
		
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
		
		// Creating airport objects from the information about the airports and then adding them to a Hashmap indexed by airport ID to optimize several processes
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
		}
		
		// Adding the elements to the graph
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
			// double distance = airportDistance(sourceAirport, destAirport);
			// PrivateLinkedList.Node newNode = new PrivateLinkedList.Node(destAirport, distance);
			// airportGraph.Basegraph[intID].addAirportNode(newNode);
			
		}
		Set<String> graphKeys = graphHashMap.keySet();
		for(String c:graphKeys) {
			System.out.println("Airport ID: " + c + " has a total of " + graphHashMap.get(c).lengthList() + " flights");
		}
		
	
	}

}
