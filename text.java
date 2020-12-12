package Project;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.undo.*;
import javax.swing.event.*;
import javax.swing.text.DefaultEditorKit.*;
import javax.swing.text.StyledEditorKit.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class text {

	private JFrame frame;
	private JTextPane jtextpane;
	private UndoManager undoManager;

	enum UndoActionType {
		UNDO, REDO
	};

	private static final String ProjectTitle = "SIMPLE TEXT EDITOR";
	private static final String DefaultFont = "SansSerif";
	private static final int DefaultFontSize = 18;

	public static void main(String[] args) throws Exception {

		UIManager.put("TextPane.font", new Font(DefaultFont, Font.PLAIN, DefaultFontSize));
		UIManager.setLookAndFeel(new NimbusLookAndFeel());

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				new text().createAndShowGUI();
			}
		});
	}

	private void createAndShowGUI() {

		frame = new JFrame(ProjectTitle);
		jtextpane = new JTextPane();
		JScrollPane editorScrollPane = new JScrollPane(jtextpane);

		jtextpane.setDocument(getNewDocument());

		undoManager = new UndoManager();
		EditButtonActionListener editButtonActionListener = new EditButtonActionListener();

		JButton cutButton = new JButton(new CutAction());
		cutButton.setHideActionText(true);
		cutButton.setText("Cut");
		cutButton.addActionListener(editButtonActionListener);
		JButton copyButton = new JButton(new CopyAction());
		copyButton.setHideActionText(true);
		copyButton.setText("Copy");
		copyButton.addActionListener(editButtonActionListener);
		JButton pasteButton = new JButton(new PasteAction());
		pasteButton.setHideActionText(true);
		pasteButton.setText("Paste");
		pasteButton.addActionListener(editButtonActionListener);

		JButton boldButton = new JButton(new BoldAction());
		boldButton.setHideActionText(true);
		boldButton.setText("Bold");
		boldButton.addActionListener(editButtonActionListener);
		JButton italicButton = new JButton(new ItalicAction());
		italicButton.setHideActionText(true);
		italicButton.setText("Italic");
		italicButton.addActionListener(editButtonActionListener);
		JButton underlineButton = new JButton(new UnderlineAction());
		underlineButton.setHideActionText(true);
		underlineButton.setText("Underline");
		underlineButton.addActionListener(editButtonActionListener);

		JButton undoButton = new JButton("Undo");
		undoButton.addActionListener(new UndoActionListener(UndoActionType.UNDO));
		JButton redoButton = new JButton("Redo");
		redoButton.addActionListener(new UndoActionListener(UndoActionType.REDO));

		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel1.add(cutButton);
		panel1.add(copyButton);
		panel1.add(pasteButton);
		panel1.add(new JSeparator(SwingConstants.VERTICAL));
		panel1.add(boldButton);
		panel1.add(italicButton);
		panel1.add(underlineButton);

		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel2.add(new JSeparator(SwingConstants.VERTICAL));
		panel2.add(new JSeparator(SwingConstants.VERTICAL));
		panel2.add(undoButton);
		panel2.add(redoButton);

		JPanel toolBarPanel = new JPanel();
		toolBarPanel.setLayout(new BoxLayout(toolBarPanel, BoxLayout.PAGE_AXIS));
		toolBarPanel.add(panel1);
		toolBarPanel.add(panel2);

		frame.add(toolBarPanel, BorderLayout.NORTH);
		frame.add(editorScrollPane, BorderLayout.CENTER);

		frame.setSize(900, 500);
		frame.setLocation(150, 80);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		jtextpane.requestFocusInWindow();
	}

	private StyledDocument getNewDocument() {

		StyledDocument doc = new DefaultStyledDocument();
		doc.addUndoableEditListener(new UndoEditListener());
		return doc;
	}

	private class EditButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			jtextpane.requestFocusInWindow();
		}
	}

	private class UndoEditListener implements UndoableEditListener {

		@Override
		public void undoableEditHappened(UndoableEditEvent e) {

			undoManager.addEdit(e.getEdit()); // remember the edit
		}
	}

	private class UndoActionListener implements ActionListener {

		private UndoActionType undoActionType;

		public UndoActionListener(UndoActionType type) {

			undoActionType = type;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			switch (undoActionType) {

				case UNDO:
					if (!undoManager.canUndo()) {

						jtextpane.requestFocusInWindow();
						return; // no edits to undo
					}

					undoManager.undo();
					break;

				case REDO:
					if (!undoManager.canRedo()) {

						jtextpane.requestFocusInWindow();
						return; // no edits to redo
					}

					undoManager.redo();
			}

			jtextpane.requestFocusInWindow();
		}
	} // UndoActionListener
}
