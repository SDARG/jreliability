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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import org.jreliability.evaluator.Evaluator;
import org.jreliability.evaluator.InverseEvaluator;
import org.jreliability.evaluator.MomentEvaluator;
import org.jreliability.function.UnreliabilityFunction;
import org.jreliability.function.ReliabilityFunction;

/**
 * The {@link MeasuresPanel} collects a {@link MeasurePanel} for each
 * {@link ReliabilityFunction} that shall be shown in the GUI and adds them to a
 * {@link JTabbedPane}.
 * 
 * @author glass
 * 
 */
public class MeasuresPanel extends JPanel {

	/**
	 * The width of the widest subpanel.
	 */
	private static int largestWidth = 0;
	/**
	 * The set of subpanels.
	 */
	private static final Set<JPanel> SUBPANELS = new HashSet<>();

	/**
	 * The standard serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The used {@link JTabbedPane}.
	 */
	protected JTabbedPane tabs;

	/**
	 * The {@link ReliabilityFunction}s that shall be shown in the GUI.
	 */
	protected final Map<String, ReliabilityFunction> reliabilityFunctions;

	/**
	 * Constructs a {@link MeasuresPanel} with given
	 * {@link ReliabilityFunction}s and their identifiers.
	 * 
	 * @param reliabilityFunctions
	 *            the reliability functions and their identifiers.
	 */
	public MeasuresPanel(Map<String, ReliabilityFunction> reliabilityFunctions) {
		this.reliabilityFunctions = reliabilityFunctions;
		initialize();
	}

	/**
	 * Initializes the {@link MeasuresPanel} by adding all single
	 * {@link MeasurePanel}s to a {@link JTabbedPane}.
	 */
	protected void initialize() {
		tabs = new JTabbedPane();
		for (Entry<String, ReliabilityFunction> entry : reliabilityFunctions.entrySet()) {
			String name = entry.getKey();
			ReliabilityFunction function = entry.getValue();
			MeasurePanel measurePanel = new MeasurePanel(function);
			tabs.addTab(name, measurePanel);
		}
		this.add(tabs);
		this.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Reliability-related Measures"),
						BorderFactory.createEmptyBorder(5, 0, 0, 0)));
	}

	/**
	 * The {@link MeasurePanel} shows some common reliability-related measures
	 * that are derived from the {@link ReliabilityFunction}s. Currently, these
	 * are related directly to the {@link ReliabilityFunction}, i.e., the
	 * expected value, the variance, and the standard deviation, as well as some
	 * familiar reliability measures like Mean-Time-To-Failure and Mission-Time.
	 * 
	 * @author glass
	 * 
	 */
	protected static class MeasurePanel extends JPanel implements ActionListener {

		/**
		 * The {@link Evaluator} to determine the first moment, i.e., the
		 * expected value.
		 */
		protected MomentEvaluator firstMoment = new MomentEvaluator(1);
		/**
		 * The {@link Evaluator} to determine the second moment, used to derive
		 * the variance and deviation.
		 */
		protected MomentEvaluator secondMoment = new MomentEvaluator(2);
		/**
		 * The {@link Evaluator} to calculate the inverse of the
		 * {@link UnreliabilityFunction} of the {@link ReliabilityFunction},
		 * used to derive the Mission-Time.
		 */
		protected InverseEvaluator inverse = new InverseEvaluator();
		/**
		 * The used {@link ReliabilityFunction}.
		 */
		protected final ReliabilityFunction reliabilityFunction;
		/**
		 * The {@link JLabel} that is used to display the Mission-Time MT for
		 * the user specified probability {@code p} in {@code p = P[MT]}.
		 */
		protected JLabel mt;
		/**
		 * The probability {@code p} in {@code p = P[MT]}.
		 */
		protected JFormattedTextField mtProbability;
		/**
		 * The used {@link NumberFormat} for the {@code mtProbability} text
		 * field.
		 */
		protected NumberFormat mtFieldFormat;
		/**
		 * The used standard value for the {@code mtProbability}.
		 */
		protected Double standardMT = 0.999;
		/**
		 * The standard serialVersionUID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructs a {@link MeasurePanel} with a given
		 * {@link ReliabilityFunction}.
		 * 
		 * @param reliabilityFunction
		 *            the reliability function
		 */
		public MeasurePanel(ReliabilityFunction reliabilityFunction) {
			this.reliabilityFunction = reliabilityFunction;
			initialize();
		}

		/**
		 * Initializes the panel.
		 */
		private void initialize() {
			this.setLayout(new GridLayout(0, 1, 10, 10));

			DecimalFormatSymbols symbol = new DecimalFormatSymbols();

			symbol.setDecimalSeparator('.');
			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setDecimalFormatSymbols(symbol);
			mtFieldFormat = decimalFormat;

			Double expected = firstMoment.evaluate(reliabilityFunction);

			JLabel expectedLabel = new JLabel("Expected Value:");
			JPanel propertiesPanel = createPropertiesPanel(expected, expectedLabel);
			JPanel mttfPanel = createMttfPanel(expected, expectedLabel.getPreferredSize());
			JPanel mtPanel = createMtPanel(expectedLabel.getPreferredSize());

			this.add(propertiesPanel);
			this.add(mttfPanel);
			this.add(mtPanel);

			revalidate();
			repaint();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent )
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			Double value = ((Number) mtProbability.getValue()).doubleValue();
			if (value <= 0 || value >= 1.0) {
				mtProbability.setText(standardMT.toString());
			} else {
				Double mtVal = inverse.evaluate(reliabilityFunction, value);
				mt.setText(mtVal.toString());
			}
		}

		/**
		 * Creates the {@link JPanel} showing the properties.
		 * 
		 * @param expected
		 *            the expected value
		 * @param expectedLabel
		 *            the "expected" label, i.e. the longest
		 * @return the JPanel showing the properties
		 */
		private JPanel createPropertiesPanel(Double expected, JLabel expectedLabel) {
			Double variance = secondMoment.evaluate(reliabilityFunction) - Math.pow(expected, 2);
			Double deviation = Math.sqrt(variance);

			JLabel expectedValue = new JLabel(expected.toString());

			JLabel varianceLabel = new JLabel("Variance:");
			JLabel varianceValue = new JLabel(variance.toString());

			JLabel deviationLabel = new JLabel("Deviation:");
			JLabel deviationValue = new JLabel(deviation.toString());

			return createSubPanel("Properties", expectedLabel, expectedValue, varianceLabel, varianceValue,
					deviationLabel, deviationValue);
		}

		/**
		 * Creates the {@link JPanel} showing the mean time to failure.
		 * 
		 * @param mttf
		 *            the mean time to failure
		 * @param maxWidth
		 *            the dimension of the longest label
		 * @return the JPanel showing the mean time to failure
		 */
		private JPanel createMttfPanel(Double mttf, Dimension maxWidth) {
			JLabel mttfLabel = new JLabel("MTTF:");
			mttfLabel.setMinimumSize(maxWidth);

			JLabel expectedValue = new JLabel(mttf.toString());

			return createSubPanel("Mean-Time-To-Failure", mttfLabel, expectedValue);
		}

		/**
		 * Creates the {@link JPanel} showing the mission time.
		 * 
		 * @param maxwidth
		 *            the dimension of the longest label
		 * @return the JPanel showing the mission time
		 */
		private JPanel createMtPanel(Dimension maxwidth) {
			Double mtVal = inverse.evaluate(reliabilityFunction, standardMT);

			JLabel pmtLabel = new JLabel("P[MT] =");
			pmtLabel.setMinimumSize(maxwidth);

			JLabel mtLabel = new JLabel("MT:");
			mtLabel.setMinimumSize(maxwidth);

			mtProbability = new JFormattedTextField(mtFieldFormat);
			mtProbability.addActionListener(MeasurePanel.this);
			mtProbability.setPreferredSize(new Dimension(70, 15));
			mtProbability.setHorizontalAlignment(SwingConstants.RIGHT);
			mtProbability.setText(standardMT.toString());

			mt = new JLabel(mtVal.toString());

			return createSubPanel("Mission-Time", pmtLabel, mtProbability, mtLabel, mt);
		}

		/**
		 * Creates a {@link JPanel} with a title and a set of label/value pairs.
		 * 
		 * Each label must be followed by its corresponding value. Therefore the
		 * number of components must be even.
		 * 
		 * @param title
		 *            the title of the panel
		 * @param components
		 *            the alternating array of labels and their corresponding
		 *            values
		 * @return
		 */
		private JPanel createSubPanel(String title, JComponent... components) {
			if (components.length % 2 == 1) {
				throw new IllegalArgumentException("Number of components is odd (" + components.length + ").");
			}

			JPanel subPanel = new JPanel();

			subPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)));

			subPanel.setLayout(new GridLayout(0, 2, 10, 10));

			for (int i = 0; i < components.length; i++) {
				if (i % 2 == 0) {
					Font myFont = components[i].getFont();
					components[i].setFont(myFont.deriveFont(Font.PLAIN));
				} else {
					Font myFont = components[i].getFont();
					components[i].setFont(myFont.deriveFont(Font.BOLD));
				}
				subPanel.add(components[i]);
			}

			if (subPanel.getPreferredSize().width > largestWidth) {
				largestWidth = subPanel.getPreferredSize().width;
				for (JPanel other : SUBPANELS) {
					other.setPreferredSize(new Dimension(largestWidth, other.getPreferredSize().height));
					other.revalidate();
					other.repaint();
				}
			} else {
				subPanel.setPreferredSize(new Dimension(largestWidth, subPanel.getPreferredSize().height));
			}
			SUBPANELS.add(subPanel);
			return subPanel;
		}
	}

}
