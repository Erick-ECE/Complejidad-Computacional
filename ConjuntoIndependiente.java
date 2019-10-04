import org.graphstream.graph.implementations.*;
import org.graphstream.graph.*;
import org.graphstream.algorithm.generator.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class ConjuntoIndependiente {
	public static ArrayList<Node> cIndependiente = new ArrayList<>();

	public static void adivina(Graph g){
		Graph newG = Graphs.clone(g);
		int nodos = newG.getNodeCount();
		Random r = new Random();
		ArrayList<Integer> candidato = new ArrayList<>();
		int k = r.nextInt((int) g.getNodeCount() / 2) +1; // nodos del conjunto independiente
		for(int i=0; i<k; i++){
			int indice = r.nextInt(nodos);
			while(candidato.contains(indice)){
				indice = r.nextInt(nodos);
			}
			candidato.add(indice);
			cIndependiente.add(newG.getNode(indice));
			newG.getNode(indice).addAttribute("ui.style", "fill-color: rgb(5,143,208);");	
		}
		

		//System.out.print("\n ver nodos " + nodos);
		System.out.print("\n conjunto independiente :" + cIndependiente.toString());

		newG.display(true);	
	}

	public static boolean verifica(Graph g, ArrayList<Node> ci){
		
		// Para las parejas ordenadas de ci, que no sean vecinos.
		for(int i = 0; i < ci.size(); i++) {
			for(int j = i+1; j < ci.size(); j++) {
				Node n1 = g.getNode(ci.get(i).getId());
				Node n2 = g.getNode(ci.get(j).getId());
				if(n1.hasEdgeToward(n2)) {
					return false;
				}
			}
		}

		// Para todo nodo en g, exista uno en ci, que sea su vecino.
		/*for(int i = 0; i < g.getNodeCount(); i++) {
			Node n = g.getNode(i);
			boolean found = false;
			for(Node ind : ci) {
				found = ind.hasEdgeToward(n);
			}
			if(!found) {
				return false;
			}
		}
		System.out.print("\n True: Es un conjunto independiente");*/
		return true;
	}
	
	public static void main(String[] args) {
	
		Random rnd = new Random();
		int vertices = rnd.nextInt(6)+5; // número de vertices de la gráfica
		int grado = rnd.nextInt(2)+2; // restringimos un grado maximo de 4 (pero este se puede dejar abierto al gusto del programador)
	
		Graph graph = new SingleGraph("Random"); // creacion de la gráfica
    	Generator gen = new RandomGenerator(grado); // grado promedio de los vertices
    	gen.addSink(graph);
    	gen.begin(); // coloca el vertice inicial
	
		for(int i=0; i<(vertices-(grado+1)); i++)
        	gen.nextEvents(); //conecta vertices de manera aleatoria
    	gen.end();

   
    	for(Node n:graph) {
			n.addAttribute("ui.style", "fill-color: rgb(255,0,0);"); // coloreamos de rojo los nodos de nuestra grafica
			n.addAttribute("ui.label", n.getId());
		}
	
		//System.out.print("vertices: " + vertices + "\n");
		//System.out.print("Grado Promedio: " + grado);
		graph.display(); // muestra la grafica original
		adivina(graph);
		boolean res = verifica(graph,cIndependiente);
		System.out.println();
		if(res)
			System.out.println("Si es conjunto independiente");
		else
			System.out.println("No es conjunto independiente");
		
	}
}
