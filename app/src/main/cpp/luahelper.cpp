#include "luahelper.h"

#include <android/log.h>
#include <string>

using namespace std;

static const char* tag = "luahelper->";

LuaHelper::LuaHelper() {
    L = luaL_newstate();
    luaL_openlibs(L);
}

LuaHelper::~LuaHelper() {
    lua_close(L);
    L = nullptr;
}

/*
 * 运行lua入口文件 并把这个文件所在目录添加到搜索路径
 */
int LuaHelper::runLuaFile(const char* filePath) {
    __android_log_print(ANDROID_LOG_INFO, tag, "runLuaFile:filePath=%s", filePath);

    {
        // 添加搜索路径
        string path = (filePath);
        auto pos = path.find_last_of('/');
        string pathPrefix = path.substr(0, pos);

        string luaCmd("package.path = package.path .. \";");
        luaCmd += pathPrefix;
        luaCmd += "/?.lua\"";
        int status = luaL_dostring(L, luaCmd.c_str());
        if (status != 0) {
            __android_log_print(ANDROID_LOG_ERROR, tag, "config package.path failed! status = %d", status);
            __android_log_print(ANDROID_LOG_ERROR, tag, "%s", lua_tostring(L, -1));
        }
    }

    int status = luaL_dofile(L, filePath);
    if (status != 0) {
        __android_log_print(ANDROID_LOG_ERROR, tag, "do lua file failed! status = %d", status);
        __android_log_print(ANDROID_LOG_ERROR, tag, "%s", lua_tostring(L, -1));

        lua_close(L);
        return -1;
    }
    return 0;
}

lua_State *LuaHelper::getLuaState() {
    return L;
}
