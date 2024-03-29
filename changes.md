## Main Changes
- Added `GuestHomePanel`, which does the following:
    - Creates a content panel that greets the guest and has two options:
        - Edit reservations (not implemented, `ActionListener` ready to accept logic)
        - Create reservation, which repaints the frame's content with a `ReserveRoomPanel` instance
## Changes to Other Classes
- To allow for the functionality to go between separate panels in the same frame, I had to make small edits to the `Login` functions and the `ReserveRoomPanel` class
    - ***IMPORTANT: `Login` changes are outdated and will be updated in future pull requests***
### `Login` Changes
- Instead of a `JFrame`, it has been adapted into a `JPanel` contained in the main `JFrame`
    - This `JPanel` is removed after a successful login and is replaced by a `GuestHomePanel` instance
- `Login` components have been rearranged with a `GridBagLayout` manager
### `ReserveRoomPanel` Changes
- The constructor now takes two parameters, both related to going back to the Guest's home page:
    - `JPanel mainPanel`, the content panel for the main `JFrame`, to update the content of `mainPanel` to `GuestHomePanel`
    - `String guestFirstName`, to display the correct name for `GuestHomePanel`'s welcome label
- Added an exit button, `exitButton`
    - `reserveButton` and `exitButton` are packed into a panel to fit both of them in the `ReserveRoomPanel`'s `BorderLayout` 
