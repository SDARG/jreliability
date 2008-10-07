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
	 * An currentAspect picker.
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

		@Override
		public void actionPerformed(ActionEvent arg0) {
			boolean changed = false;
			Aspect aspect = aspects.get(comboBox.getSelectedIndex());
			if (currentAspect != aspect) {
				currentAspect = aspect;
				changed = true;
			}

			if (changed) {
				panel.setLabels(getFirst(), getSecond());
				panel.paint(getValues());
			}

		}

		public Aspect get() {
			return currentAspect;
		}

		public void set(Aspect aspect) {
			this.currentAspect = aspect;
		}

		public String getFirst() {
			return currentAspect.getX();
		}

		public String getSecond() {
			return currentAspect.getY();
		}

		public SortedMap<Double, Double> getValues() {
			return currentAspect.getValues();
		}

	}

	public ReliabilityPanel(Samples samples) {
		this.samples = samples;
		init();

	}

	public void init() {
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

		setLabels(picker.getFirst(), picker.getSecond());
		paint(picker.getValues());

		panel.setPreferredSize(new Dimension(600, 400));

		panel.revalidate();
		panel.repaint();
	}

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.gui.Widget#getPanel()
	 */
	public JPanel getPanel() {
		return panel;
	}
}
