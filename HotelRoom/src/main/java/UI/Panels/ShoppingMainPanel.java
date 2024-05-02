package UI.Panels;

import AccountService.Guest;
import UI.Frames.Guest.GuestHomeFrame;
import UI.ShoppingMainPanelHandler;
import ShoppingService.ItemSpec;
import Utilities.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Logger;

// When shopping section is first entered
// Avenue to cart, items and checkout, or back to home

/**
 * Class serving as the main panel of the shopping system. Any transitions to other shopping panels are handled within
 * this panel through it's CardLayout member, shoppingCL. Aside from serving as the master panel, it also contains
 * the initial menu users guests will see when entering the shopping menu, the available items they can purchase and browse.
 * @author Rafe Loya
 */
public class ShoppingMainPanel extends JPanel {
    /**
     * Logger documenting information regarding operations of ShoppingMainPanel
     */
    private static final Logger SMP_Logger
            = Logger.getLogger(ShoppingMainPanel.class.getName());

    /**
     * Items to display per row when browsing
     */
    private static final int itemsPerRow = 3;

    /**
     * Width of hotel logo image
     */
    private static final int hotelLogoX = 75;

    /**
     * Height of hotel logo image
     */
    private static final int hotelLogoY = 34;

    /**
     * Dimensions of images to display on the browsing section of shop
     */
    private static final int imageXY = 200;

    /**
     * Header, present in all forms of "Shopping-" panels. Allows for transition between ShoppingMainPanel content,
     * ShoppingItemPanel content, and ShoppingCheckout content, as well as going to the guest's home page
     */
    private static JPanel header;

    /**
     * Interactive content of ShoppingMainPanel / ShoppingItemPanel / ShoppingCheckoutPanel
     */
    private static JPanel shoppingContent;

    /**
<<<<<<< HEAD
     * JPanel for browsing items on the main panel of the Shopping system
=======
     *
>>>>>>> editRooms
     */
    private static JPanel SMP;

    /**
     * ShoppingCartPanel instance, generated when appropriate button in header is clicked
     */
    private static JPanel SCP;

    /**
     * ShoppingItemPanel instance, generated when an itemPanel on ShoppingMainPanel is clicked
     */
    private static JPanel SIP;

    /**
     * ShoppingCheckoutPanel instance, generated when appropriate button in header is clicked
     */
    private static JPanel SCOP;

    /**
     * sub-container inside content JPanel, contains all itemPanels user can browse
     */
    private static JPanel itemContainers;

    /**
     * sub-JPanel inside header JPanel, contains search toolbar and button to activate search
     */
    private static JPanel searchPanel;

    /**
     * Image of hotel logo, contained within header
     */
    private static JLabel hotelLogoLabel;

    /**
     * Button to return user to guest's home page, contained within header
     */
    private static JButton homeBtn;

    /**
     * Button to generate a ShoppingCartPanel instance & to load that JPanel
     * inside ShoppingMainPanel's content JPanel. Contained within header
     */
    private static JButton cartBtn;

    /**
     * Button to initiate search on browsable items. Contained in searchPanel, contained in header
     */
    private static JButton searchBtn;

    /**
     * Text field to enter string / substring to search for within browsable items.
     * Contained in searchPanel, contained in header
     */
    private static JTextField searchBar;

    /**
     * CardLayout responsible for updating content JPanel when transitioning between Shopping UI JPanels.
     */
    public static CardLayout shoppingCL;
    
    /**
     * Information of guest in shopping system
     */
    private static Guest guest;

    /**
     * @return Guest information
     */
    public Guest getGuest() { return guest; }

    /**
     * @return CardLayout that controls transition between ShoppingMainPanel,
     *         ShoppingCartPanel, ShoppingCheckoutPanel, and ShoppingItemPanel
     */
    public CardLayout getShoppingCL() {
        return shoppingCL;
    }

    /**
     * @return Main JPanel that contains content of ShoppingMainPanel,
     *         ShoppingCartPanel, ShoppingCheckoutPanel, and ShoppingItemPanel
     */
    public JPanel getShoppingContent() { return shoppingContent; }

    /**
     * Intended to be used in JPanels that transition into the ShoppingCheckoutPanel
     *
     * @return JPanel containing Panel for ShoppingCheckoutPanel
     */
    public JPanel getSCOP() { return SCOP; }

    /**
     * Initializes the SCOP data member with a ShoppingCheckoutPanel instance.
     *
     * @param scop ShoppingCheckoutPanel instance to add to ShoppingMainPanel
     */
    public void setSCOP(ShoppingCheckoutPanel scop) { SCOP = scop; }

    /**
     * Creates the header present in all shopping sub-panels, and the ShoppingMainPanel
     * that allows for browsing (SMP, which is added to shoppingContent to display)
     *
     * @param frame Frame containing all guest capabilities
     */
    public ShoppingMainPanel(GuestHomeFrame frame) {
        // Container initialization
        guest = frame.getGuest();
        try {
            ShoppingMainPanelHandler.initGuestCart(guest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        setLayout(new BorderLayout());
        shoppingContent = new JPanel();
        SMP = new JPanel();

        // Header initialization
        header = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Sub-content initialization
        shoppingCL = new CardLayout();
        shoppingContent = new JPanel(shoppingCL);

        // Header - Logo
        Image hotelLogo = null;
        try {
            hotelLogo = Utilities.generateScaledImage(this, "Hotel_Transylvania_logo.png", hotelLogoX, hotelLogoY);
        } catch (IOException e) {
            SMP_Logger.warning("Could not load hotel logo");
        }
        hotelLogoLabel = new JLabel(new ImageIcon(hotelLogo));
        SMP_Logger.info("Successfully loaded hotel logo");
        hotelLogoLabel.setMinimumSize(new Dimension(80, 80));
        header.add(hotelLogoLabel);

        // Header - Home button
        homeBtn = new JButton("Home");
        homeBtn.addActionListener(e -> {
            ShoppingMainPanelHandler.clearGuestCart(guest);
            frame.cl.show(frame.container, "Home");
        });
        header.add(homeBtn);

        // Header - Search panel (search bar & button)
        searchBar = new JTextField();
        //searchBar.setMinimumSize(new Dimension(100, 30));
        searchBar.setColumns(20);
        //header.add(search);
        searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(searchBar);

        Image searchIcon = null;
        try {
            searchIcon = Utilities.generateScaledImage(this, "search-icon.png", 20, 20);
        } catch (IOException e) {
            SMP_Logger.warning("Could not load search icon");
        }
        searchBtn = new JButton(new ImageIcon(searchIcon));
        SMP_Logger.info("Successfully loaded search icon");
        //searchBtn.setMinimumSize(new Dimension(50, 50));
        searchPanel.add(searchBtn);
        header.add(searchPanel);

        // Header - Cart button
        cartBtn = new JButton("Cart");
        cartBtn.addActionListener(e -> {
            SCP = new ShoppingCartPanel(this);
            shoppingContent.add(SCP, "Cart");
            shoppingCL.show(shoppingContent, "Cart");
        });
        header.add(cartBtn);

        // Container - Adding header
        header.setSize(new Dimension(720, 80));
        header.setBackground(new Color(21, 71, 52));
        add(header, BorderLayout.NORTH);

        // itemContainer & itemPanel initialization(s)
        ArrayList<ItemSpec> isl = ShoppingMainPanelHandler.loadItems();
        ArrayList<JPanel> itemPanels = new ArrayList<>();
        for (var is : isl) {
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));

            JLabel itemTitle = new JLabel(is.getName());

            JLabel itemPic;
            try {
                BufferedImage itemBU = Utilities.generateImage(this, is.getImageURL());
                itemPic = new JLabel(new ImageIcon(itemBU.getScaledInstance(
                        Math.min(imageXY, itemBU.getWidth()),
                        Math.min(imageXY, itemBU.getHeight()), Image.SCALE_SMOOTH
                )));
                SMP_Logger.info("Successfully loaded image of item : " + is.getName());
            } catch (IOException e) {
                SMP_Logger.severe("Could not load image of item : " + is.getName());
                itemPic = new JLabel(new ImageIcon());
            }
            itemPic.setMinimumSize(new Dimension(180, 100));

            //JLabel itemPrice = new JLabel(is.getPrice().toString());
            JLabel itemPrice = new JLabel(NumberFormat.getCurrencyInstance(Locale.US).format(is.getPrice()));

            itemPanel.add(itemTitle);
            itemPanel.add(itemPic);
            itemPanel.add(itemPrice);
            itemPanel.setSize(new Dimension(200, 200));
            itemPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // NEED TO VERIFY LATER!!!
                    //ShoppingMainPanel parentContainer = (ShoppingMainPanel) e.getSource();
                    SIP = new ShoppingItemPanel(ShoppingMainPanel.this, is);
                    //parentContainer.add(SIP, "SIP");
                    shoppingContent.add(SIP, "ShoppingItem");
                    shoppingCL.show(shoppingContent, "ShoppingItem");
                }
            });

            itemPanels.add(itemPanel);
        }

        itemContainers = new JPanel(new GridBagLayout());
        itemContainers.setSize(new Dimension(720, 1000));
        GridBagConstraints gbc =  new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        for (JPanel itemPanel : itemPanels) {
            itemContainers.add(itemPanel, gbc);
            gbc.gridx = (gbc.gridx + 1) % itemsPerRow;
            if (gbc.gridx == 0) { ++gbc.gridy; }
        }
        SMP.add(itemContainers, BorderLayout.CENTER);
        SMP.setSize(new Dimension(720, 1000));

        add(shoppingContent, BorderLayout.CENTER);
        shoppingContent.add(new JScrollPane(SMP), "ShoppingMain");
        shoppingCL.show(shoppingContent, "ShoppingMain");
    }
}
