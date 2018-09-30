package app.fileencryption.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

import app.fileencryption.utilities.EncryptionUtility;

class EncryptDecryptFile implements ActionListener
{
	//Declaring swing components
	JFrame frame;  
	JPanel encryptPanel, decryptPanel;
	JTabbedPane tabbedPane;
	JLabel encryptFileSourcePathLabel, encryptFileDestPathLabel, encryptionKeyLabel, decryptFileSourcePathLabel, decryptFileDestPathLabel, decryptionKeyLabel;
	JTextField encryptFileSourcePath, encryptFileDestPath, encryptionKey, decryptFileSourcePath, decryptFileDestPath, decryptionKey;
	JButton encryptButton, decryptButton, exitButton;
	//constructor
	public EncryptDecryptFile()
	{
		//defining frame and panel
		frame = new JFrame();  
		encryptPanel = new JPanel();
		encryptPanel.setLayout(null);
		decryptPanel = new JPanel();
		decryptPanel.setLayout(null);
		
		//defining encryption panel
		encryptFileSourcePathLabel = new JLabel("Source Path :");
		encryptFileSourcePathLabel.setBounds(10, 10, 150, 30);
		encryptFileSourcePath = new JTextField();
		encryptFileSourcePath.setBounds(10, 40, 250, 30);
		
		//defining decryption panel		
		encryptFileDestPathLabel = new JLabel("Destination Path :");
		encryptFileDestPathLabel.setBounds(10, 70, 150, 30);
		encryptFileDestPath = new JTextField();
		encryptFileDestPath.setBounds(10, 100, 250, 30);
		
		//defining encryption key label, input box and button
		encryptionKeyLabel = new JLabel("Encryption Key :");
		encryptionKeyLabel.setBounds(10, 130, 150, 30);
		encryptionKey = new JTextField();
		encryptionKey.setBounds(10, 160, 250, 30);		
		encryptButton = new JButton("Encrypt");
		encryptButton.setBounds(100, 200, 100, 50);
		
		//defining source path label and input box
		decryptFileSourcePathLabel = new JLabel("Source Path :");
		decryptFileSourcePathLabel.setBounds(10, 10, 150, 30);
		decryptFileSourcePath = new JTextField();
		decryptFileSourcePath.setBounds(10, 40, 250, 30);
		
		//defining destination path label and input box
		decryptFileDestPathLabel = new JLabel("Destination Path :");
		decryptFileDestPathLabel.setBounds(10, 70, 150, 30);
		decryptFileDestPath = new JTextField();
		decryptFileDestPath.setBounds(10, 100, 250, 30);
		
		//defining decryption key label, input box and button
		decryptionKeyLabel = new JLabel("Encryption Key :");
		decryptionKeyLabel.setBounds(10, 130, 150, 30);
		decryptionKey = new JTextField();
		decryptionKey.setBounds(10, 160, 250, 30);
		decryptButton = new JButton("Decrypt");
		decryptButton.setBounds(100, 200, 100, 50);
		
		//adding encryption components to encryption 
		encryptPanel.add(encryptFileSourcePathLabel);
		encryptPanel.add(encryptFileSourcePath);	
		encryptPanel.add(encryptFileDestPathLabel);
		encryptPanel.add(encryptFileDestPath);		
		encryptPanel.add(encryptionKeyLabel);
		encryptPanel.add(encryptionKey);		
		encryptPanel.add(encryptButton);
	
		//adding decryption components to encryption
		decryptPanel.add(decryptFileSourcePathLabel);
		decryptPanel.add(decryptFileSourcePath);		
		decryptPanel.add(decryptFileDestPathLabel);
		decryptPanel.add(decryptFileDestPath);		
		decryptPanel.add(decryptionKeyLabel);
		decryptPanel.add(decryptionKey);		
		decryptPanel.add(decryptButton);
		
		//defining exit button
		exitButton = new JButton("Exit");
		exitButton.setBounds(220, 320, 80, 30);
		
		//adding buttons to actionlistioner
		encryptButton.addActionListener(this);
		decryptButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		//defining tabbed pane
	    tabbedPane=new JTabbedPane();  
	    tabbedPane.setBounds(10,10,300,300);

	    //adding panels to tabbed pane
	    tabbedPane.add("Encrpyt File",encryptPanel);  
	    tabbedPane.add("Decrypt File",decryptPanel);
	    
	    //adding tabbed pane and exit button to frame
	    frame.add(tabbedPane);  
	    frame.add(exitButton);
	    
	    //setting frame properties 
	    frame.setSize(320,400);  
	    frame.setLayout(null);  
	    frame.setVisible(true);  
	    frame.setResizable(false);
	}
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * listens to components and calls encryptFile() and decryptFile() as per user command
	 */
	@Override
	public void actionPerformed(ActionEvent event)
	{		
		//getting command
		String getCommand = event.getActionCommand();
		String sourcePath, destPath, message, encryptedMessage, decryptedMessage, encryptionToken, decryptionToken;		
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		File encryptedFile = null;		
		EncryptionUtility encryptionUtility = new EncryptionUtility();		
		//if user clicks on exit button
		if(getCommand.equals("Exit"))
		{
			System.exit(1);
		}
		//if user clicks on encrypt button
		else if(getCommand.equals("Encrypt"))
		{
			//getting source, destination and encryption key
			sourcePath = encryptFileSourcePath.getText();
			destPath = encryptFileDestPath.getText();
			if(sourcePath.equals(destPath))
			{
				JOptionPane.showMessageDialog(null, "Source or destination file cannot have same path", "Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
			encryptionToken = encryptionKey.getText();
			//System.out.println(sourcePath);
			//System.out.println(destPath);
			//System.out.println(encryptionToken);
			//call to generateEncryptionKey() to give an array of integers based on encryption key
			int[] encryptionKeyValArr = encryptionUtility.generateEncryptionKey(encryptionToken);
			try
			{
				encryptedFile = new File(destPath);
				bufferedReader = new BufferedReader(new FileReader(sourcePath));
				bufferedWriter = new BufferedWriter(new FileWriter(encryptedFile));				
				//reading source file
				while ((message = bufferedReader.readLine()) != null)
				{
					//call to encryptFile() which encrypts each line and returns it
					encryptedMessage = encryptionUtility.encryptFile(message,encryptionToken, encryptionKeyValArr);
					//write encrypted line into the destination file
					bufferedWriter.write(encryptedMessage+"\n");
				}
			}
			//handling file not found
			catch (IOException e)
			{
				JOptionPane.showMessageDialog(null, "Source or destination file does not exist", "Error",JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			//closing connections
			finally 
			{
				try
				{
					bufferedReader.close();
					bufferedWriter.close();
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		//if user clicks on decrypt button
		else if(getCommand.equals("Decrypt"))
		{
			//getting source, destination and decryption key
			sourcePath = decryptFileSourcePath.getText();
			destPath = decryptFileDestPath.getText();
			if(sourcePath.equals(destPath))
			{
				JOptionPane.showMessageDialog(null, "Source or destination file cannot have same path", "Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
			decryptionToken = decryptionKey.getText();
			//System.out.println(sourcePath);
			//System.out.println(destPath);
			//System.out.println(decryptionToken);
			//call to generateEncryptionKey() to give an array of integers based on decryption key
			int[] decryptionKeyValArr = encryptionUtility.generateEncryptionKey(decryptionToken);
			try
			{
				encryptedFile = new File(destPath);
				bufferedReader = new BufferedReader(new FileReader(sourcePath));
				bufferedWriter = new BufferedWriter(new FileWriter(encryptedFile));
				//reading source encrypted file
				while ((message = bufferedReader.readLine()) != null)
				{		
					//call to encryptFile() which decrypts each line and returns it
					decryptedMessage = encryptionUtility.decryptFile(message, decryptionToken, decryptionKeyValArr);
					//write decrypted line into the destination file
					bufferedWriter.write(decryptedMessage+"\n");
				}
			}
			//handling file not found
			catch (IOException e)
			{	
				JOptionPane.showMessageDialog(null, "Source or destination file does not exist", "Error",JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			//closing connections
			finally 
			{
				try
				{
					bufferedReader.close();
					bufferedWriter.close();
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}	
}
public class FileEncryptionUI
{
	public static void main(String[] args)
	{
		new EncryptDecryptFile();
	}
}