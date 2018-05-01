cmake_minimum_required(VERSION 2.8)

aux_source_directory(. LUA_SRC)

# add for NDK build
add_compile_options(-D "lua_getlocaledecpoint()=('.')")

add_library(lualib STATIC ${LUA_SRC})
