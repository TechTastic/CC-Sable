# CC: Sable
*CC: Tweaked addon for Sable!*

**CC: Sable** is a CC: Tweaked addon for the Sable backend of Create: Simulated!
Currently, it provides the Sub-Level API (`sublevel`) for all in-game computers! Its methods can only be properly called when the computer is on a Sub-Level (physics object).

This mod also comes bundled with the [CC: Advanced Math](https://techtastic.github.io/Advanced-Math/) datapack for extra math utilities, namely the `quaternion` module for handling rotations in 3D space. This is used to convert the orientation of Sub-Levels into a more usable format for Lua.

---

### Sub-Level API
#### isInPlotGrid(): boolean
This method returns `true` if the computer is currently on a Sub-Level that is part of the plot grid, and `false` otherwise.
#### getUniqueId(): string
This method returns the Universally Unique Identifier (UUID) of the Sub-Level that the computer is currently on. 
#### getName(): string
This method returns the name of the Sub-Level that the computer is currently on.
#### getLogicalPose(): table
This method returns the logical pose of the Sub-Level that the computer is currently on. The logical pose is a table containing the position and orientation of the Sub-Level in the world. The `position`, `scale`, and `rotationPoint` fields are automatically converted to Lua `vector`s. The `orientation` field is also automatically converted to the [CC: Advanced Math `quaternion`](https://techtastic.github.io/Advanced-Math/modules/quaternion.html).
#### getLastPose(): table
This method returns the last known pose of the Sub-Level that the computer is currently on. The same changes to `getLogicalPose` are also made here.
#### getVelocity(): table
This method returns the global velocity of the Sub-Level that the computer is currently on as a Lua `vector`.