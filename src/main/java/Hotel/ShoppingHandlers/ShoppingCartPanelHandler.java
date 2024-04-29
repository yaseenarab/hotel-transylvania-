package Hotel.ShoppingHandlers;

import Hotel.ShoppingService.ItemSpec;
import Hotel.UI.ShoppingMainPanel;
import Hotel.Utilities.Utilities;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.logging.Logger;
import javax.swing.*;

public class ShoppingCartPanelHandler {
    private static final Logger SCPH_Logger
            = Logger.getLogger(ShoppingCartPanelHandler.class.getName());

    private static final int imageDimension = 50;

    private static BigDecimal processSubtotal(ShoppingMainPanel smp) {
        BigDecimal subtotal = BigDecimal.valueOf(0.00);
        for (Map.Entry<ItemSpec, Integer> entry : smp.getGuest().getCart().getEntries()) {
            var item = entry.getKey().getPrice();
            var quantity = BigDecimal.valueOf(entry.getValue());
            // subtotal += item price * item quantity
            subtotal = subtotal.add(item.multiply(quantity));
        }

        return subtotal;
    }

    public static BigDecimal getSubtotalAsBigDecimal(ShoppingMainPanel smp) {
        return processSubtotal(smp);
    }

    public static String getSubtotalAsString(ShoppingMainPanel smp) {
        return processSubtotal(smp).toString();
    }

    public static LinkedHashSet<JPanel> loadCartItemPanels(ShoppingMainPanel smp) {
        LinkedHashSet<JPanel> cartItems = new LinkedHashSet<>();

        for (Map.Entry<ItemSpec, Integer> entry : smp.getGuest().getCart().getEntries()) {
            JPanel itemPanel = new JPanel();
            var item = entry.getKey();
            var quantity = entry.getValue();

            JLabel itemImgLabel;
            try {
                BufferedImage itemBI = Utilities.generateImage(smp, item.getImageURL());
                Image itemImage = itemBI.getScaledInstance(
                        Math.min(imageDimension, itemBI.getWidth()),
                        Math.min(imageDimension, itemBI.getHeight()),
                        Image.SCALE_SMOOTH);
                itemImgLabel = new JLabel(new ImageIcon(itemImage));
                itemPanel.add(itemImgLabel);
            } catch (IOException e) {
                SCPH_Logger.severe("Unable to load image for item : " + item.getName());
            }

            JLabel itemName = new JLabel(item.getName());
            itemPanel.add(itemName);

            // add / items in cart / subtract button panel
            JPanel itemEditQuantity = new JPanel();
            JLabel itemQuantity = new JLabel(String.valueOf(quantity));
            JButton addMoreBtn = new JButton("+");
            addMoreBtn.addActionListener(e ->{
                // CHECK LATER
                itemQuantity.setText(String.valueOf(quantity + 1));
                smp.getGuest().getCart().addItem(item);
            });
            JButton subtractMoreBtn = new JButton("-");
            subtractMoreBtn.addActionListener(e -> {
                smp.getGuest().getCart().removeItem(item);
                if (!smp.getGuest().getCart().containsKey(item)) {
                    // CHECK LATER
                    itemPanel.removeAll();
                    itemPanel.setEnabled(false);
                    itemPanel.setVisible(false);
                    itemPanel.repaint();
                } else {
                    itemQuantity.setText(String.valueOf(quantity - 1));
                }
            });
            itemEditQuantity.add(subtractMoreBtn);
            itemEditQuantity.add(itemQuantity);
            itemEditQuantity.add(addMoreBtn);
            itemPanel.add(itemEditQuantity);

            // price / remove panel
            JPanel priceAndRemovePanel = new JPanel();
            priceAndRemovePanel.setLayout(new BoxLayout(priceAndRemovePanel, BoxLayout.Y_AXIS));
            JLabel priceLabel = new JLabel(item.getPrice().toString());
            priceLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            priceAndRemovePanel.add(priceLabel);
            JLabel removeItemLabel = new JLabel("Remove");
            removeItemLabel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int choice = JOptionPane.showConfirmDialog(null, "Are you sure you would like to remove this item from your cart?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        smp.getGuest().getCart().remove(item);
                        itemPanel.removeAll();
                        itemPanel.setEnabled(false);
                        itemPanel.setVisible(false);
                        itemPanel.repaint();
                    }
                }
            });
            priceAndRemovePanel.add(removeItemLabel);
            itemPanel.add(priceAndRemovePanel);

            cartItems.add(itemPanel);
        }

        return cartItems;
    }
}
