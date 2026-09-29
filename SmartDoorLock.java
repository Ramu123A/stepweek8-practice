/**
 * Section 2: A Capability That Doesn't Need a Device At All
 *
 * SmartDoorLock is NOT a Device (does not extend Device) -- it is a security fixture
 * with different needs entirely. Yet it implements Remoteable, allowing it to be
 * managed alongside SmartLight and SmartThermostat in any Remoteable[] collection.
 */
public class SmartDoorLock implements Remoteable {
    private String lockId;

    public SmartDoorLock(String lockId) {
        this.lockId = lockId;
    }

    public String getLockId() {
        return lockId;
    }

    @Override
    public void connectToApp(String appId) {
        System.out.println(lockId + " connected to app: " + appId);
    }
}
