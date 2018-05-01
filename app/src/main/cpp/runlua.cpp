#include <jni.h>

#include "luahelper.h"
#include "luaextension/init.h"

extern "C" JNIEXPORT void JNICALL Java_com_shuai_androidlua_MainActivity_runLuaFile(
        JNIEnv *env, jobject, jstring filePath) {

    const char* path = env->GetStringUTFChars(filePath, nullptr);

    LuaHelper luaHelper;
    openExtendLibs(luaHelper.getLuaState());
    luaHelper.runLuaFile(path);

    env->ReleaseStringUTFChars(filePath, path);
}
