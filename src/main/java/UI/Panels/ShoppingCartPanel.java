package UI.Panels;

import UI.ShoppingCartPanelHandler;

import javax.swing.*;
import java.awt.*;

// Panel to look at contents of cart and / or go to checkout

/**
 * Class to display user's items in their cart, and optionally transition to the checkout process
 *
 * @author Rafe Loya
 */
public class ShoppingCartPanel extends JPanel {
    //private JPanel content;
    /**
     * subtotal displayed to user
     */
    private static JLabel subtotal;

    /**
     * Constructor initializing the ShoppingCartPanel inside ShoppingMainPanel
     *
     * @param shoppingPanel Main shopping panel, containing guest information
     */
    public ShoppingCartPanel(ShoppingMainPanel shoppingPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Items in cart - up here due to button that can remove all items below vvv
        JPanel cartItems = new JPanel();
        cartItems.setLayout(new BoxLayout(cartItems, BoxLayout.Y_AXIS));
        var cartItemPanels = ShoppingCartPanelHandler.loadCartItemPanels(shoppingPanel, this);
        for (JPanel p : cartItemPanels) {
            cartItems.add(p);
        }

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
        JButton checkoutBtn = getCheckoutBtn(shoppingPanel);
        subtotalAndCheckout.add(checkoutBtn);

        add(subtotalAndCheckout);
    }

    /**
     * @return JLabel representing subtotal
     */
    public JLabel getSubtotal() { return subtotal; }

    /**
     * @param shoppingPanel Main shopping panel containing the ShoppingCartPanel
     * @return Button allowing for the transition to check out
     */
    private JButton getCheckoutBtn(ShoppingMainPanel shoppingPanel) {
        JButton checkoutBtn = new JButton("Checkout");
        checkoutBtn.addActionListener(e -> {
            shoppingPanel.setSCOP(new ShoppingCheckoutPanel(shoppingPanel));
            shoppingPanel.getShoppingContent().add(shoppingPanel.getSCOP(), "ShoppingCheckout");
            shoppingPanel.getShoppingCL().show(shoppingPanel.getShoppingContent(), "ShoppingCheckout");
        });
        if (shoppingPanel.getGuest().getCart().getTotalItems() <= 0) {
            checkoutBtn.setEnabled(false);
        }
        return checkoutBtn;
    }

    /**
     * Creates a JPanel storing a label displaying the guest's name,
     * along with a button to remove all items in their cart
     *
     * @param shoppingPanel Main shopping panel, containing ShoppingCartPanel
     * @param cartItems     JPanel containing "line item" JPanels
     * @return JPanel with "{guest name}'s Cart" and remove all button
     */
    private JPanel getTopPanel(ShoppingMainPanel shoppingPanel, JPanel cartItems) {
        JPanel topPanel = new JPanel();
        JLabel cartLabel = new JLabel(shoppingPanel.getGuest().getFirstName() + "'s Cart");
        topPanel.add(cartLabel);
        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> shoppingPanel.getShoppingCL().show(shoppingPanel.getShoppingContent(), "ShoppingMain"));
        topPanel.add(backBtn);
        JButton removeAllBtn = getRemoveAllBtn(shoppingPanel, cartItems);
        if (shoppingPanel.getGuest().getCart().getTotalItems() <= 0) {
            removeAllBtn.setEnabled(false);
        }
        topPanel.add(removeAllBtn);
        return topPanel;
    }

    private JButton getRemoveAllBtn(ShoppingMainPanel shoppingPanel, JPanel cartItems) {
        JButton removeAllBtn = new JButton("Remove All");
        removeAllBtn.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you would like to remove all items from your cart?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                shoppingPanel.getGuest().getCart().removeAllItems();
                cartItems.removeAll();
                cartItems.setEnabled(false);
                cartItems.setVisible(false);
                subtotal.setText("$0.00");
                cartItems.repaint();
            }
        });
        return removeAllBtn;
    }
}
