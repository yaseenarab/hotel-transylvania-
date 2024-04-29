package Hotel.UI;

import javax.swing.*;

import Hotel.ShoppingService.ItemSpec;
import Hotel.Utilities.Utilities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;

// Panel for individual items
public class ShoppingItemPanel extends JPanel {
    private static final Logger SIP_Logger
            = Logger.getLogger(ShoppingMainPanel.class.getName());

    private JPanel itemContent;

    private ItemSpec item;

    public ShoppingItemPanel(ShoppingMainPanel shoppingPanel, ItemSpec is) {
        item = is;
        itemContent = new JPanel();
        itemContent.setLayout(new BoxLayout(itemContent, BoxLayout.X_AXIS));

        // Item picture
        JLabel itemPic;
        try {
            BufferedImage BI = Utilities.generateImage(this, is.getImageURL());
            itemPic = new JLabel(new ImageIcon(BI));
            SIP_Logger.info("Successfully loaded picture for item : " + is.getName());
        } catch (IOException e) {
            SIP_Logger.warning("Unable to load picture for item : " + is.getName());
            itemPic = new JLabel(new ImageIcon());
        }
        itemContent.add(itemPic);

        // Panel for item information
        JPanel itemInformation = new JPanel();
        itemInformation.setLayout(new BoxLayout(itemInformation, BoxLayout.Y_AXIS));

        JLabel itemName = new JLabel(item.getName());
        itemName.setFont(new Font("Arial", Font.PLAIN, 32));
        itemInformation.add(itemName);

        JLabel itemDescription = new JLabel(item.getDescription());
        itemDescription.setFont(new Font("Arial", Font.PLAIN, 18));
        itemInformation.add(itemDescription);

        JLabel itemPrice = new JLabel(item.getPrice().toPlainString());
        itemPrice.setFont(new Font("Arial", Font.BOLD, 24));
        itemInformation.add(itemPrice);
        JButton addBtn = new JButton("Add to cart");
        addBtn.addActionListener(e -> {
            shoppingPanel.getGuest().getCart().addItem(item);
            JOptionPane.showMessageDialog(null, "Item added to cart!");
            shoppingPanel.getShoppingCL().show(shoppingPanel.getSMP(), "ShoppingMain");
        });

        //shoppingPanel.getShoppingCL
    }
}
