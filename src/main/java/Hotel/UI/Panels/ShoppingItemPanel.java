package UI.Panels;

import javax.swing.*;

import ShoppingService.ItemSpec;
import Utilities.Utilities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Class to display the information for a selected item within a JPanel, with the ability
 * to add the item to the user's cart
 *
 * @author Rafe Loya
 */

public class ShoppingItemPanel extends JPanel {
    private static final Logger SIP_Logger
            = Logger.getLogger(ShoppingMainPanel.class.getName());

    /**
     * Specific item to display
     */
    private ItemSpec item;

    /**
     * Maximum dimensions of an item's image to display
     */
    private static final int imageMaxDimension = 360;

    /**
     * Creates a JPanel for a specific item, displaying relevant information to the user, and the option to add it to their cart
     *
     * @param shoppingPanel The main shopping panel, configured to display the shopping system's UI
     * @param is Specific item to display information
     */
    public ShoppingItemPanel(ShoppingMainPanel shoppingPanel, ItemSpec is) {
        item = is;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Item picture
        JLabel itemPic;
        try {
            BufferedImage BI = Utilities.generateImage(this, is.getImageURL());
            itemPic = new JLabel(new ImageIcon(BI.getScaledInstance(
                    Math.min(imageMaxDimension, BI.getWidth()),
                    Math.min(imageMaxDimension, BI.getHeight()),
                    Image.SCALE_SMOOTH
            )));
            SIP_Logger.info("Successfully loaded picture for item : " + is.getName());
        } catch (IOException e) {
            SIP_Logger.warning("Unable to load picture for item : " + is.getName());
            itemPic = new JLabel(new ImageIcon());
        }
        add(itemPic);

        // Panel for item information
        JPanel itemInformation = new JPanel();
        itemInformation.setLayout(new BoxLayout(itemInformation, BoxLayout.Y_AXIS));

        String itemName = "<html>" + item.getName() + "</html>";
        JLabel itemNameLabel = new JLabel(itemName);
        itemNameLabel.setFont(new Font("Arial", Font.PLAIN, 32));
        itemInformation.add(itemNameLabel);

        String itemDesc = "<html><p>" + item.getDescription() + "</p></html>";
        JLabel itemDescLabel = new JLabel(itemDesc);
        itemDescLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        itemInformation.add(itemDescLabel);

        JLabel itemPrice = new JLabel(NumberFormat.getCurrencyInstance(Locale.US).format(is.getPrice()));
        itemPrice.setFont(new Font("Arial", Font.BOLD, 24));
        itemInformation.add(itemPrice);
        JPanel buttonPanel = getButtonPanel(shoppingPanel);
        itemInformation.add(buttonPanel);

        add(itemInformation);
    }

    /**
     * Creates a panel containing two buttons:
     * <ul>
     *     <li>A "Back" button, to return to the browsing panel without adding the item to the cart</li>
     *     <li>An "Add to Cart" button, to add the item to the guest's cart and return to browsing</li>
     * </ul>
     *
     * @param shoppingPanel Instance currently being created
     * @return JPanel containing the two buttons
     */
    private JPanel getButtonPanel(ShoppingMainPanel shoppingPanel) {
        JPanel buttonPanel = new JPanel();
        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> {
            shoppingPanel.getShoppingCL().show(shoppingPanel.getShoppingContent(), "ShoppingMain");
        });
        buttonPanel.add(backBtn);
        JButton addBtn = new JButton("Add to cart");
        addBtn.addActionListener(e -> {
            shoppingPanel.getGuest().getCart().addItem(item);
            JOptionPane.showMessageDialog(null, "Item added to cart!");
            shoppingPanel.getShoppingCL().show(shoppingPanel.getShoppingContent(), "ShoppingMain");
        });
        buttonPanel.add(backBtn);
        buttonPanel.add(addBtn);
        return buttonPanel;
    }
}
