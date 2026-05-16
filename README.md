# CC: Sable
*CC: Tweaked addon for Sable!*

**CC: Sable** is a CC: Tweaked addon for the Sable backend of Create: Simulated!
Currently, it provides the Sub-Level API (`sublevel`) for all in-game computers! Its methods can only be properly called when the computer is on a Sub-Level (physics object). It also provides the Aerodynamics API (`aero`,`aerodynamics`) for all in-game computers to access dimension-specific physics information!

This mod also comes bundled with the [CC: Advanced Math](https://techtastic.github.io/Advanced-Math/) datapack for extra math utilities, namely the `quaternion` module for handling rotations in 3D space. This is used to convert the orientation of Sub-Levels into a more usable format for Lua.

---

## Extra References

### LuaBezierResourceFunction
This object is gotten from the tables returned by `aero.getRaw` and `aero.getDefault`, specifically from the `pressureFunction` key.
#### getPoints(): table
This method returns an array of points along the bezier curve with `altitude`, `value`, and `slope` as fields for each point.
#### evaluateFunction(altitude: number): number
This method evaluates the function given the altitude along the bezier curve.

### More Block Details
This mod also adds the physics properties of blocks to any method calling to block details, such as `turtle.inspect()`.
These new details will be listed under the `"sable"` key and have the following key-value pairs:
- `mass: number`
- `friction: number`
- `restitution: number`
- `volume: number`
- `floatingScale: number`
- `floatingMaterial: table` (can be empty)
  - `factHorizontalFriction: number`
  - `factVerticalFriction: number`
  - `slowHorizontalFriction: number`
  - `slowVerticalFriction: number`
  - `liftStrength: number`
  - `transitionSpeed: number`
  - `preventSelfLift: boolean`
  - `scaleWithGravity: boolean`
  - `scaleWithPressure: boolean`

  