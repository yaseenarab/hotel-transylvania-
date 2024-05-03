package UI.Panels;

import Central.CentralReservations;
import UI.Frames.SelectActivereservationsFrame;
import UI.ShoppingCartPanelHandler;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
     * table showing the active reservations
     */
    private JTable table;

    /**
     * used to get the vlaue of its guest and checkout cost
     */


    /**
     * Frame of table of active reservations
     */
    private SelectActivereservationsFrame activeReservations;


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
     * @return Button allowing for the transition to checkout
     */
    private JButton getCheckoutBtn(ShoppingMainPanel shoppingPanel) {
        JButton checkoutBtn = new JButton("Checkout");
        checkoutBtn.addActionListener(e -> {
            shoppingPanel.setSCOP(new ShoppingCheckoutPanel(shoppingPanel));
            shoppingPanel.getShoppingContent().add(shoppingPanel.getSCOP(), "ShoppingCheckout");
            shoppingPanel.getShoppingCL().show(shoppingPanel.getShoppingContent(), "ShoppingCheckout");

            String cost = ShoppingCartPanelHandler.getSubtotalAsString(shoppingPanel);;
            double addedCost = Double.parseDouble(cost.substring(1,cost.length()));

            activeReservations = new SelectActivereservationsFrame(shoppingPanel,addedCost );
            activeReservations.setVisible(true);

            if(activeReservations.getAvailRooms().getModel().getRowCount() == 0){
                activeReservations.dispatchEvent(new WindowEvent(activeReservations, WindowEvent.WINDOW_CLOSING));

            }
            activeReservations. addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {


                    e.getWindow().dispose();
                }
            });


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
        if (shoppingPanel.getGuest().getCart().getTotalItems() <= 0) {
            removeAllBtn.setEnabled(false);
        }
        topPanel.add(removeAllBtn);
        return topPanel;
    }
}