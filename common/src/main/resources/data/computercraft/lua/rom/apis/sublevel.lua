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

--- Sets the Sub-level's name.
-- @function setName
-- @tparam string newName The new name for the Sub-Level
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

--- Gets the Sub-Level's latest linear velocity.
-- @function getLinearVelocity
-- @treturn vector the latest linear velocity of the Sub-Level
-- @raise This method errors if there is no Sub-Level associated with the computer.

--- Gets the Sub-Level's latest angular velocity.
-- @function getAngularVelocity
-- @treturn vector the latest angular velocity of the Sub-Level
-- @raise This method errors if there is no Sub-Level associated with the computer.

--- Gets the Sub-Level's center of mass.
-- @function getCenterOfMass
-- @treturn vector the center of mass of the Sub-Level
-- @raise This method errors if there is no Sub-Level associated with the computer.

--- Gets the Sub-Level's mass.
-- @function getMass
-- @treturn number the mass of the Sub-Level
-- @raise This method errors if there is no Sub-Level associated with the computer.

--- Gets the Sub-Level's inverse mass.
-- @function getInverseMass
-- @treturn number the inverse mass of the Sub-Level
-- @raise This method errors if there is no Sub-Level associated with the computer.

--- Gets the Sub-Level's inertia tensor.
-- @function getInertiaTensor
-- @treturn matrix the inertia tensor of the Sub-Level
-- @raise This method errors if there is no Sub-Level associated with the computer.

--- Gets the Sub-Level's inverse inertia tensor.
-- @function getInverseInertiaTensor
-- @treturn matrix the inverse inertia tensor of the Sub-Level
-- @raise This method errors if there is no Sub-Level associated with the computer.

--- Gets all forces acting upon the sublevel.
-- @function getForces
-- @treturn table All forces in the format { TYPE = { [1] = {position, force}, ... }, ... }
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
	elseif k == "getVelocity" or k == "getLinearVelocity" or k == "getAngularVelocity" or k == "getCenterOfMass" then
		env[k] = function()
			local result, err = v()
			if err then
				error(err)
			end
			return vector.new(result.x, result.y, result.z)
		end
	elseif k == "getInertiaTensor" or k == "getInverseInertiaTensor" then
		env[k] = function()
			local result, err = v()
			if err then
				error(err)
			end
			return matrix.from2DArray(result)
		end
    elseif k == "getForces" then
        env[k] = function()
			local result, err = v()
			if err then
				error(err)
			end

            for name, force in pairs(result) do
                for i, point in ipairs(force) do
                    point.position = vector.new(point.position.x, point.position.y, point.position.z)
                    point.force = vector.new(point.force.x, point.force.y, point.force.z)
                end
            end

            return result
        end
	else
		env[k] = v
	end
end