/**
 * SmartLight IS-A Device (extends Device) and CAN-DO Remoteable and Schedulable
 * (implements Remoteable, Schedulable).
 */
public class SmartLight extends Device implements Remoteable, Schedulable {
    private int brightness;

    public SmartLight(String deviceId) {
        this(deviceId, 100);
    }

    public SmartLight(String deviceId, int brightness) {
        super(deviceId);
        this.brightness = brightness;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    @Override
    public void performPrimaryAction() {
        System.out.println(getDeviceId() + " is glowing at " + brightness + "% brightness");
    }

    @Override
    public void connectToApp(String appId) {
        System.out.println(getDeviceId() + " connected to app: " + appId);
    }

    @Override
    public void scheduleAction(String time) {
        System.out.println(getDeviceId() + " scheduled to turn on at " + time);
    }
}
