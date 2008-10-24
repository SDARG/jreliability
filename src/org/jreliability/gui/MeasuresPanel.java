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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import org.jreliability.evaluator.InverseEvaluator;
import org.jreliability.evaluator.MomentEvaluator;
import org.jreliability.function.ReliabilityFunction;

/**
 * The {@code MeasuresPanel} collects a {@code MeasurePanel} for each {@code
 * ReliabilityFunction} that shall be shown in the GUI and adds them to a
 * {@code JTabbedPane}.
 * 
 * @author glass
 * 
 */
public class MeasuresPanel extends JPanel {

	/**
	 * The standard serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The used {@code JTabbedPane}.
	 */
	protected JTabbedPane tabs;

	/**
	 * The {@code ReliabilityFunctions} that shall be shown in the GUI.
	 */
	protected final Map<String, ReliabilityFunction> reliabilityFunctions;

	/**
	 * Constructs a {@code MeasuresPanel} with a given {@code
	 * ReliabilityFunctions} and their {@code Identifiers}.
	 * 
	 * @param reliabilityFunctions
	 *            the reliability functions and their identifiers.
	 */
	public MeasuresPanel(Map<String, ReliabilityFunction> reliabilityFunctions) {
		this.reliabilityFunctions = reliabilityFunctions;
		initialize();
	}

	/**
	 * Initializes the {@code MeasuresPanel} by adding all single {@code
	 * MeasurePanels} to a {@code JTabbedPane}.
	 */
	protected void initialize() {
		tabs = new JTabbedPane();
		for (Entry<String, ReliabilityFunction> entry : reliabilityFunctions
				.entrySet()) {
			String name = entry.getKey();
			ReliabilityFunction function = entry.getValue();
			MeasurePanel measurePanel = new MeasurePanel(function);
			tabs.addTab(name, measurePanel);
		}
		this.add(tabs);
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder("Reliability-related Measures"),
				BorderFactory.createEmptyBorder(5, 0, 0, 0)));
	}

	/**
	 * The {@code MeasurePanel} shows some common reliability-related measures
	 * that are derived from the {@code ReliabilityFunctions}. Currently, these
	 * are related directly to the {@code ReliabilityFunction}, i.e., the
	 * expected value, the variance, and the standard deviation, as well as some
	 * familiar reliability measures like Mean-Time-To-Failure and Mission-Time.
	 * 
	 * @author glass
	 * 
	 */
	protected class MeasurePanel extends JPanel implements ActionListener {

		/**
		 * The {@code Evaluator} to determine the first moment, i.e., the
		 * expected value.
		 */
		protected MomentEvaluator firstMoment = new MomentEvaluator(1);
		/**
		 * The {@code Evaluator} to determine the second moment, used to derive
		 * the variance and deviation.
		 */
		protected MomentEvaluator secondMoment = new MomentEvaluator(2);
		/**
		 * The {@code Evaluator} to calculate the inverse of the {@code
		 * Distribution} of the {@code ReliabilityFunction}, used to derive the
		 * Mission-Time.
		 */
		protected InverseEvaluator inverse = new InverseEvaluator();
		/**
		 * The used {@code ReliabilityFunction}.
		 */
		protected final ReliabilityFunction reliabilityFunction;
		/**
		 * The {code JLabel} that is used to display the Mission-Time {@code MT}
		 * for the user specified probability {@code p} in {@code p = P[MT]}.
		 */
		protected JLabel mt;
		/**
		 * The probability {@code p} in {@code p = P[MT]}.
		 */
		protected JFormattedTextField mtProbability;
		/**
		 * The used {@code NumberFormat} for the {@code mtProbability} text
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
		 * Constructs a {@code MeasurePanel} with a given {@code
		 * ReliabilityFunction}.
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
			this.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();

			DecimalFormatSymbols symbol = new DecimalFormatSymbols();
			;
			symbol.setDecimalSeparator('.');
			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setDecimalFormatSymbols(symbol);
			mtFieldFormat = decimalFormat;

			// Collect all the interesting values.
			Double expected = firstMoment.evaluate(reliabilityFunction);
			Double variance = secondMoment.evaluate(reliabilityFunction)
					- Math.pow(expected, 2);
			Double deviation = Math.sqrt(variance);
			Double mtVal = inverse.evaluate(reliabilityFunction, standardMT);

			JLabel expectedLabel = new JLabel("Expected Value:");
			JLabel expectedValue = new JLabel(expected.toString());

			JLabel varianceLabel = new JLabel("Variance:");
			JLabel varianceValue = new JLabel(variance.toString());

			JLabel deviationLabel = new JLabel("Deviation:");
			JLabel deviationValue = new JLabel(deviation.toString());

			c.ipady = 10;
			c.ipadx = 5;
			c.gridx = 0;
			c.gridy = 5;
			c.gridwidth = 2;
			this.add(new JLabel("Properties"), c);
			c.gridwidth = 1;

			c.ipady = 10;
			c.ipadx = 5;
			c.gridx = 0;
			c.gridy = 10;
			this.add(expectedLabel, c);

			c.ipady = 10;
			c.ipadx = 5;
			c.gridx = 1;
			c.gridy = 10;
			this.add(expectedValue, c);

			c.ipady = 10;
			c.ipadx = 5;
			c.gridx = 0;
			c.gridy = 20;
			this.add(varianceLabel, c);

			c.ipady = 10;
			c.ipadx = 5;
			c.gridx = 1;
			c.gridy = 20;
			this.add(varianceValue, c);

			c.ipady = 10;
			c.ipadx = 5;
			c.gridx = 0;
			c.gridy = 30;
			this.add(deviationLabel, c);

			c.ipady = 10;
			c.ipadx = 5;
			c.gridx = 1;
			c.gridy = 30;
			this.add(deviationValue, c);

			c.ipady = 10;
			c.ipadx = 5;
			c.gridx = 0;
			c.gridy = 40;
			c.gridwidth = 2;
			this.add(new JSeparator(), c);
			c.gridwidth = 1;

			JLabel mttfLabel = new JLabel("MTTF:");
			JLabel mttfValue = new JLabel(expected.toString());

			c.ipady = 10;
			c.ipadx = 5;
			c.gridx = 0;
			c.gridy = 45;
			c.gridwidth = 2;
			this.add(new JLabel("Mean-Time-To-Failure"), c);
			c.gridwidth = 1;

			c.ipady = 10;
			c.ipadx = 5;
			c.gridx = 0;
			c.gridy = 50;
			this.add(mttfLabel, c);

			c.ipady = 10;
			c.ipadx = 5;
			c.gridx = 1;
			c.gridy = 50;
			this.add(mttfValue, c);

			c.ipady = 10;
			c.ipadx = 5;
			c.gridx = 0;
			c.gridy = 60;
			c.gridwidth = 2;
			this.add(new JSeparator(), c);
			c.gridwidth = 1;

			c.ipady = 10;
			c.ipadx = 5;
			c.gridx = 0;
			c.gridy = 65;
			c.gridwidth = 2;
			this.add(new JLabel("Mission-Time"), c);
			c.gridwidth = 1;

			JLabel pmtLabel = new JLabel("P[MT] =");
			JLabel mtLabel = new JLabel("MT:");
			mtProbability = new JFormattedTextField(mtFieldFormat);
			mtProbability.addActionListener(MeasurePanel.this);
			mtProbability.setPreferredSize(new Dimension(70, 15));
			mtProbability.setHorizontalAlignment(SwingConstants.RIGHT);
			mtProbability.setText(standardMT.toString());
			mt = new JLabel(mtVal.toString());

			c.ipady = 10;
			c.ipadx = 5;
			c.gridx = 0;
			c.gridy = 70;
			this.add(pmtLabel, c);

			c.ipady = 10;
			c.ipadx = 5;
			c.gridx = 1;
			c.gridy = 70;
			this.add(mtProbability, c);

			c.ipady = 10;
			c.ipadx = 5;
			c.gridx = 0;
			c.gridy = 80;
			this.add(mtLabel, c);

			c.ipady = 10;
			c.ipadx = 5;
			c.gridx = 1;
			c.gridy = 80;
			this.add(mt, c);

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		public void actionPerformed(ActionEvent e) {
			Double value = ((Number) mtProbability.getValue()).doubleValue();
			if (value <= 0 || value >= 1.0) {
				mtProbability.setText(standardMT.toString());
			} else {
				Double mtVal = inverse.evaluate(reliabilityFunction, value);
				mt.setText(mtVal.toString());
			}
		}
	}

}
