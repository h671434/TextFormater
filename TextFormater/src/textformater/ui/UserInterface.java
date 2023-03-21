package textformater.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import textformater.format.Formater;
import textformater.format.TabFormater;

@SuppressWarnings("serial")
public class UserInterface extends JFrame{
	
	private Formater formater = new TabFormater();
	
	private JTextArea inputArea = new JTextArea(50, 50);
	private JTextArea previewArea = new JTextArea(50, 50);
	
	public UserInterface(String title) {
		super(title);
		
		this.setForeground(Color.GRAY);
		
		JPanel inputPanel = getTextPanel("Input", inputArea);
		inputArea.setEditable(true);
		JPanel previewPanel = getTextPanel("Preview", previewArea);
		previewArea.setEditable(false);
		JPanel buttonPanel = getButtonPanel();
		
		getContentPane().add(BorderLayout.WEST, inputPanel);
		getContentPane().add(BorderLayout.EAST, previewPanel);
		getContentPane().add(BorderLayout.SOUTH, buttonPanel);
	}
	
	private JPanel getTextPanel(String header, JTextArea textArea) {
		JPanel panel = new JPanel();
		panel.setLayout (new BoxLayout(panel, BoxLayout.Y_AXIS));    
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JLabel label = new JLabel(header);
		
		panel.add(label);
		panel.add(new JScrollPane(textArea, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		
		return panel;
	}
	
	private JPanel getButtonPanel() {
		JPanel buttonPanel = new JPanel();
		
		JButton previewButton = new JButton("Preview");
		previewButton.addActionListener(e -> {
			onPreview();
		});
		
		JButton saveButton = new JButton("Save As");
		saveButton.addActionListener(e -> {
			onSave();
		});
		
		buttonPanel.add(previewButton);
		buttonPanel.add(saveButton);
		
		return buttonPanel;
	}
	
	private void onPreview() {
		previewArea.setText(formater.format(inputArea.getText()));
	}
	
	private void onSave() {
		FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Text File", "txt");
		
		final JFileChooser saveAsFileChooser = new JFileChooser();
		saveAsFileChooser.setApproveButtonText("Save");
		saveAsFileChooser.setFileFilter(extensionFilter);
		
		int actionDialog = saveAsFileChooser.showOpenDialog(this);
		if (actionDialog != JFileChooser.APPROVE_OPTION) {
			return;
		}

		// !! File fileName = new File(SaveAs.getSelectedFile() + ".txt");
		File file = saveAsFileChooser.getSelectedFile();
		if (!file.getName().endsWith(".txt")) {
			file = new File(file.getAbsolutePath() + ".txt");
		}
		
		BufferedWriter outFile = null;
		try {
			outFile = new BufferedWriter(new FileWriter(file));
	         
			previewArea.write(outFile);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
	         if (outFile != null) {
	        	 try {
	        		 outFile.close();
	        	 } catch (IOException e) {}
	         }
		}
	}

}
