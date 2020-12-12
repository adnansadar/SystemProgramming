import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import javax.swing.undo.*;
import javax.swing.event.*;
import javax.swing.text.DefaultEditorKit.*;
import javax.swing.text.StyledEditorKit.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.util.List;
import java.util.*;

public class texteditor {

	private JFrame frame__;
	private JTextPane editor__;
	// private JComboBox<String> fontSizeComboBox__;
	// private JComboBox<String> textAlignComboBox__;
	// private JComboBox<String> fontFamilyComboBox__;
	private UndoManager undoMgr__;

	enum UndoActionType {
		UNDO, REDO
	};

	// is at the first text position after the number (number + dot + space).

	private static final String MAIN_TITLE = "My Editor 3";
	private static final String DEFAULT_FONT_FAMILY = "SansSerif";
	private static final int DEFAULT_FONT_SIZE = 18;
	// private static final List<String> FONT_LIST = Arrays
	// .asList(new String[] { "Arial", "Calibri", "Cambria", "Courier New", "Comic
	// Sans MS", "Dialog", "Georgia",
	// "Helevetica", "Lucida Sans", "Monospaced", "Tahoma", "Times New Roman",
	// "Verdana" });
	// private static final String[] FONT_SIZES = { "Font Size", "12", "14", "16",
	// "18", "20", "22", "24", "26", "28",
	// "30" };
	// private static final String[] TEXT_ALIGNMENTS = { "Text Align", "Left",
	// "Center", "Right", "Justified" };

	public static void main(String[] args) throws Exception {

		UIManager.put("TextPane.font", new Font(DEFAULT_FONT_FAMILY, Font.PLAIN, DEFAULT_FONT_SIZE));
		UIManager.setLookAndFeel(new NimbusLookAndFeel());

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				new texteditor().createAndShowGUI();
			}
		});
	}

	private void createAndShowGUI() {

		frame__ = new JFrame(MAIN_TITLE);
		editor__ = new JTextPane();
		JScrollPane editorScrollPane = new JScrollPane(editor__);

		editor__.setDocument(getNewDocument());

		undoMgr__ = new UndoManager();
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

		// JButton colorButton = new JButton("Set Color");
		// colorButton.addActionListener(new ColorActionListener());

		// textAlignComboBox__ = new JComboBox<String>(TEXT_ALIGNMENTS);
		// textAlignComboBox__.setEditable(false);
		// textAlignComboBox__.addItemListener(new TextAlignItemListener());

		// fontSizeComboBox__ = new JComboBox<String>(FONT_SIZES);
		// fontSizeComboBox__.setEditable(false);
		// fontSizeComboBox__.addItemListener(new FontSizeItemListener());

		// Vector<String> editorFonts = getEditorFonts();
		// editorFonts.add(0, "Font Family");
		// fontFamilyComboBox__ = new JComboBox<String>(editorFonts);
		// fontFamilyComboBox__.setEditable(false);
		// fontFamilyComboBox__.addItemListener(new FontFamilyItemListener());

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
		// panel1.add(new JSeparator(SwingConstants.VERTICAL));
		// panel1.add(colorButton);
		// panel1.add(new JSeparator(SwingConstants.VERTICAL));
		// panel1.add(textAlignComboBox__);
		// panel1.add(new JSeparator(SwingConstants.VERTICAL));
		// panel1.add(fontSizeComboBox__);
		// panel1.add(new JSeparator(SwingConstants.VERTICAL));
		// panel1.add(fontFamilyComboBox__);

		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel2.add(new JSeparator(SwingConstants.VERTICAL));
		panel2.add(new JSeparator(SwingConstants.VERTICAL));
		panel2.add(undoButton);
		panel2.add(redoButton);

		JPanel toolBarPanel = new JPanel();
		toolBarPanel.setLayout(new BoxLayout(toolBarPanel, BoxLayout.PAGE_AXIS));
		toolBarPanel.add(panel1);
		toolBarPanel.add(panel2);

		frame__.add(toolBarPanel, BorderLayout.NORTH);
		frame__.add(editorScrollPane, BorderLayout.CENTER);

		frame__.setSize(900, 500);
		frame__.setLocation(150, 80);
		frame__.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame__.setVisible(true);

		editor__.requestFocusInWindow();
	}

	private StyledDocument getNewDocument() {

		StyledDocument doc = new DefaultStyledDocument();
		doc.addUndoableEditListener(new UndoEditListener());
		return doc;
	}

	private Vector<String> getEditorFonts() {

		String[] availableFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		Vector<String> returnList = new Vector<>();

		for (String font : availableFonts) {

			if (FONT_LIST.contains(font)) {

				returnList.add(font);
			}
		}

		return returnList;
	}

	private class EditButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			editor__.requestFocusInWindow();
		}
	}

	// private class ColorActionListener implements ActionListener {

	// public void actionPerformed(ActionEvent e) {

	// Color newColor = JColorChooser.showDialog(frame__, "Choose a color",
	// Color.BLACK);
	// if (newColor == null) {

	// editor__.requestFocusInWindow();
	// return;
	// }

	// SimpleAttributeSet attr = new SimpleAttributeSet();
	// StyleConstants.setForeground(attr, newColor);
	// editor__.setCharacterAttributes(attr, false);
	// editor__.requestFocusInWindow();
	// }
	// }

	// private class TextAlignItemListener implements ItemListener {

	// public void itemStateChanged(ItemEvent e) {

	// if ((e.getStateChange() != ItemEvent.SELECTED) ||
	// (textAlignComboBox__.getSelectedIndex() == 0)) {

	// return;
	// }

	// String alignmentStr = (String) e.getItem();
	// int newAlignment = textAlignComboBox__.getSelectedIndex() - 1;
	// // New alignment is set based on these values defined in StyleConstants:
	// // ALIGN_LEFT 0, ALIGN_CENTER 1, ALIGN_RIGHT 2, ALIGN_JUSTIFIED 3
	// textAlignComboBox__.setAction(new AlignmentAction(alignmentStr,
	// newAlignment));
	// textAlignComboBox__.setSelectedIndex(0); // initialize to (default) select
	// editor__.requestFocusInWindow();
	// }
	// } // TextAlignItemListener

	// private class FontSizeItemListener implements ItemListener {

	// public void itemStateChanged(ItemEvent e) {

	// if ((e.getStateChange() != ItemEvent.SELECTED) ||
	// (fontSizeComboBox__.getSelectedIndex() == 0)) {

	// return;
	// }

	// String fontSizeStr = (String) e.getItem();
	// int newFontSize = 0;

	// try {
	// newFontSize = Integer.parseInt(fontSizeStr);
	// } catch (NumberFormatException ex) {

	// return;
	// }

	// fontSizeComboBox__.setAction(new FontSizeAction(fontSizeStr, newFontSize));
	// fontSizeComboBox__.setSelectedIndex(0); // initialize to (default) select
	// editor__.requestFocusInWindow();
	// }
	// } // FontSizeItemListener

	// private class FontFamilyItemListener implements ItemListener {

	// public void itemStateChanged(ItemEvent e) {

	// if ((e.getStateChange() != ItemEvent.SELECTED) ||
	// (fontFamilyComboBox__.getSelectedIndex() == 0)) {

	// return;
	// }

	// String fontFamily = (String) e.getItem();
	// fontFamilyComboBox__.setAction(new FontFamilyAction(fontFamily, fontFamily));
	// fontFamilyComboBox__.setSelectedIndex(0); // initialize to (default) select
	// editor__.requestFocusInWindow();
	// }
	// } // FontFamilyItemListener

	private class UndoEditListener implements UndoableEditListener {

		@Override
		public void undoableEditHappened(UndoableEditEvent e) {

			undoMgr__.addEdit(e.getEdit()); // remember the edit
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
					if (!undoMgr__.canUndo()) {

						editor__.requestFocusInWindow();
						return; // no edits to undo
					}

					undoMgr__.undo();
					break;

				case REDO:
					if (!undoMgr__.canRedo()) {

						editor__.requestFocusInWindow();
						return; // no edits to redo
					}

					undoMgr__.redo();
			}

			editor__.requestFocusInWindow();
		}
	} // UndoActionListener

}
