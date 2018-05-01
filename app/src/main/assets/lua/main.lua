require("luaextension/extension")

function main()
    print("Hello Lua!")

    local sum = tools.add(1.1, 2, 3.0)
    print("sum = " .. sum)
end

main()
