## Main Changes
***IMPORTANT! `GuestHomeFrame` and `ReserveRoomPanel` are not fully functional!*** 

- `GuestHomePanel` renamed to `GuetHomeFrame`
- `GuestHomeFrame` is now an extension of `JFrame`, independent from `LoginFrame` save for the two classes being able to open the other's frame
- **Need to implement `CardLayout` in order to switch to other panes (`ReserveRoomPanel` & editing reservations)**
### `LoginFrame`
- `ActionPerformed`: On a successful login, the method now creates a new `GuestHomeFrame` instance and closes the current `LoginFrame`
