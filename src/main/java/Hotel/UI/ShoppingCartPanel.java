package Hotel.UI;

import Hotel.ShoppingHandlers.ShoppingCartPanelHandler;

import javax.swing.*;
import java.awt.*;

// Panel to look at contents of cart and / or go to checkout
public class ShoppingCartPanel extends JPanel {
    //private JPanel content;

    public ShoppingCartPanel(ShoppingMainPanel shoppingPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Items in cart - up here due to button that can remove all items below vvv
        JPanel cartItems = new JPanel();
        cartItems.setLayout(new BoxLayout(cartItems, BoxLayout.Y_AXIS));
        var cartItemPanels = ShoppingCartPanelHandler.loadCartItemPanels(shoppingPanel);
        //if () {}
        for (JPanel p : cartItemPanels) {
            cartItems.add(p);
        }

        JLabel subtotal;

        JPanel topPanel = getTopPanel(shoppingPanel, cartItems);
        add(topPanel);
        add(cartItems);

        JPanel subtotalAndCheckout = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel subtotalLabel = new JLabel("Subtotal: ");
        subtotalAndCheckout.add(subtotalLabel);
        gbc.gridx++;

        subtotal = new JLabel(ShoppingCartPanelHandler.getSubtotalAsString(shoppingPanel));
        subtotalAndCheckout.add(subtotal);
        gbc.gridx = 0;
        gbc.gridy++;
        JButton checkoutBtn = new JButton("Checkout");
        checkoutBtn.addActionListener(e -> {
            shoppingPanel.setSCOP(new ShoppingCheckoutPanel(shoppingPanel));
            shoppingPanel.getShoppingContent().add(shoppingPanel.getSCOP(), "ShoppingCheckout");
            shoppingPanel.getShoppingCL().show(shoppingPanel.getShoppingContent(), "ShoppingCheckout");
        });
        if (shoppingPanel.getGuest().getCart().getTotalItems() <= 0) {
            checkoutBtn.setEnabled(false);
        }
        subtotalAndCheckout.add(checkoutBtn);

        add(subtotalAndCheckout);
    }

    private JPanel getTopPanel(ShoppingMainPanel shoppingPanel, JPanel cartItems) {
        JPanel topPanel = new JPanel();
        JLabel cartLabel = new JLabel(shoppingPanel.getGuest().getFirstName() + "'s Cart");
        topPanel.add(cartLabel);
        JButton removeAllBtn = new JButton("Remove All");
        removeAllBtn.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you would like to remove all items from your cart?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                shoppingPanel.getGuest().getCart().removeAllItems();
                cartItems.removeAll();
                cartItems.setEnabled(false);
                cartItems.setVisible(false);
                cartItems.repaint();
            }
        });
        if (shoppingPanel.getGuest().getCart().getTotalItems() <= 0) {
            removeAllBtn.setEnabled(false);
        }
        topPanel.add(removeAllBtn);
        return topPanel;
    }
}
