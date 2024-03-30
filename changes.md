## Main Changes
- Removed `HotelReservationPanel`
### `GuestHomeFrame`
- Added new static variables:
    - `private GuestProfile guest`: for debug purposes (`ReserveRoomPanel` requirement)
    - `public CardLayout cl`: handles multiple panes within same frame
        - access modifier is `public` due to needing it for updates (`ReserveRoomPanel`)
    - `public JPanel container`: "master" panel that holds all content of `GuestHomeFrame` and is able to switch to change displayed panes based on user action
        - `public` access modifier for same reasons above (`ReserveRoomPanel`)
    - `private JPanel homePanel`: panel for the guest's home screen
    - `private JPanel reservePanel`: panel for room reservations (`ReserveRoomPanel` instance)
    - `private JPanel editPanel`: panel for editing reservations (***NOT CURRENTLY IMPLEMENTED!***)
    - `private JButton EditReservationBtn / ReserveRoomBtn / LogoutBtn`: buttons to change current panel displayed
- Instead of new `JFrame` instances, the separate panels are displayed in one frame using a `CardLayoutManager`
### `LoginFrame`
- `actionPerformed`
    - on successful login, a `GuestHomeFrame` instance is created with `username` and `password` as a parameter
    - ***IMPORTANT: `LoginFrame` needs to be updated to handle guests and / or other profile types in the future!***
### `ReserveRoomPanel`
- Constructor
    - Parameters changed to take a `GuestHomeFrame` instance as a parameter for updating itself
    - New button added `exitButton`, to return to the guest's home page
      - the new `exitButton` and preexisting `reserveButton` are bundled into a new `JPanel` to keep both of them at the bottom of the `ReserveRoomPanel`

