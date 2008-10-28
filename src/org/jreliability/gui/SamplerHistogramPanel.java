/**
 * JReliability is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */
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

import org.jreliability.function.ReliabilityFunction;
import org.jreliability.gui.sampler.Sampler;

import ptolemy.plot.Histogram;

/**
 * The {@code SamplerHistogramPanel} is used to visualize the output of all
 * kinds of {@code Evaluators} whose results can be interpreted using {@code
 * Histograms}.
 * 
 * @author glass
 * 
 */
public class SamplerHistogramPanel extends JPanel {

	/**
	 * Standard serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The used {@code Ptolemy} plot.
	 */
	protected Histogram plot;
	/**
	 * The list of samplers that can be chosen.
	 */
	protected final List<Sampler> samplers;
	/**
	 * The used sampler picker.
	 */
	protected SamplerPicker picker;
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
	 * The map keeps track of the sampler and its index in the picker.
	 */
	protected final Map<Sampler, Integer> indices = new HashMap<Sampler, Integer>();

	/**
	 * The {@code SamplerPicker} is used to choose between the different {@code
	 * Samplers}.
	 * 
	 * @author lukasiewycz, glass
	 * 
	 */
	protected class SamplerPicker extends JToolBar implements ActionListener {

		/**
		 * The standard serialVersionID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * The different {@code Samplers}.
		 */
		protected final List<Sampler> samplers;
		/**
		 * The box for the different {@code Samplers}.
		 */
		protected JComboBox comboBox = new JComboBox();
		/**
		 * The currently shown {@code Sampler}.
		 */
		protected Sampler currentSampler;
		/**
		 * The used {@code SamplerHistogramPanel}.
		 */
		protected SamplerHistogramPanel panel;

		/**
		 * Constructs an {@code SamplerPicker} with a given {@code JPanel} and
		 * the {@code Samplers}.
		 * 
		 * @param panel
		 *            the panel
		 * @param samplers
		 *            the samplers
		 */
		public SamplerPicker(SamplerHistogramPanel panel, List<Sampler> samplers) {
			super();
			this.panel = panel;
			this.samplers = samplers;

			List<String> strings = new ArrayList<String>();

			int i = 0;
			for (Sampler sampler : samplers) {
				String s = sampler.getName();
				strings.add(s);
				indices.put(sampler, i);
				i++;
			}

			String[] e = new String[strings.size()];
			final String[] elements = strings.toArray(e);
			comboBox = new JComboBox(elements);

			comboBox.addActionListener(SamplerPicker.this);
			comboBox.setMaximumSize(comboBox.getPreferredSize());

			add(new JLabel("Sampler: "));
			add(comboBox);
			setFloatable(false);

			initSelection();
		}

		/**
		 * Initializes the picker with a selected sampler.
		 */
		private void initSelection() {
			Sampler sampler = samplers.get(0);
			comboBox.setSelectedIndex(indices.get(sampler));
			set(sampler);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		public void actionPerformed(ActionEvent arg0) {
			boolean changed = false;
			Sampler sampler = samplers.get(comboBox.getSelectedIndex());
			if (currentSampler != sampler) {
				currentSampler = sampler;
				changed = true;
			}

			if (changed) {
				panel.paint(currentSampler);
			}
		}

		/**
		 * Returns the current {@code Sampler}.
		 * 
		 * @return the current sampler
		 */
		public Sampler get() {
			return currentSampler;
		}

		/**
		 * Sets the current {@code Sampler}.
		 * 
		 * @param sampler
		 *            the sampler to be set
		 */
		public void set(Sampler sampler) {
			this.currentSampler = sampler;
		}

	}

	/**
	 * Constructs a {@code SamplerHistogramPanel} with the given {@code
	 * Samplers}.
	 * 
	 * @param samplers
	 *            the samplers
	 */
	public SamplerHistogramPanel(List<Sampler> samplers) {
		this.samplers = samplers;
	}

	/**
	 * Returns the {@code JPanel} for a given set of {@code
	 * ReliabilityFunctions}.
	 * 
	 * @param reliabilityFunctions
	 *            the reliabilityFunctions
	 * @return the panel
	 */
	protected JPanel get(Map<String, ReliabilityFunction> reliabilityFunctions) {
		this.reliabilityFunctions = reliabilityFunctions;
		names = new ArrayList<String>(reliabilityFunctions.keySet());
		Collections.sort(names);

		plot = new Histogram();

		picker = new SamplerPicker(SamplerHistogramPanel.this, samplers);
		Sampler currentSampler = picker.get();

		Color[] colors = { Color.BLACK, Color.RED, Color.BLUE, Color.YELLOW,
				Color.GREEN, Color.ORANGE };
		plot.setColors(colors);

		int i = 0;
		for (String name : names) {
			plot.addLegend(i, name);
			i++;
		}

		panel.setLayout(new BorderLayout());
		panel.add(picker, BorderLayout.NORTH);
		panel.add(plot, BorderLayout.CENTER);

		setLabels(currentSampler.getXAxis(), currentSampler.getYAxis());
		paint(currentSampler);

		panel.setPreferredSize(new Dimension(600, 300));

		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder("Sampler Plots"), BorderFactory
				.createEmptyBorder(5, 0, 0, 0)));

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
	 * Repaints the diagram with a given {@code Sampler}.
	 * 
	 * @param sampler
	 *            the aspect
	 */
	protected void paint(Sampler sampler) {
		plot.clear(false);
		double min = 0.0;
		double max = 0.0;
		int i = 0;
		setLabels(sampler.getXAxis(), sampler.getYAxis());
		Map<String, List<Double>> samples = new HashMap<String, List<Double>>();
		for (Entry<String, ReliabilityFunction> entry : reliabilityFunctions
				.entrySet()) {
			String name = entry.getKey();
			ReliabilityFunction reliabilityFunction = entry.getValue();
			List<Double> functionSamples = sampler
					.getSamples(reliabilityFunction);
			samples.put(name, functionSamples);
		}

		for (String name : names) {
			List<Double> sample = samples.get(name);
			if (sample == null) {
				continue;
			}
			for (Double value : sample) {
				if (value > max) {
					max = value;
				}
				plot.addPoint(i, value, value, true);
			}
			i++;
		}
		setLabels(sampler.getXAxis(), sampler.getYAxis());
		plot.setBinWidth((max - min) / 50);
		plot.revalidate();
		plot.repaint();
	}
}
