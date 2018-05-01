#ifndef LUAHELPER_H
#define LUAHELPER_H

#include "lua.hpp"

class LuaHelper {
public:
    LuaHelper();

    ~LuaHelper();

    int runLuaFile(const char* filePath);

    lua_State *getLuaState();

private:
    lua_State *L = nullptr;
};

#endif // LUAHELPER_H
