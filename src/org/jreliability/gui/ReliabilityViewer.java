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
import java.awt.Dimension;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

import org.jreliability.function.ReliabilityFunction;

/**
 * The {@code ReliabilityViewer} is a basic GUI that shows the {@code
 * ReliabilityPanel}.
 * 
 * @author glass
 * 
 */
public class ReliabilityViewer extends JFrame {

	/**
	 * The location of the LOGO.
	 */
	protected static final String LOGO = "icons/LOGO.png";

	/**
	 * Constructs and views {@code JFrame} with a given title, a list of {@code
	 * ReliabilityFunctions}, and the {@code Aspects}.
	 * 
	 * @param title
	 *            the title
	 * @param reliabilityFunctions
	 *            the reliabilityFunctions
	 * @param aspects
	 *            the aspects
	 */
	public static void view(String title,
			Map<String, ReliabilityFunction> reliabilityFunctions,
			List<Aspect> aspects) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		JFrame frame = new JFrame();

		ReliabilityPanel reliabilityPanel = new ReliabilityPanel(aspects);

		ImageIcon icon = getImageIcon();
		frame.setIconImage(icon.getImage());

		frame.setLayout(new BorderLayout());
		frame.setPreferredSize(new Dimension(640, 480));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(reliabilityPanel.get(reliabilityFunctions));

		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Returns the {@code ImageIcon}.
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

	private static final long serialVersionUID = 1L;

}