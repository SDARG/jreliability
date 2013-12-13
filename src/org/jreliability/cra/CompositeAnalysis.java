package org.jreliability.cra;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

public class CompositeAnalysis {

	private final DirectedSparseGraph<Node, Dependency> graph = new DirectedSparseGraph<Node, Dependency>();
	private final Map<String, Node> targets = new HashMap<String, Node>();

	public void connect(Node source, Node... target) {
		Node last = source;
		for (Node current : target) {
			connectboth(last, current);
			last = current;
		}
	}

	private void connectboth(Node source, Node target) {
		if (!graph.containsVertex(source)) {
			graph.addVertex(source);
		}
		if (!graph.containsVertex(target)) {
			graph.addVertex(target);
		}

		graph.addEdge(new Dependency(), source, target);
	}

	public void setTargetID(Node node, String id) {
		assert !targets.containsKey(id) : "id not unique";
		Endnode endnode = new Endnode(id);
		connect(node, endnode);
		targets.put(id, endnode);
	}

	public <A extends Object> A get(String id2) {
		view();
		return analyze(targets.get(id2));
	}

	private void view() {
		Layout<Node, Dependency> layout = new TreeLayout<Node, Dependency>(new DelegateForest<Node, Dependency>(graph));
		BasicVisualizationServer<Node, Dependency> vv = new BasicVisualizationServer<Node, Dependency>(layout);
		vv.setPreferredSize(new Dimension(350, 350));
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		Transformer<Node, Paint> vertexPaint = new Transformer<Node, Paint>() {
			@Override
			public Paint transform(Node i) {
				if (i instanceof Adapter) {
					return Color.BLACK;
				} else if (i instanceof DecompositionNode) {
					return Color.BLUE;
				} else if (i instanceof CompositionalReliabilityNode) {
					return Color.GRAY;
				} else if (i instanceof Provider) {
					return Color.GREEN;
				}
				return Color.CYAN;
			}
		};
		vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
		JFrame frame = new JFrame("Simple Graph View");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(vv);
		frame.pack();
		frame.setVisible(true);
	}

	@SuppressWarnings("unchecked")
	private <A extends Object> A analyze(Node node) {
		System.out.println("@ " + node.getClass());
		if (node instanceof Provider) {
			return ((Provider<A>) node).get();
		} else if (node instanceof Adapter) {
			Adapter<Object, A> adapter = (Adapter<Object, A>) node;
			assert graph.getPredecessorCount(adapter) == 1;
			Collection<Node> predecessors = graph.getPredecessors(adapter);
			System.out.println("predecessors of adapter: " + predecessors);
			Node predecessor = predecessors.iterator().next();
			Object input = analyze(predecessor);
			System.out.println("result: " + input);
			System.out.println("Transforming " + input.getClass() + " with " + adapter.getClass());
			return adapter.transform(input);
		} else if (node instanceof CompositionalReliabilityNode) {
			CompositionalReliabilityNode<A> compositionalReliabilityNode = (CompositionalReliabilityNode<A>) node;
			for (Node predecessor : graph.getPredecessors(compositionalReliabilityNode)) {
				Object input = analyze(predecessor);
				System.out.println("result: " + input);
				System.out.println("Adding input " + input.getClass());
				compositionalReliabilityNode.set(input);
			}
			System.out.println("Starting CRA " + compositionalReliabilityNode.getClass());
			return compositionalReliabilityNode.get();
		}
		throw new IllegalStateException();
	}

	/**
	 * @param node
	 * @param predecessor
	 * @return
	 */
	private <A> A analyze(Node node, Node predecessor) {
		Dependency<A> dependency = graph.findEdge(predecessor, node);
		if (dependency.getModel() == null) {
			dependency.setModel((A) analyze(predecessor));
		}
		return dependency.getModel();
	}

	private static class Endnode implements Adapter<Object, Object> {
		private final String id;

		public Endnode(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}

		@Override
		public Object transform(Object i) {
			return i;
		}

		@Override
		public String toString() {
			return id;
		}
	}
}
