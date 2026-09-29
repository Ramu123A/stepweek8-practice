# Week 8 Practice — Abstraction, Interface, Class vs Interface
**STEP SEM-3 · Category C · CodInClub | Powered by BridgeLabz**

This repository contains the complete Java implementation of the **Smart Home Device Ecosystem** and detailed answers to all **Test Yourself** and **Pause & Think** questions from the Week 8 Category C Concept Guide (*Abstraction, Interface, Class vs Interface*).

---

## Table of Contents
1. [Project Structure & Files](#project-structure--files)
2. [Smart Home Ecosystem Architecture](#smart-home-ecosystem-architecture)
3. [Section-by-Section Code Walkthrough](#section-by-section-code-walkthrough)
4. [Whole Smart Home Program Output](#whole-smart-home-program-output)
5. [Questions & Answers (Test Yourself & Pause & Think)](#questions--answers-test-yourself--pause--think)
6. [Compilation & Execution Instructions](#compilation--execution-instructions)

---

## Project Structure & Files

| File | Type | Role in Ecosystem |
| :--- | :--- | :--- |
| [`Device.java`](Device.java) | Abstract Class | Shared IS-A identity for devices (`deviceId`, `powerOn`, `turnOn()`, `turnOff()`, and abstract `performPrimaryAction()`) |
| [`Remoteable.java`](Remoteable.java) | Interface | CAN-DO capability for connecting to a mobile app (`connectToApp(String appId)`) |
| [`Schedulable.java`](Schedulable.java) | Interface | CAN-DO capability for scheduling actions (`scheduleAction(String time)`) |
| [`EnergyMonitorable.java`](EnergyMonitorable.java) | Interface | CAN-DO capability for reporting power draw (`getPowerConsumption()`) |
| [`SmartLight.java`](SmartLight.java) | Concrete Class | `extends Device implements Remoteable, Schedulable` |
| [`SmartThermostat.java`](SmartThermostat.java) | Concrete Class | `extends Device implements Remoteable, Schedulable, EnergyMonitorable` |
| [`BasicLamp.java`](BasicLamp.java) | Concrete Class | `extends Device` (IS-A `Device` with no extra CAN-DO interfaces) |
| [`SmartDoorLock.java`](SmartDoorLock.java) | Concrete Class | `implements Remoteable` (CAN-DO `Remoteable` without being a `Device`) |
| [`HomeHubLogger.java`](HomeHubLogger.java) | Concrete Class | Plain concrete class where no variation is expected |
| [`SmartHomeDemo.java`](SmartHomeDemo.java) | Main Program | Runs the complete Smart Home demonstration from Page 9 |
| [`QnA.md`](QnA.md) | Documentation | Complete answers to all 8 **Test Yourself** questions and 4 **Pause & Think** prompts |

---

## Smart Home Ecosystem Architecture

- **Abstract Class (`Device`) — IS-A Relationship (`extends`):**
  - Holds private instance state (`deviceId`, `powerOn`), a constructor (`Device(String deviceId)`), shared concrete methods (`turnOn()`, `turnOff()`), and an abstract contract (`performPrimaryAction()`).
  - Subclasses: `SmartLight`, `SmartThermostat`, `BasicLamp`.
- **Interfaces (`Remoteable`, `Schedulable`, `EnergyMonitorable`) — CAN-DO Capabilities (`implements`):**
  - Pure capability contracts that can be layered onto any class, whether inside or outside the `Device` hierarchy.
  - `SmartDoorLock` is **not** a `Device`, yet implements `Remoteable` and works seamlessly inside `connectAllToApp(Remoteable[] devices, String appId)`.
- **Plain Concrete Class (`HomeHubLogger`):**
  - Used when there is only one kind of thing and no variation is expected.

---

## Whole Smart Home Program Output

Running [`SmartHomeDemo.java`](SmartHomeDemo.java) executes the full program from Page 9 and prints:

```text
LIGHT-01 is now ON
LIGHT-01 is glowing at 100% brightness
THERMO-01 is now ON
THERMO-01 is regulating temperature to 22.5 degrees
LAMP-01 is now ON
LAMP-01 is simply lit, nothing fancy
Thermostat power draw: 45.5W
LIGHT-01 connected to app: HomeConnect
THERMO-01 connected to app: HomeConnect
LOCK-01 connected to app: HomeConnect
```

---

## Questions & Answers (Test Yourself & Pause & Think)

*(See [`QnA.md`](QnA.md) for the standalone Q&A reference including all "Pause & Think" prompts.)*

### 1. Why does `Device` need a constructor at all, given that `Device` itself can never be instantiated?
`Device` holds real instance fields (`deviceId` and `powerOn`) inherited by every subclass. Its constructor (`public Device(String deviceId)`) exists so every subclass's constructor (`SmartLight`, `SmartThermostat`, `BasicLamp`) can call `super(deviceId)` to initialize that shared state. The abstract class participates fully in construction and inheritance—it simply cannot be instantiated as the final concrete object on its own.

### 2. What compiler error do you get if a subclass of an abstract class forgets to implement one of its abstract methods — and what does that error actually tell you?
Compiler error:
```text
error: BrokenDevice is not abstract and does not override abstract method performPrimaryAction() in Device
```
This tells you that any class extending an abstract class has only two legal choices: either implement **every** inherited abstract method with a matching signature, or declare the subclass itself `abstract`.

### 3. Why can `SmartDoorLock` implement `Remoteable` without extending `Device` at all?
Because `Remoteable` is an interface expressing a pure **CAN-DO** capability (`connectToApp(String appId)`), not a family identity. Any class—regardless of what it extends or whether it shares any ancestry with `Device`—can adopt an interface contract and be passed to methods expecting `Remoteable`.

### 4. Give one concrete reason Java allows a class to implement many interfaces, but never allows it to extend more than one class.
Extending two classes could introduce **conflicting instance state** (e.g., two parent classes defining a `powerOn` field differently) or **competing concrete method implementations** with the same signature, leaving the compiler with no principled way to choose one (the Diamond Problem). Interfaces traditionally have no instance state and no competing implementations, avoiding this ambiguity completely.

### 5. What does it mean for an interface's fields to be "implicitly `public static final`" — what would happen if you tried to reassign one?
Interface fields are shared constants belonging to the interface type (`static`), visible everywhere (`public`), and permanently fixed (`final`), rather than per-object instance state. Attempting to assign a new value causes a compile-time error:
```text
error: cannot assign a value to static final variable
```

### 6. In your own words, what's the real difference between IS-A and CAN-DO — not the definitions, but why the distinction actually matters when designing a class?
**IS-A (`extends`)** defines what an object *fundamentally and permanently is* (singular identity and shared code/state), while **CAN-DO (`implements`)** layers *optional capabilities* onto a class. Separating the two keeps designs flexible: when a new capability (like `VoiceControllable` or `Remoteable`) arises, you can add it only to the classes that need it (even across unrelated class hierarchies like `SmartLight` and `SmartDoorLock`) without altering `Device` or forcing `BasicLamp` to support capabilities it doesn't have.

### 7. `BasicLamp` extends `Device` but implements no interfaces at all. Is it any less a `Device` than `SmartLight`? Why or why not?
No—`BasicLamp` is every bit as much a `Device` as `SmartLight`. It inherits `deviceId`, `powerOn`, `turnOn()`, and `turnOff()`, and implements `performPrimaryAction()`. Its **IS-A** `Device` identity is complete; it simply opted out of optional **CAN-DO** capabilities (`Remoteable`, `Schedulable`, `EnergyMonitorable`), proving IS-A never obligates CAN-DO.

### 8. You're modeling a system with five closely related classes that share three fields and two methods, but each needs one method to behave differently. Would you reach for an abstract class or an interface — and why?
An **Abstract Class**, because the five classes are closely related (IS-A family) and share **real instance state** (three fields) and **real working behavior** (two concrete methods) that can be written once in the abstract base class, while the one differing method can be declared `abstract` so each subclass must provide its own implementation.

---

## Compilation & Execution Instructions

```bash
# Compile all Java files
javac *.java

# Run the complete Smart Home Ecosystem demonstration
java SmartHomeDemo
```
