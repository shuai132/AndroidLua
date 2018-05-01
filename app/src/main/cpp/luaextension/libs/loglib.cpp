#include "libs.h"

#include <android/log.h>
#include <string>

using namespace std;

static const char* tag = "lua->";

static int log_d(lua_State *l) {
    int paramNum = lua_gettop(l);
    string log;

    for (int i = 1; i <= paramNum; i++) {
        if (lua_isstring(l, i)) {
            const char* str = luaL_checkstring(l, i);
            log += str;
        }
        else if (lua_isnumber(l, i)) {
            double value = luaL_checknumber(l, i);
            log += value;
        }
    }
    __android_log_print(ANDROID_LOG_DEBUG, tag, "%s", log.c_str());

    return 0;
}

static const luaL_Reg libs[] = {
        {"d",       log_d},
        {nullptr, nullptr}
};

int luaopen_log(lua_State *L) {
    luaL_newlib(L, libs);
    return 1;
}
