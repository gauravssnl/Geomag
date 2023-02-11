#/bin/bash

export NDK_VERSION=25.2.9519653
export ANDROID_NDK_HOME=${ANDROID_HOME}/ndk/${NDK_VERSION}
export PATH=${ANDROID_NDK_HOME}/toolchains/llvm/prebuilt/darwin-x86_64/bin:${PATH}

export GOOS=android
export CGO_ENABLED=1

export LIBS_DIR="libs"
export ARCH_LIST=("arm64-v8a" "armeabi-v7a")
export LIBS_LIST=("igrf" "wmm")

if [ ! -e ${LIBS_DIR} ]; then
    mkdir ${LIBS_DIR}
fi

for ARCH in ${ARCH_LIST[*]}; do

  if [ ${ARCH} == "arm64-v8a" ]; then

    export GOARCH=arm64
    export CC=aarch64-linux-android26-clang

  elif [ ${ARCH} == "armeabi-v7a" ]; then

    export GOARCH=arm
    export CC=armv7a-linux-androideabi26-clang

  fi

  export ARCH_DIR=${LIBS_DIR}/${ARCH}

  for LIB in ${LIBS_LIST[*]}; do

    go build -x -trimpath -buildmode=c-shared -o "${ARCH_DIR}/lib${LIB}.so" ${LIB}/main.go

  done

done