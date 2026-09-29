/**
 * Wrap-Up: The Whole Smart Home, In One Program
 *
 * Demonstrates every concept from the Week 8 Practice guide:
 * 1. Abstract Classes (Device: shared fields, constructor, turnOn/turnOff, and abstract performPrimaryAction)
 * 2. Interfaces & Multiple Inheritance of Capability (Remoteable, Schedulable, EnergyMonitorable)
 * 3. Abstract Class vs Interface (IS-A identity vs CAN-DO capability across unrelated classes)
 * 4. IS-A without CAN-DO (BasicLamp) and CAN-DO without Device IS-A (SmartDoorLock)
 * 5. Plain Concrete Class (HomeHubLogger)
 */
public class SmartHomeDemo {

    // Works on ANY Remoteable object, regardless of its class hierarchy / ancestry
    public static void connectAllToApp(Remoteable[] devices, String appId) {
        for (Remoteable r : devices) {
            r.connectToApp(appId);
        }
    }

    public static void main(String[] args) {
        // Instantiate the ecosystem components
        SmartLight light = new SmartLight("LIGHT-01");
        SmartThermostat thermostat = new SmartThermostat("THERMO-01", 22.5);
        BasicLamp lamp = new BasicLamp("LAMP-01");
        SmartDoorLock lock = new SmartDoorLock("LOCK-01");

        // Demonstrate shared concrete methods (turnOn) and specialized abstract methods (performPrimaryAction)
        light.turnOn();
        light.performPrimaryAction();

        thermostat.turnOn();
        thermostat.performPrimaryAction();

        lamp.turnOn();
        lamp.performPrimaryAction();

        // Demonstrate EnergyMonitorable interface capability on SmartThermostat
        System.out.println("Thermostat power draw: " + thermostat.getPowerConsumption() + "W");

        // Demonstrate Remoteable interface capability across both Devices and non-Devices (SmartDoorLock)
        Remoteable[] remoteDevices = { light, thermostat, lock }; // lock is NOT a Device -- and that's fine
        connectAllToApp(remoteDevices, "HomeConnect");
    }
}
