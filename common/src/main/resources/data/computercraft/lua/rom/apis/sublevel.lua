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
	else
		env[k] = v
	end
end