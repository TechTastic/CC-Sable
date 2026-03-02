--- This API is added by CC: Sable and allows CC: Tweaked computers to access information from Sable Sub-Levels.
--
-- This library also includes [CC: Advanced Math][advanced-math] which provides the `quaternion` API.
--
-- [advanced-math]: https://techtastic.github.io/Advanced-Math/
--
-- @module sublevel

--- Determines whether the computer is currently on a Sub-Level.
-- @function isInPlotGrid
-- @treturn boolean `true` if the computer is on a Sub-Level, `false` otherwise

--- Gets the Sub-Level's Universally Unique Identifier (UUID).
-- @function getUniqueId
-- @treturn string The Sub-Level UUID
-- @raise This method errors if there is no Sub-Level associated with the computer.

--- Gets the Sub-Level's name.
-- @function getName
-- @treturn string The Sub-Level name
-- @raise This method errors if there is no Sub-Level associated with the computer.

--- Gets the Sub-Level's logical pose of the Sub-Level.
-- @function getLogicalPose
-- @treturn table Pose information including position, orientation, scale, and rotation point
-- @raise This method errors if there is no Sub-Level associated with the computer.

--- Gets the Sub-Level's last pose of the Sub-Level.
-- @function getLastPose
-- @treturn table Pose information including position, orientation, scale, and rotation point
-- @raise This method errors if there is no Sub-Level associated with the computer.

--- Gets the Sub-Level's global velocity.
-- @function getVelocity
-- @treturn vector the global velocity of the Sub-Level
-- @raise This method errors if there is no Sub-Level associated with the computer.

if not sublevel then
    error("Cannot load Sub-Level API on computer")
end

local native = sublevel.native or sublevel
local env = _ENV

for k,v in pairs(native) do
	if k == "getLogicalPose" or k == "getLastPose" then
		env[k] = function()
			local result, err = v()
			if err then
				error(err)
			end
			result.position = vector.new(result.position.x, result.position.y, result.position.z)
			result.orientation = quaternion.fromComponents(result.orientation.x, result.orientation.y, result.orientation.z, result.orientation.w)
			result.scale = vector.new(result.scale.x, result.scale.y, result.scale.z)
			result.rotationPoint = vector.new(result.rotationPoint.x, result.rotationPoint.y, result.rotationPoint.z)
			return result
		end
	else if k == "getVelocity" then
		env[k] = function()
			local result, err = v()
			if err then
				error(err)
			end
			return vector.new(result.x, result.y, result.z)
		end
	else
		env[k] = v
	end
end