package de.cs12.reliability.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.SortedMap;

import javax.swing.JFrame;
import javax.swing.UIManager;

import de.cs12.reliability.common.Samples;

/**
 * The {@code ReliabilityViewer} is a basic GUI that shows the {@code
 * ReliabilityPanel}.
 * 
 * @author glass
 * 
 */
public class ReliabilityViewer extends JFrame {

	private static final long serialVersionUID = 1L;

	protected final String title;

	protected ReliabilityPanel reliabilityPanel;

	/**
	 * Constructs a {@code ReliabilityViewer} with a given title and {@code
	 * Samples}.
	 * 
	 * @param title
	 *            the title
	 * @param samples
	 *            the samples
	 */
	public ReliabilityViewer(String title, SortedMap<String, Samples> samples) {
		this.title = title;

		reliabilityPanel = new ReliabilityPanel();

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		init(samples);
	}

	/**
	 * Initializes the frame with a given map of {@code Samples}.
	 * 
	 * @param samples
	 *            the samples
	 */
	protected final void init(SortedMap<String, Samples> samples) {
		setTitle(title);
		setLayout(new BorderLayout());

		setPreferredSize(new Dimension(800, 600));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		getContentPane().add(reliabilityPanel.get(samples));

		pack();
		setVisible(true);
	}

}