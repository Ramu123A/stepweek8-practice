/**
 * Section 2: Interfaces — Complete Abstraction
 *
 * Remoteable expresses the pure CAN-DO capability of connecting to a mobile app.
 * Any class (whether in the Device hierarchy or completely unrelated like SmartDoorLock)
 * can implement this contract.
 */
public interface Remoteable {
    void connectToApp(String appId); // no body -- a pure promise
}
