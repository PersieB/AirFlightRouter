import java.util.LinkedList;
//Adjacent list represents a graph as an array of linked lists

public class Adjacent_List {
    // number of vertices
    int vertices;
    LinkedList<Integer> my_array[];
    //constructor
    public Adjacent_List(int vertices){
        this.vertices = vertices;
        //the size of the array takes the number of vertices
        my_array = new LinkedList[vertices];

        //creating  a linked list for each vertex so that they can store adjacent nodes.
        for(int i = 0; i < vertices; i++){
            my_array[i] = new LinkedList<>();
        }
    }
    //method to add the edges to the graph
    public  void Graph_edges(int source_code, int destination_code){
        my_array[source_code].add(destination_code);
        my_array[destination_code].add(source_code); // a destination can also be a source for another journey
    }
    //method to print the adjacent list of the graph
    public void Adj_List_Graph(){
        for(int i = 0; i < vertices; i++){
            //checking if there are adjacent nodes linked to a vertex
            if(my_array[i].size()>0){
                System.out.println("Vertex " + i + " is linked to: ");
                for(int j = 0; j < my_array[i].size(); j++){
                    System.out.print(my_array[i].get(j) + "->");
                }
                System.out.println();
            }
        }
    }

}
