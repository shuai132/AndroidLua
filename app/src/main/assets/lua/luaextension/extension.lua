-- 重定向到android log
print = function(...)
    local str = {}
    for i, v in pairs({...}) do
        if i > 1 then
            table.insert(str, #str + 1, "\t")
        end
        table.insert(str, #str + 1, tostring(v))
    end

    log.d(table.concat(str))
end

function printf(...)
    print(string.format(...))
end

table.toString = function(t)
    local str = {}
    for k, v in pairs(t) do
        table.insert(str, #str + 1, string.format("%-20s %s\n", k, v))
    end

    return table.concat(str)
end

table.print = function(t)
    print(table.toString(t))
end
