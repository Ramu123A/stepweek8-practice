/**
 * SmartThermostat extends ONE abstract class (Device) and simultaneously implements
 * THREE interfaces (Remoteable, Schedulable, EnergyMonitorable).
 * This demonstrates multiple inheritance of capability in Java.
 */
public class SmartThermostat extends Device implements Remoteable, Schedulable, EnergyMonitorable {
    private double targetTemperature;
    private double powerConsumptionWatts;

    public SmartThermostat(String deviceId, double targetTemperature) {
        this(deviceId, targetTemperature, 45.5);
    }

    public SmartThermostat(String deviceId, double targetTemperature, double powerConsumptionWatts) {
        super(deviceId);
        this.targetTemperature = targetTemperature;
        this.powerConsumptionWatts = powerConsumptionWatts;
    }

    public double getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(double targetTemperature) {
        this.targetTemperature = targetTemperature;
    }

    @Override
    public void performPrimaryAction() {
        System.out.println(getDeviceId() + " is regulating temperature to " + targetTemperature + " degrees");
    }

    @Override
    public void connectToApp(String appId) {
        System.out.println(getDeviceId() + " connected to app: " + appId);
    }

    @Override
    public void scheduleAction(String time) {
        System.out.println(getDeviceId() + " scheduled temperature adjustment at " + time);
    }

    @Override
    public double getPowerConsumption() {
        return powerConsumptionWatts;
    }
}
