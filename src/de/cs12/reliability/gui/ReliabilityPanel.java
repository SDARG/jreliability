package de.cs12.reliability.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.Map.Entry;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import ptolemy.plot.Plot;
import de.cs12.reliability.common.Samples;
import de.cs12.reliability.gui.Aspect.Aspects;

/**
 * The {@code ReliabilityPanel} is a basic GUI to visualize the reliability
 * {@code Aspects} for a given {@code BDD}.
 * 
 * @author glass
 * 
 */
public class ReliabilityPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	protected Plot plot;

	protected List<Aspect> aspects = new ArrayList<Aspect>();

	protected AspectPicker picker;

	protected JPanel panel = new JPanel();

	protected final Samples samples;

	/**
	 * The {@code AspectPicker} is used to choose between the different {@code
	 * Aspects}.
	 * 
	 * @author lukasiewycz, glass
	 * 
	 */
	protected class AspectPicker extends JToolBar implements ActionListener {

		private static final long serialVersionUID = 1L;

		protected final Map<Aspect, Integer> indices = new HashMap<Aspect, Integer>();

		protected JComboBox comboBox = new JComboBox();

		protected Aspect currentAspect;

		protected ReliabilityPanel panel;

		/**
		 * Constructs an {@code AspectPicker} with a given {@code JPanel}.
		 * 
		 * @param panel
		 *            the used panel
		 */
		public AspectPicker(ReliabilityPanel panel) {
			super();
			this.panel = panel;

			List<String> strings = new ArrayList<String>();

			int i = 0;

			for (Aspect aspect : aspects) {
				String s = aspect.getName();
				strings.add(s);
				indices.put(aspect, i);
				i++;
			}

			String[] e = new String[strings.size()];
			final String[] elements = strings.toArray(e);

			comboBox = new JComboBox(elements);

			comboBox.addActionListener(AspectPicker.this);
			comboBox.setMaximumSize(comboBox.getPreferredSize());

			add(new JLabel("Reliability Aspect: "));
			add(comboBox);
			setFloatable(false);

			initSelection();
		}

		private void initSelection() {
			Aspect aspect = aspects.get(0);
			final int index = indices.get(aspect);
			comboBox.setSelectedIndex(index);
			set(aspect);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
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
				panel.setLabels(getXLabel(), getYLabel());
				panel.paint(getValues());
			}

		}

		/**
		 * Returns the current {@code Aspect}.
		 * 
		 * @return the current aspect
		 */
		public Aspect get() {
			return currentAspect;
		}

		/**
		 * Sets the current {@code Aspect}.
		 * 
		 * @param aspect
		 *            the aspect to be set
		 */
		public void set(Aspect aspect) {
			this.currentAspect = aspect;
		}

		/**
		 * Returns the label of the {@code x-axis}.
		 * 
		 * @return the label of the code y-axis
		 */
		public String getXLabel() {
			return currentAspect.getX();
		}

		/**
		 * Returns the label of the {@code y-axis}.
		 * 
		 * @return the label of the y-axis
		 */
		public String getYLabel() {
			return currentAspect.getY();
		}

		/**
		 * Returns the values to be plotted.
		 * 
		 * @return the values to be plotted
		 */
		public SortedMap<Double, Double> getValues() {
			return currentAspect.getValues();
		}

	}

	/**
	 * Constructs a {@code ReliabilityPanel} with given {@code Samples}.
	 * 
	 * @param samples
	 *            the samples
	 */
	public ReliabilityPanel(Samples samples) {
		this.samples = samples;
		init();

	}

	/**
	 * Initializes the {@code Panel}.
	 */
	protected void init() {
		plot = new Plot();

		Aspect distribution = new Aspect(Aspects.DISTRIBUTION, samples);
		aspects.add(distribution);
		Aspect density = new Aspect(Aspects.DENSITY, samples);
		aspects.add(density);
		Aspect lambda = new Aspect(Aspects.LAMBDA, samples);
		aspects.add(lambda);

		picker = new AspectPicker(ReliabilityPanel.this);

		Color[] colors = new Color[1];
		colors[0] = Color.BLACK;
		plot.setColors(colors);

		panel.setLayout(new BorderLayout());
		panel.add(picker, BorderLayout.NORTH);
		panel.add(plot, BorderLayout.CENTER);

		setLabels(picker.getXLabel(), picker.getYLabel());
		paint(picker.getValues());

		panel.setPreferredSize(new Dimension(600, 400));

		panel.revalidate();
		panel.repaint();
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
	 * Repaints the diagram.
	 */
	protected void paint(SortedMap<Double, Double> values) {
		plot.clear(false);
		double min = 0.0;
		double max = 0.0;
		for (Entry<Double, Double> entry : values.entrySet()) {
			double x = entry.getKey();
			double y = entry.getValue();
			if (y > max) {
				max = y;
			}
			plot.addPoint(0, x, y, true);

		}
		max = max + (0.1 * max);
		plot.setYRange(min, max);
		plot.revalidate();
		plot.repaint();
	}


	/**
	 * Returns the {@code JPanel}.
	 * 
	 * @return the panel
	 */
	public JPanel getPanel() {
		return panel;
	}
}
