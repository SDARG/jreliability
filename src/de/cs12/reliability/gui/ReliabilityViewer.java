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
package de.cs12.reliability.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.SortedMap;

import javax.swing.JFrame;
import javax.swing.UIManager;

import de.cs12.reliability.function.Function;

/**
 * The {@code ReliabilityViewer} is a basic GUI that shows the {@code
 * ReliabilityPanel}.
 * 
 * @author glass
 * 
 */
public class ReliabilityViewer extends JFrame {

	/**
	 * The standard serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The title of the viewer.
	 */
	protected final String title;

	/**
	 * The shown reliability panel.
	 */
	protected ReliabilityPanel reliabilityPanel;

	/**
	 * Constructs a {@code ReliabilityViewer} with a given title, a list of
	 * {@code Functions}, and the {@code Aspects}.
	 * 
	 * @param title
	 *            the title
	 * @param functions
	 *            the functions
	 * @param aspects
	 *            the aspects
	 */
	public ReliabilityViewer(String title,
			SortedMap<String, Function> functions, List<Aspect> aspects) {
		this.title = title;

		reliabilityPanel = new ReliabilityPanel(aspects);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		init(functions);
	}

	/**
	 * Initializes the frame with a given set of {@code Functions}.
	 * 
	 * @param functions
	 *            the functions
	 */
	protected final void init(SortedMap<String, Function> functions) {
		setTitle(title);
		setLayout(new BorderLayout());

		setPreferredSize(new Dimension(800, 600));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		getContentPane().add(reliabilityPanel.get(functions));

		pack();
		setVisible(true);
	}

}