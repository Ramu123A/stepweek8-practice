# Week 8 Practice — Questions & Answers (Q&A)
**STEP SEM-3 · Category C — Abstraction, Interface, Class vs Interface**

---

## Part 1: "Test Yourself" Questions & Answers (Page 10)

### Q1. Why does `Device` need a constructor at all, given that `Device` itself can never be instantiated?
**Answer:**  
Even though `Device` cannot be instantiated directly with `new Device("D-01")` (`error: Device is abstract; cannot be instantiated`), `Device` still holds real instance fields (`private String deviceId` and `private boolean powerOn`) that every subclass inherits.  
`Device`'s constructor (`public Device(String deviceId)`) exists so that every concrete subclass's constructor (`SmartLight`, `SmartThermostat`, `BasicLamp`) can invoke `super(deviceId)` to properly initialize those inherited fields. An abstract class participates fully in object construction and the inheritance chain—it simply cannot be the final, standalone concrete object at the end of that chain.

---

### Q2. What compiler error do you get if a subclass of an abstract class forgets to implement one of its abstract methods — and what does that error actually tell you?
**Answer:**  
If a concrete subclass (e.g., `BrokenDevice`) extends `Device` without implementing `performPrimaryAction()`, the Java compiler produces:
```text
error: BrokenDevice is not abstract and does not override abstract method performPrimaryAction() in Device
```
**What this error tells you:**  
Java strictly enforces that a subclass of an abstract class has only **two legal options**:
1. Implement **every** inherited abstract method with a concrete body matching the exact method signature, **OR**
2. Declare the subclass itself `abstract` (`public abstract class BrokenDevice extends Device`).  
There is no third option; the compiler refuses to build a concrete class that leaves an inherited promise unfulfilled.

---

### Q3. Why can `SmartDoorLock` implement `Remoteable` without extending `Device` at all?
**Answer:**  
Because `Remoteable` is an **interface**, not a class. An interface expresses a pure **CAN-DO** capability (`void connectToApp(String appId);`) with no requirement on what the class fundamentally **IS** (its class ancestry).  
`SmartDoorLock` is a security fixture rather than a standard appliance `Device`, so forcing it to extend `Device` would create an artificial hierarchy. By implementing `Remoteable`, `SmartDoorLock` fulfills the `connectToApp(...)` contract independently of any parent class and can be passed into `connectAllToApp(Remoteable[] devices, String appId)` alongside `SmartLight` and `SmartThermostat`.

---

### Q4. Give one concrete reason Java allows a class to implement many interfaces, but never allows it to extend more than one class.
**Answer:**  
Java prevents extending multiple classes to avoid the **Diamond Problem (state and implementation ambiguity)**:
- If a class could extend two abstract or concrete classes that both defined an instance field with the same name (e.g., `boolean powerOn` with different semantics) or two different concrete method bodies with the same signature, the compiler would have no principled way to decide which field state or method implementation to inherit.
- Interfaces traditionally carry **no instance state** and **no competing method implementations** (only method signatures / pure contracts). If a class implements multiple interfaces (`implements Remoteable, Schedulable, EnergyMonitorable`), there is no conflicting state or competing parent implementation—the implementing class itself provides the single, unambiguous implementation.

---

### Q5. What does it mean for an interface's fields to be "implicitly `public static final`" — what would happen if you tried to reassign one?
**Answer:**  
Any field declared inside an interface is automatically treated by the Java compiler as `public`, `static`, and `final`, even if you omit those keywords:
- **`public`**: Accessible everywhere.
- **`static`**: Belongs to the interface itself rather than to any individual object instance (shared across all implementers, not per-object state).
- **`final`**: Its value is a constant fixed at compile/initialization time and can never be modified.

If you attempt to reassign an interface field anywhere in your code, the code fails to compile with the error:
```text
error: cannot assign a value to static final variable <variableName>
```

---

### Q6. In your own words, what's the real difference between IS-A and CAN-DO — not the definitions, but why the distinction actually matters when designing a class?
**Answer:**  
- **IS-A (`extends`)** defines an object's **fundamental, permanent, singular identity** (what family it belongs to and what core state/behavior it inherently shares—e.g., a `SmartThermostat` IS-A `Device`).
- **CAN-DO (`implements`)** defines **optional, modular capabilities** layered on top of an object regardless of its identity (what actions it can perform—e.g., `Remoteable`, `Schedulable`, `EnergyMonitorable`).

**Why it matters in design:**  
Separating identity (IS-A) from capabilities (CAN-DO) keeps a software architecture flexible as requirements evolve. If a new capability like `VoiceControllable` is introduced later, you can create an interface and attach it *only* to the specific classes that support voice control (even across unrelated class trees like `SmartLight` and `SmartDoorLock`) without polluting the base `Device` class or forcing simpler classes like `BasicLamp` to implement dummy/unsupported methods.

---

### Q7. `BasicLamp` extends `Device` but implements no interfaces at all. Is it any less a `Device` than `SmartLight`? Why or why not?
**Answer:**  
**No, `BasicLamp` is not any less a `Device` than `SmartLight`.**  
`BasicLamp` extends `Device`, initializes its `deviceId` via `super(deviceId)`, inherits `turnOn()` and `turnOff()`, and provides a complete concrete implementation of `performPrimaryAction()`. Its **IS-A** `Device` identity is 100% complete, and it can be stored in any `Device[]` collection and operated as a `Device`.  
The fact that it implements no interfaces (`Remoteable`, `Schedulable`, etc.) simply means it opted out of optional **CAN-DO** capabilities—proving that an IS-A relationship never obligates a class to adopt extra CAN-DO interfaces.

---

### Q8. You're modeling a system with five closely related classes that share three fields and two methods, but each needs one method to behave differently. Would you reach for an abstract class or an interface — and why?
**Answer:**  
You should reach for an **Abstract Class**.  
**Why:**
1. **Shared State (Three Instance Fields):** Interfaces cannot hold mutable per-object instance fields (only `public static final` constants). An abstract class can hold the three `private`/`protected` instance fields and initialize them once via a shared constructor.
2. **Shared Code (Two Concrete Methods):** The two identical methods can be written once as concrete methods in the abstract class so all five subclasses inherit them without code duplication.
3. **Forced Specialization (One Differing Method):** The one method that behaves differently in each class can be declared `abstract` in the base class, forcing each of the five subclasses to provide its own specialized implementation.
4. **Genuine IS-A Relationship:** All five classes are closely related types belonging to the same family.

---

## Part 2: "Pause & Think" Questions & Answers (Sections 1–4)

### Section 1 — Pause & Think (Page 2)
> **`Device` has a real constructor, even though it can never be instantiated directly with `new Device(...)`. What is that constructor actually for, if not for building a `Device` object on its own?**

**Answer:**  
The constructor in `Device` exists to initialize the private state (`deviceId` and `powerOn`) declared inside `Device` whenever a concrete subclass is instantiated. Every subclass constructor chains to `super(deviceId)` as its first step, delegating the setup of the shared `Device` fields to `Device`'s constructor.

---

### Section 2 — Pause & Think (Page 4)
> **`connectAllToApp(...)` accepts a `Remoteable[]`, not a `Device[]`. What kinds of objects could legally be placed into that array — only `Devices`, or something broader?**

**Answer:**  
Something much broader: **any object whose class implements the `Remoteable` interface**, completely regardless of its class hierarchy. It can hold `Device` subclasses that implement `Remoteable` (`SmartLight`, `SmartThermostat`) as well as non-`Device` classes like `SmartDoorLock` (or future classes like `SmartGarageDoor` or `SecurityCamera`), as long as they fulfill the `Remoteable` contract. Conversely, a `Device` that does *not* implement `Remoteable` (such as `BasicLamp`) cannot be placed in a `Remoteable[]` array.

---

### Section 3 — Pause & Think (Page 6)
> **`SmartLight` and `SmartThermostat` both extend `Device` and both implement `Remoteable` and `Schedulable`. If Java allowed a class to extend two abstract classes the same way it allows implementing two interfaces, what genuine ambiguity would that create that interfaces manage to avoid?**

**Answer:**  
If two parent abstract classes both declared an instance field with the same name (such as `powerOn`) with different meanings, or both provided concrete methods with identical signatures (`turnOn()`) that executed conflicting logic, a subclass extending both would inherit two competing states and implementations. The compiler would have no deterministic rule to pick which parent's field or method body to execute. Interfaces avoid this ambiguity because they carry no instance fields and traditionally only specify method signatures without competing implementations.

---

### Section 4 — Pause & Think (Page 7)
> **A `SmartThermostat` IS-A `Device` and separately CAN-DO `EnergyMonitorable`. Could a future `SmartPlug` also CAN-DO `EnergyMonitorable` without ever being a `Device` at all — the same way `SmartDoorLock` CAN-DO `Remoteable` without being a `Device`? What does that tell you about how independent these two relationships really are?**

**Answer:**  
**Yes!** A future `SmartPlug` class could implement `EnergyMonitorable` without extending `Device` at all. This demonstrates that **IS-A (class inheritance) and CAN-DO (interface implementation) are orthogonal and completely independent relationships**:
- An object can have the IS-A identity without any CAN-DO capabilities (`BasicLamp`).
- An object can have CAN-DO capabilities without the `Device` IS-A identity (`SmartDoorLock`, `SmartPlug`).
- An object can combine the IS-A identity with any combination of CAN-DO capabilities (`SmartLight`, `SmartThermostat`).
