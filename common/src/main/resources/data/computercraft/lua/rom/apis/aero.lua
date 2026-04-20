--- This API is added by CC: Sable and allows CC: Tweaked computers to access dimensional physics information from Sable.
--
-- @module aero

--- Gets the air pressure at the given position.
-- @function getAirPressure
-- @tparam vector position the position to get the air pressure at
-- @treturn number the air pressure at the given position

--- Gets the dimension's gravity vector
-- @function getGravity
-- @treturn vector the gravity of the dimension

--- Gets the dimension's magnetic north vector
-- @function getMagneticNorth
-- @treturn vector the magnetic north of the dimension

--- Gets the universal drag constant for the dimension.
-- @function getUniversalDrag
-- @treturn number the universal drag constant for the dimension

--- Gets the raw physics information of the dimension (basically the JSON values assigned to it).
-- @function getRaw
-- @treturn table the raw physics information of the dimension including base gravity, base pressure, magnetic north, universal drag, and air pressure function information if found

--- Gets the default physics information of the dimension (basically the values used if no JSON configuration is set).
-- @function getDefault
-- @treturn table the raw physics information of the dimension including base gravity, base pressure, magnetic north, universal drag, and air pressure function information if found

if not aero then
    error("Cannot load Aerodynamics API on computer")
end

local native = aero.native or aero
local expect = dofile("rom/modules/main/cc/expect.lua").expect
local env = _ENV

for k,v in pairs(native) do
	if k == "getAirPressure" then
		env[k] = function(...)
			local args = {...}
			local position = args[1]
			expect(1, position, "table")
			if (getmetatable(position) or {}).__name ~= "vector" then
                expect(1, position, "vector")
            end
			local result, err = v(position.x, position.y, position.z)
			if err then
				error(err)
			end
			return result
		end
	elseif k == "getGravity" or k == "getMagneticNorth" then
		env[k] = function()
			local result, err = v()
			if err then
				error(err)
			end
			return vector.new(result.x, result.y, result.z)
		end
	elseif k == "getRaw" or k == "getDefault" then
		env[k] = function()
			local result, err = v()
			if err then
				error(err)
			end
			if result["gravity"] ~= nil then
				result.gravity = vector.new(result.gravity.x, result.gravity.y, result.gravity.z)
			end
			if result["magneticNorth"] ~= nil then
				result.magneticNorth = vector.new(result.magneticNorth.x, result.magneticNorth.y, result.magneticNorth.z)
			end
			return result
		end
	else
		env[k] = v
	end
end