# CC: Sable
*CC: Tweaked addon for Sable!*

**CC: Sable** is a CC: Tweaked addon for the Sable backend of Create: Simulated!
Currently, it provides the Sub-Level API (`sublevel`) for all in-game computers! Its methods can only be properly called when the computer is on a Sub-Level (physics object). It also provides the Aerodynamics API (`aero`,`aerodynamics`) for all in-game computers to access dimension-specific physics information!

This mod also comes bundled with the [CC: Advanced Math](https://techtastic.github.io/Advanced-Math/) datapack for extra math utilities, namely the `quaternion` module for handling rotations in 3D space. This is used to convert the orientation of Sub-Levels into a more usable format for Lua.

---

### Sub-Level API
#### getUniqueId(): string
This method returns the Universally Unique Identifier (UUID) of the Sub-Level that the computer is currently on.
#### getName(): string
This method returns the name of the Sub-Level that the computer is currently on.
#### setName(newName:string)
This method sets the name of the Sub-Level that the computer is currently on.
#### getLogicalPose(): table
This method returns the logical pose of the Sub-Level that the computer is currently on. The logical pose is a table containing the position and orientation of the Sub-Level in the world. The `position`, `scale`, and `rotationPoint` fields are automatically converted to Lua `vector`s. The `orientation` field is also automatically converted to the [CC: Advanced Math `quaternion`](https://techtastic.github.io/Advanced-Math/modules/quaternion.html).
#### getLastPose(): table
This method returns the last known pose of the Sub-Level that the computer is currently on. The same changes to `getLogicalPose` are also made here.
#### getVelocity(): vector
This method returns the global velocity of the Sub-Level that the computer is currently on.
#### getLinearVelocity(): vector
This method returns the linear velocity of the Sub-Level that the computer is currently on.
#### getAngularVelocity(): vector
This method returns the angular velocity of the Sub-Level that the computer is currently on.
#### getCenterOfMass(): vector
This method returns the center of mass of the Sub-Level that the computer is currently on.
#### getMass(): number
This method returns the mass of the Sub-Level that the computer is currently on.
#### getInverseMass(): number
This method returns the inverse mass of the Sub-Level that the computer is currently on.
#### getInertiaTensor(): number
This method returns the inertia tensor of the Sub-Level that the computer is currently on.
#### getInverseInertiaTensor(): number
This method returns the inverse inertia tensor of the Sub-Level that the computer is currently on.

---

### Aero/Aerodynamics API
#### getAirPressure(position: vector): number
This method returns the air pressure at the given position.
#### getGravity(): vector
This method returns the gravity of the dimension.
#### getMagneticNorth(): vector
This method returns the magnetic north vector of the dimension.
#### getUniversalDrag(): number
This method returns the universal drag constant of the dimension.
#### getRaw(): table
This method returns the raw physics information of the dimension (basically the values assigned by datapack). This is given as a table with `dimension` as a string, `priority` as a number, `gravity` as a vector, `pressure` as a number, `magneticNorth` as a vector, `universalDrag` as a number, and `pressureFunction` as a custom Lua wrapped object (see `LuaBezierResourceFunction`).
#### getDefault(): table
This method returns the default physics information of the dimension. The table is laid out the same as `getRaw`.

---

### LuaBezierResourceFunction
#### getPoints(): table
This method returns an array of points along the bezier curve with `altitude`, `value`, and `slope` as fields for each point.
#### evaluateFunction(altitude: number): number
This method evaluates the function given the altitude along the bezier curve.