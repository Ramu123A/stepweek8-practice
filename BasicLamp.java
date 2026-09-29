/**
 * Section 4: IS-A vs CAN-DO
 *
 * BasicLamp IS-A Device (extends Device), permanently and singularly.
 * It implements no interfaces at all -- it opted out of every extra CAN-DO capability.
 */
public class BasicLamp extends Device { // IS-A Device
    public BasicLamp(String deviceId) {
        super(deviceId);
    }

    @Override
    public void performPrimaryAction() {
        System.out.println(getDeviceId() + " is simply lit, nothing fancy");
    }
    // no "implements" clause at all -- this lamp CAN-DO nothing extra
}
