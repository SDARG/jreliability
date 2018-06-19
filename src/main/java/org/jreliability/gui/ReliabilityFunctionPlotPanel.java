/*******************************************************************************
 * JReliability is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * JReliability is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JReliability. If not, see http://www.gnu.org/licenses/.
 *******************************************************************************/

package org.jreliability.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.jreliability.common.Samples;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.gui.aspect.Aspect;
import org.jreliability.gui.aspect.SampleCollector;

import ptolemy.plot.Plot;

/**
 * The {@link ReliabilityFunctionPlotPanel} is a basic GUI to visualize the
 * reliability {@link Aspect}s for given {@link ReliabilityFunction}s.
 * 
 * @author glass
 * 
 */
public class ReliabilityFunctionPlotPanel extends JPanel {

	/**
	 * Standard serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The used Ptolemy {@link Plot}.
	 */
	protected Plot plot;

	/**
	 * The used sampleCollector to determine the {@link Samples} of a
	 * {@link ReliabilityFunction} under a given {@link Aspect}.
	 */
	protected SampleCollector sampleCollector;

	/**
	 * The list of samplers that can be chosen.
	 */
	protected final List<Aspect> aspects;

	/**
	 * The used aspect picker.
	 */
	protected AspectPicker picker;

	/**
	 * The panel.
	 */
	protected JPanel panel = new JPanel();

	/**
	 * The list of reliabilityFunctions that shall be plotted.
	 */
	protected Map<String, ReliabilityFunction> reliabilityFunctions;

	/**
	 * The names of the reliabilityFunctions in a distinct order.
	 */
	protected List<String> names;

	/**
	 * The map keeps track of the aspect and its index in the picker.
	 */
	protected final Map<Aspect, Integer> indices = new HashMap<>();

	/**
	 * The {@link AspectPicker} is used to choose between the different
	 * {@link Aspect}s.
	 * 
	 * @author lukasiewycz, glass
	 * 
	 */
	protected class AspectPicker extends JToolBar implements ActionListener {

		private static final long serialVersionUID = 1L;

		/**
		 * The different {@link Aspect}s.
		 */
		protected final List<Aspect> aspects;
		/**
		 * The box for the different {@link Aspect}s.
		 */
		protected JComboBox<String> comboBox = new JComboBox<>();
		/**
		 * The currently shown {@link Aspect}.
		 */
		protected Aspect currentAspect;
		/**
		 * The used {@link ReliabilityFunctionPlotPanel}.
		 */
		protected ReliabilityFunctionPlotPanel panel;

		/**
		 * Constructs an {@link AspectPicker} with a given {@link JPanel} and
		 * the {@link Aspect}s.
		 * 
		 * @param panel
		 *            the panel
		 * @param aspects
		 *            the aspects
		 */
		public AspectPicker(ReliabilityFunctionPlotPanel panel, List<Aspect> aspects) {
			super();
			this.panel = panel;
			this.aspects = aspects;

			List<String> strings = new ArrayList<>();

			int i = 0;
			for (Aspect aspect : aspects) {
				String s = aspect.getName();
				strings.add(s);
				indices.put(aspect, i);
				i++;
			}

			String[] e = new String[strings.size()];
			final String[] elements = strings.toArray(e);
			comboBox = new JComboBox<>(elements);

			comboBox.addActionListener(AspectPicker.this);
			comboBox.setMaximumSize(comboBox.getPreferredSize());

			add(new JLabel("Reliability Aspect: "));
			add(comboBox);
			setFloatable(false);

			initSelection();
		}

		/**
		 * Initializes the picker with a selected aspect.
		 */
		private void initSelection() {
			Aspect aspect = aspects.get(0);
			comboBox.setSelectedIndex(indices.get(aspect));
			set(aspect);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent )
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			boolean changed = false;
			Aspect aspect = aspects.get(comboBox.getSelectedIndex());
			if (currentAspect != aspect) {
				currentAspect = aspect;
				changed = true;
			}

			if (changed) {
				panel.paint(currentAspect);
			}
		}

		/**
		 * Returns the current {@link Aspect}.
		 * 
		 * @return the current aspect
		 */
		public Aspect get() {
			return currentAspect;
		}

		/**
		 * Sets the current {@link Aspect}.
		 * 
		 * @param aspect
		 *            the aspect to be set
		 */
		public void set(Aspect aspect) {
			this.currentAspect = aspect;
		}

	}

	/**
	 * Constructs a {@link ReliabilityFunctionPlotPanel} with the given
	 * {@link Aspect}s.
	 * 
	 * @param aspects
	 *            the aspects
	 */
	public ReliabilityFunctionPlotPanel(List<Aspect> aspects) {
		this.aspects = aspects;
	}

	/**
	 * Returns the {@link JPanel} for a given set of
	 * {@link ReliabilityFunction}s.
	 * 
	 * @param reliabilityFunctions
	 *            the reliabilityFunctions
	 * @return the panel
	 */
	protected JPanel get(Map<String, ReliabilityFunction> reliabilityFunctions) {
		this.reliabilityFunctions = reliabilityFunctions;
		names = new ArrayList<>(reliabilityFunctions.keySet());
		Collections.sort(names);

		plot = new Plot();
		sampleCollector = new SampleCollector();

		picker = new AspectPicker(ReliabilityFunctionPlotPanel.this, aspects);
		Aspect currentAspect = picker.get();

		Color[] colors = { Color.BLACK, Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.ORANGE };
		plot.setColors(colors);

		int i = 0;
		for (String name : names) {
			plot.addLegend(i, name);
			i++;
		}

		panel.setLayout(new BorderLayout());
		panel.add(picker, BorderLayout.NORTH);
		panel.add(plot, BorderLayout.CENTER);

		setLabels(currentAspect.getXAxis(), currentAspect.getYAxis());
		paint(currentAspect);

		panel.setPreferredSize(new Dimension(600, 300));

		panel.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("ReliabilityFunction Plots"),
						BorderFactory.createEmptyBorder(5, 0, 0, 0)));

		panel.revalidate();
		panel.repaint();

		return panel;
	}

	/**
	 * Sets the labels for the axes.
	 * 
	 * @param xLabel
	 *            the label for the x-axis
	 * @param yLabel
	 *            the label for the y-axis
	 */
	protected void setLabels(final String xLabel, final String yLabel) {
		plot.setXLabel(xLabel);
		plot.setYLabel(yLabel);
	}

	/**
	 * Repaints the diagram under a given {@link Aspect}.
	 * 
	 * @param aspect
	 *            the aspect
	 */
	protected void paint(Aspect aspect) {
		plot.clear(false);
		double min = 0.0;
		double max = 0.0;
		int i = 0;
		setLabels(aspect.getXAxis(), aspect.getYAxis());
		Map<String, Samples> samples = sampleCollector.getSamples(reliabilityFunctions, aspect, 500);

		for (String name : names) {
			Samples sample = samples.get(name);
			for (Entry<Double, Double> value : sample.entrySet()) {
				double x = value.getKey();
				double y = value.getValue();
				if (y > max) {
					max = y;
				}
				plot.addPoint(i, x, y, true);
			}
			i++;
		}
		max = max + (0.1 * max);
		setLabels(aspect.getXAxis(), aspect.getYAxis());
		plot.setYRange(min, max);
		plot.revalidate();
		plot.repaint();
	}
}
