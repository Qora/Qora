package gui.assets;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import qora.assets.Asset;
import controller.Controller;

public class AssetDetailsPanel extends JPanel {

	private static final long serialVersionUID = 4763074704570450206L;
	
	private Asset asset;

	private JButton favoritesButton;
	
	public AssetDetailsPanel(Asset asset)
	{
		this.asset = asset;
	
		//LAYOUT
		this.setLayout(new GridBagLayout());
		
		//PADDING
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		//LABEL GBC
		GridBagConstraints labelGBC = new GridBagConstraints();
		labelGBC.insets = new Insets(0, 5, 5, 0);
		labelGBC.fill = GridBagConstraints.HORIZONTAL;   
		labelGBC.anchor = GridBagConstraints.NORTHWEST;
		labelGBC.weightx = 0;	
		labelGBC.gridx = 0;
		
		//DETAIL GBC
		GridBagConstraints detailGBC = new GridBagConstraints();
		detailGBC.insets = new Insets(0, 5, 5, 0);
		detailGBC.fill = GridBagConstraints.HORIZONTAL;  
		detailGBC.anchor = GridBagConstraints.NORTHWEST;
		detailGBC.weightx = 1;	
		detailGBC.gridwidth = 2;
		detailGBC.gridx = 1;		
		
		//LABEL KEY
		labelGBC.gridy = 1;
		JLabel keyLabel = new JLabel("Key:");
		this.add(keyLabel, labelGBC);
				
		//KEY
		detailGBC.gridy = 1;
		JTextField txtKey = new JTextField(Long.toString(asset.getKey()));
		txtKey.setEditable(false);
		this.add(txtKey, detailGBC);	
		
		//LABEL NAME
		labelGBC.gridy = 2;
		JLabel nameLabel = new JLabel("Name:");
		this.add(nameLabel, labelGBC);
		
		//NAME
		detailGBC.gridy = 2;
		JTextField txtName = new JTextField(asset.getName());
		txtName.setEditable(false);
		this.add(txtName, detailGBC);		
		
		//LABEL DESCRIPTION
		labelGBC.gridy = 3;
		JLabel descriptionLabel = new JLabel("Description:");
		this.add(descriptionLabel, labelGBC);
		           
		//DESCRIPTION
		detailGBC.gridy = 3;
		JTextArea txtAreaDescription = new JTextArea(asset.getDescription());
		txtAreaDescription.setRows(4);
		txtAreaDescription.setBorder(txtName.getBorder());
		txtAreaDescription.setEditable(false);
		this.add(txtAreaDescription, detailGBC);	
		
		//LABEL OWNER
		labelGBC.gridy = 4;
		JLabel ownerLabel = new JLabel("Owner:");
		this.add(ownerLabel, labelGBC);
				
		//OWNER
		detailGBC.gridy = 4;
		JTextField owner = new JTextField(asset.getOwner().getAddress());
		owner.setEditable(false);
		this.add(owner, detailGBC);
		
		//LABEL QUANTITY
		labelGBC.gridy = 5;
		JLabel quantityLabel = new JLabel("Quantity:");
		this.add(quantityLabel, labelGBC);
		           
		//QUANTITY
		detailGBC.gridy = 5;
		JTextField txtQuantity = new JTextField(asset.getQuantity().toString());
		txtQuantity.setEditable(false);
		this.add(txtQuantity, detailGBC);		
		
		//LABEL DIVISIBLE
		labelGBC.gridy = 6;
		JLabel divisibleLabel = new JLabel("Divisible:");
		this.add(divisibleLabel, labelGBC);
		           
		//DIVISIBLE
		detailGBC.gridy = 6;
		JCheckBox chkDivisible = new JCheckBox();
		chkDivisible.setSelected(asset.isDivisible());
		chkDivisible.setEnabled(false);
		this.add(chkDivisible, detailGBC);	
		
		//IF ASSET CONFIRMED AND NOT QORA
		if(this.asset.getKey() > 0)
		{
			//ADD QORA PAIR BUTTON
			labelGBC.gridy++;
			labelGBC.gridwidth = 2;
			JButton openQoraPairButton = new JButton("Open Qora pair");
			openQoraPairButton.setPreferredSize(new Dimension(200, 25));
			openQoraPairButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					onQoraPairClick();
				}
			});	
			this.add(openQoraPairButton, labelGBC);
		}
		
		//IF ASSET CONFIRMED
		if(this.asset.getKey() >= 0)
		{
			//ADD QORA PAIR BUTTON
			labelGBC.gridy++;
			labelGBC.gridwidth = 2;
			JButton openPairButton = new JButton("Open pair");
			openPairButton.setPreferredSize(new Dimension(200, 25));
			openPairButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					onOpenPairClick();
				}
			});	
			this.add(openPairButton, labelGBC);
		}
		
		//IF ASSET CONFIRMED AND NOT QORA
		if(this.asset.getKey() > 0)
		{
			//FAVORITES
			labelGBC.gridy++;
			labelGBC.gridwidth = 2;
			this.favoritesButton = new JButton();
			
			//CHECK IF FAVORITES
			if(Controller.getInstance().isAssetFavorite(asset))
			{
				this.favoritesButton.setText("Remove Favorite");
			}
			else
			{
				this.favoritesButton.setText("Add Favorite");
			}
				
			this.favoritesButton.setPreferredSize(new Dimension(200, 25));
			this.favoritesButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					onFavoriteClick();
				}
			});	
			this.add(this.favoritesButton, labelGBC);
			
		}
		
        //PACK
		this.setVisible(true);
	}
	
	public void onQoraPairClick() {
		
		//GET QORA ASSET
		Asset qoraAsset = Controller.getInstance().getQoraAsset();
		
		new ExchangeFrame(this.asset, qoraAsset);
	}
	
	public void onOpenPairClick() {
		
		//GET ASSET
		String response = JOptionPane.showInputDialog(new JFrame(), "Asset key:", "Open pair", JOptionPane.QUESTION_MESSAGE);
		try
		{
			long key = Long.parseLong(response);
			
			if(key != this.asset.getKey())
			{
				Asset asset = Controller.getInstance().getAsset(key);
				if(asset != null)
				{
					new ExchangeFrame(this.asset, asset);
					return;
				}
			}
			
			JOptionPane.showMessageDialog(new JFrame(), "No asset with that key found!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(new JFrame(), "Invalid key!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void onFavoriteClick()
	{
		//CHECK IF FAVORITES
		if(Controller.getInstance().isAssetFavorite(asset))
		{
			this.favoritesButton.setText("Add Favorite");
			Controller.getInstance().removeAssetFavorite(this.asset);
		}
		else
		{
			this.favoritesButton.setText("Remove Favorite");
			Controller.getInstance().addAssetFavorite(this.asset);
		}
			
	}
	
}
