/**
 * Section 1: Abstract Classes — Abstract Methods and Rules
 *
 * Device represents the shared base class for all smart home devices.
 * It mixes concrete state/behavior (deviceId, powerOn, turnOn(), turnOff())
 * with an abstract contract (performPrimaryAction()) that every concrete
 * subclass must implement.
 */
public abstract class Device {
    private String deviceId;
    private boolean powerOn;

    public Device(String deviceId) {
        this.deviceId = deviceId;
        this.powerOn = false;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public boolean isPowerOn() {
        return powerOn;
    }

    // Concrete method -- shared by every device, written once
    public void turnOn() {
        powerOn = true;
        System.out.println(deviceId + " is now ON");
    }

    // Concrete method -- shared by every device, written once
    public void turnOff() {
        powerOn = false;
        System.out.println(deviceId + " is now OFF");
    }

    // Abstract method -- every concrete subclass MUST define this
    public abstract void performPrimaryAction();
}
