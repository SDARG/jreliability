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

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.jreliability.function.ReliabilityFunction;
import org.jreliability.gui.aspect.Aspect;
import org.jreliability.gui.aspect.DensityAspect;
import org.jreliability.gui.aspect.DistributionAspect;
import org.jreliability.gui.aspect.FailureRateAspect;
import org.jreliability.gui.aspect.ReliabilityFunctionAspect;
import org.jreliability.gui.sampler.Sampler;
import org.jreliability.gui.sampler.TTFFrequencyDistributionSampler;

/**
 * The {@code ReliabilityViewer} is a basic GUI that shows the {@code
 * MeasuresPanel}, the {@code ReliabilityFunctionPlotPanel} and, if desired, the
 * {@code SamplerHistogramPanel}.
 * 
 * @author glass
 * 
 */
public abstract class ReliabilityViewer extends JFrame {

	/**
	 * The location of the LOGO.
	 */
	protected static final String LOGO = "icons/logo.png";

	/**
	 * The standard serialVersionID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a {@code ReliabilityViewer}.
	 * 
	 */
	public ReliabilityViewer() {
		super();
	}

	/**
	 * Constructs and views {@code JFrame} with a given title and a list of
	 * {@code ReliabilityFunctions}. This standard {@code view()} does not show
	 * the {@code SampleHistogramPanel}.
	 * 
	 * @param title
	 *            the title
	 * @param reliabilityFunctions
	 *            the reliabilityFunctions
	 */
	public static void view(String title, Map<String, ReliabilityFunction> reliabilityFunctions) {
		view(title, reliabilityFunctions, false);
	}

	/**
	 * Constructs and views a {@code JFrame} with a given title, a list of
	 * {@code ReliabilityFunctions}, and a boolean variable that enable to show
	 * the {@code SampleHistogramPanel}.
	 * 
	 * @param title
	 *            the title
	 * @param reliabilityFunctions
	 *            the reliabilityFunctions
	 * @param showSampleHistograms
	 *            enables to show the sample histogram panel
	 */
	public static void view(String title, Map<String, ReliabilityFunction> reliabilityFunctions, boolean showSampleHistograms) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Setup the aspects for the plot panel
		List<Aspect> aspects = new ArrayList<Aspect>();
		aspects.add(new ReliabilityFunctionAspect());
		aspects.add(new DistributionAspect());
		aspects.add(new DensityAspect());
		aspects.add(new FailureRateAspect());
		ReliabilityFunctionPlotPanel reliabilityFunctionPlotPanel = new ReliabilityFunctionPlotPanel(aspects);
		JPanel plotPanel = reliabilityFunctionPlotPanel.get(reliabilityFunctions);

		JPanel measuresPanel = new MeasuresPanel(reliabilityFunctions);

		// Setup the samplers for the histogram panel?
		JPanel samplerPanel = null;
		if (showSampleHistograms) {
			List<Sampler> samplers = new ArrayList<Sampler>();
			samplers.add(new TTFFrequencyDistributionSampler());
			SamplerHistogramPanel samplerHistogramPanel = new SamplerHistogramPanel(samplers);
			samplerPanel = samplerHistogramPanel.get(reliabilityFunctions);
		}
		JFrame frame = new JFrame();

		frame.setTitle(title);
		frame.setIconImage(getImageIcon().getImage());
		if (showSampleHistograms) {
			frame.setPreferredSize(new Dimension(1000, 800));
		} else {
			frame.setPreferredSize(new Dimension(1000, 500));
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container content = frame.getContentPane();
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.ipady = 10;
		c.ipadx = 10;
		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.gridy = 5;
		c.weighty = 0.5;
		content.add(measuresPanel, c);
		c.gridx = 1;
		c.gridy = 5;
		c.weightx = 1.0;
		c.weighty = 0.5;
		content.add(plotPanel, c);
		if (showSampleHistograms) {
			c.gridx = 0;
			c.gridy = 10;
			c.gridwidth = 2;
			c.weightx = 0.5;
			c.weighty = 1.0;
			content.add(samplerPanel, c);
		}
		c.gridx = 0;
		c.gridy = 20;
		c.gridwidth = 2;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_END;
		content.add(new JLabel(" \u00A9 JReliability.org 2008"), c);

		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Returns the {@code ImageIcon}.
	 * 
	 * @return the image icon
	 */
	protected static final ImageIcon getImageIcon() {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		URL url = classLoader.getResource(LOGO);
		try {
			ImageIcon icon = new ImageIcon(url);
			return icon;
		} catch (NullPointerException e) {
			System.err.println("Image " + LOGO + " not found.");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns the GUI as a {@code JPanel} with a given title and a map of
	 * {@code ReliabilityFunctions}.
	 * 
	 * @param title
	 *            the title of the panel
	 * @param reliabilityFunctions
	 *            the reliability functions to visualize
	 * @return the GUI as a JPanel
	 */
	public static JPanel getPanel(String title, Map<String, ReliabilityFunction> reliabilityFunctions) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Setup the aspects for the plot panel
		List<Aspect> aspects = new ArrayList<Aspect>();
		aspects.add(new ReliabilityFunctionAspect());
		aspects.add(new DistributionAspect());
		aspects.add(new DensityAspect());
		aspects.add(new FailureRateAspect());
		ReliabilityFunctionPlotPanel reliabilityFunctionPlotPanel = new ReliabilityFunctionPlotPanel(aspects);
		JPanel plotPanel = reliabilityFunctionPlotPanel.get(reliabilityFunctions);

		JPanel measuresPanel = new MeasuresPanel(reliabilityFunctions);

		JPanel viewPanel = new JPanel();

		viewPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.ipady = 10;
		c.ipadx = 10;
		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.gridy = 5;
		c.weighty = 0.5;
		viewPanel.add(measuresPanel, c);
		c.gridx = 1;
		c.gridy = 5;
		c.weightx = 1.0;
		c.weighty = 0.5;
		viewPanel.add(plotPanel, c);

		return viewPanel;
	}

}