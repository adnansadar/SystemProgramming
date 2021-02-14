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

	private JFrame frame;
	private JTextPane editor;
	private JComboBox<String> FontSizeComboBox;
	private JComboBox<String> TextAlignComboBox;
	private UndoManager UndoManager;

	enum UndoActionType {
		UNDO, REDO
	};

	private static final String MainTitle = "SIMPLE TEXT EDITOR";
	private static final String DefaultFontFamily = "SansSerif";
	private static final int DefaultFontSize = 18;

	private static final String[] FontSizes = { "Font Size", "12", "14", "16", "18", "20", "22", "24", "26", "28",
			"30" };
	private static final String[] TextAlignments = { "Text Align", "Left", "Center", "Right", "Justified" };

	public static void main(String[] args) throws Exception {

		UIManager.put("TextPane.font", new Font(DefaultFontFamily, Font.PLAIN, DefaultFontSize));
		UIManager.setLookAndFeel(new NimbusLookAndFeel());

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				new texteditor().createAndShowGUI();
			}
		});
	}

	private void createAndShowGUI() {

		frame = new JFrame(MainTitle);
		editor = new JTextPane();
		JScrollPane editorScrollPane = new JScrollPane(editor);

		editor.setDocument(getNewDocument());

		UndoManager = new UndoManager();
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

		TextAlignComboBox = new JComboBox<String>(TextAlignments);
		TextAlignComboBox.setEditable(false);
		TextAlignComboBox.addItemListener(new TextAlignItemListener());

		FontSizeComboBox = new JComboBox<String>(FontSizes);
		FontSizeComboBox.setEditable(false);
		FontSizeComboBox.addItemListener(new FontSizeItemListener());

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
		panel1.add(new JSeparator(SwingConstants.VERTICAL));
		panel1.add(TextAlignComboBox);
		panel1.add(new JSeparator(SwingConstants.VERTICAL));
		panel1.add(FontSizeComboBox);

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

		editor.requestFocusInWindow();
	}

	private StyledDocument getNewDocument() {

		StyledDocument doc = new DefaultStyledDocument();
		doc.addUndoableEditListener(new UndoEditListener());
		return doc;
	}

	private class EditButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			editor.requestFocusInWindow();
		}
	}

	private class UndoEditListener implements UndoableEditListener {

		@Override
		public void undoableEditHappened(UndoableEditEvent e) {

			UndoManager.addEdit(e.getEdit()); // remember the edit
		}
	}

	private class TextAlignItemListener implements ItemListener {

		public void itemStateChanged(ItemEvent e) {

			if ((e.getStateChange() != ItemEvent.SELECTED) || (TextAlignComboBox.getSelectedIndex() == 0)) {

				return;
			}

			String alignmentStr = (String) e.getItem();
			int newAlignment = TextAlignComboBox.getSelectedIndex() - 1;
			// Align LEFT is 0, CENTER is 1, RIGHT is 2, JUSTIFIED is 3
			TextAlignComboBox.setAction(new AlignmentAction(alignmentStr, newAlignment));
			TextAlignComboBox.setSelectedIndex(0); // initialize to (default) select
			editor.requestFocusInWindow();
		}
	} // TextAlignItemListener

	private class FontSizeItemListener implements ItemListener {

		public void itemStateChanged(ItemEvent e) {

			if ((e.getStateChange() != ItemEvent.SELECTED) || (FontSizeComboBox.getSelectedIndex() == 0)) {
				return;
			}

			String fontSizeStr = (String) e.getItem();
			int newFontSize = 0;

			try {
				newFontSize = Integer.parseInt(fontSizeStr);
			} catch (NumberFormatException ex) {

				return;
			}

			FontSizeComboBox.setAction(new FontSizeAction(fontSizeStr, newFontSize));
			FontSizeComboBox.setSelectedIndex(0); // initialize to (default) select
			editor.requestFocusInWindow();
		}
	} // FontSizeItemListener

	private class UndoActionListener implements ActionListener {

		private UndoActionType undoActionType;

		public UndoActionListener(UndoActionType type) {

			undoActionType = type;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			switch (undoActionType) {

				case UNDO:
					if (!UndoManager.canUndo()) {

						editor.requestFocusInWindow();
						return; // no edits to undo
					}

					UndoManager.undo();
					break;

				case REDO:
					if (!UndoManager.canRedo()) {

						editor.requestFocusInWindow();
						return; // no edits to redo
					}

					UndoManager.redo();
			}

			editor.requestFocusInWindow();
		}
	} // UndoActionListener
}
